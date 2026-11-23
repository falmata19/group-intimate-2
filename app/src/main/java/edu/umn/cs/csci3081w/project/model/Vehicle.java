package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A vehicle that carries passengers along routes.
 */
public abstract class Vehicle {
  public static final int CO2_HISTORY_SIZE = 5;
  private int id;
  private int capacity;
  //the speed is in distance over a time unit
  private double speed;
  private PassengerLoader loader;
  private PassengerUnloader unloader;
  private List<Passenger> passengers;
  private String name;
  private Position position;
  private Line line;
  private double distanceRemaining;
  private Stop nextStop;
  // CO2 of the latest five time units, oldest first and current time unit last
  private int[] co2History = new int[CO2_HISTORY_SIZE];
  private List<CO2Observer> observers = new ArrayList<CO2Observer>();



  /**
   * Constructor for a vehicle.
   *
   * @param id       vehicle identifier
   * @param line     line the vehicle serves
   * @param capacity vehicle capacity
   * @param speed    vehicle speed
   * @param loader   passenger loader for vehicle
   * @param unloader passenger unloader for vehicle
   */
  public Vehicle(int id, Line line, int capacity, double speed, PassengerLoader loader,
                 PassengerUnloader unloader) {
    this.id = id;
    this.line = line;
    this.capacity = capacity;
    this.speed = speed;
    this.loader = loader;
    this.unloader = unloader;
    this.passengers = new ArrayList<Passenger>();
    this.distanceRemaining = 0;
    this.nextStop = line.getOutboundRoute().getNextStop();
    setName(line.getOutboundRoute().getName() + id);
    setPosition(new Position(nextStop.getPosition().getLongitude(),
        nextStop.getPosition().getLatitude()));
  }

  /**
   * Prints info about the vehicle.
   *
   * @param out the stream to print to
   */
  public abstract void report(PrintStream out);

  /**
   * Checks if the vehicle finished its trip.
   *
   * @return true if the trip is done
   */
  public boolean isTripComplete() {
    return line.getOutboundRoute().isAtEnd() && line.getInboundRoute().isAtEnd();
  }

  /**
   * Puts a passenger on the vehicle.
   *
   * @param newPassenger the passenger to put on the vehicle
   * @return how many passengers got on
   */
  public int loadPassenger(Passenger newPassenger) {
    return getPassengerLoader().loadPassenger(newPassenger, getCapacity(), getPassengers());
  }

  /**
   * Moves the vehicle along its route. The vehicle does not move while its line has an issue.
   */
  public void move() {
    if (line.isIssueActive()) {
      return;
    }
    for (Passenger passenger : getPassengers()) {
      passenger.pasUpdate();
    }
    updateDistance();
    if (!isTripComplete() && distanceRemaining <= 0) {
      int passengersHandled = handleStop();
      if (passengersHandled >= 0) {
        distanceRemaining = 0;
      }
      toNextStop();
    }

    Route currentRoute = line.getOutboundRoute();
    if (line.getOutboundRoute().isAtEnd()) {
      if (line.getInboundRoute().isAtEnd()) {
        return;
      }
      currentRoute = line.getInboundRoute();
    }
    Stop prevStop = currentRoute.prevStop();
    Stop nextStop = currentRoute.getNextStop();
    double distanceBetween = currentRoute.getNextStopDistance();
    double ratio;
    if (distanceBetween - 0.00001 < 0) {
      ratio = 1;
    } else {
      ratio = distanceRemaining / distanceBetween;
      if (ratio < 0) {
        ratio = 0;
        distanceRemaining = 0;
      }
    }
    double newLongitude = nextStop.getPosition().getLongitude() * (1 - ratio)
        + prevStop.getPosition().getLongitude() * ratio;
    double newLatitude = nextStop.getPosition().getLatitude() * (1 - ratio)
        + prevStop.getPosition().getLatitude() * ratio;
    setPosition(new Position(newLongitude, newLatitude));
  }

  /**
   * Updates the vehicle for one time step and records its CO2 for the time step.
   */
  public void update() {
    move();
    updateCo2History();
  }

  /**
   * Adds an observer that is told about the CO2 history after every update of the vehicle.
   *
   * @param observer the observer to add
   */
  public void addCO2Observer(CO2Observer observer) {
    if (observer != null && !observers.contains(observer)) {
      observers.add(observer);
    }
  }

  /**
   * Stops telling an observer about the CO2 history of the vehicle.
   *
   * @param observer the observer to remove
   */
  public void removeCO2Observer(CO2Observer observer) {
    observers.remove(observer);
  }

  /**
   * Tells every observer about the latest CO2 history.
   */
  protected void notifyCO2Observers() {
    for (CO2Observer observer : new ArrayList<CO2Observer>(observers)) {
      observer.onCO2Update(getId(), co2History.clone());
    }
  }

  /**
   * Moves the CO2 history one time unit forward, saves the current CO2 and tells the observers.
   */
  protected void updateCo2History() {
    for (int i = 0; i < CO2_HISTORY_SIZE - 1; i++) {
      co2History[i] = co2History[i + 1];
    }
    co2History[CO2_HISTORY_SIZE - 1] = getCo2Emission();
    notifyCO2Observers();
  }

  /**
   * Returns the CO2 of the latest five time units.
   *
   * @return copy of the CO2 history, oldest first and current time unit last
   */
  public int[] getCo2History() {
    return co2History.clone();
  }



  private int unloadPassengers() {
    return getPassengerUnloader().unloadPassengers(getPassengers(), nextStop);
  }

  private int handleStop() {
    int passengersHandled = 0;
    passengersHandled += unloadPassengers();
    passengersHandled += nextStop.loadPassengers(this);
    if (passengersHandled != 0) {
      distanceRemaining = 0;
    }
    return passengersHandled;
  }

  private void toNextStop() {
    currentRoute().nextStop();
    if (!isTripComplete()) {
      nextStop = currentRoute().getNextStop();
      distanceRemaining += currentRoute().getNextStopDistance();
    } else {
      nextStop = null;
      distanceRemaining = 999;
    }
  }

  private double updateDistance() {
    if (isTripComplete()) {
      return 0;
    }
    if (getSpeed() < 0) {
      return 0;
    }
    distanceRemaining -= getSpeed();
    return getSpeed();
  }

  private Route currentRoute() {
    if (!line.getOutboundRoute().isAtEnd()) {
      return line.getOutboundRoute();
    }
    return line.getInboundRoute();
  }

  public int getId() {
    return id;
  }

  public int getCapacity() {
    return capacity;
  }

  public double getSpeed() {
    return speed;
  }

  public PassengerLoader getPassengerLoader() {
    return loader;
  }

  public PassengerUnloader getPassengerUnloader() {
    return unloader;
  }

  public List<Passenger> getPassengers() {
    return passengers;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Stop getNextStop() {
    return nextStop;
  }

  public double getDistanceRemaining() {
    return distanceRemaining;
  }

  /**
   * Returns the CO2 the vehicle is producing right now.
   *
   * @return CO2 units for this time unit
   */
  public abstract int getCo2Emission();
}
