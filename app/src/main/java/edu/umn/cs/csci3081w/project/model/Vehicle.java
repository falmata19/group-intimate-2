package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A vehicle that carries passengers along routes.
 */
public abstract class Vehicle {
  private int id;
  private int capacity;
  //the speed is in distance over a time unit
  private double speed;
  private PassengerLoader loader;
  private PassengerUnloader unloader;
  private List<Passenger> passengers;
  private String name;
  private Position position;


  /**
   * Constructor for a vehicle.
   *
   * @param id       vehicle identifier
   * @param capacity vehicle capacity
   * @param speed    vehicle speed
   * @param loader   passenger loader for vehicle
   * @param unloader passenger unloader for vehicle
   */
  public Vehicle(int id, int capacity, double speed, PassengerLoader loader,
                 PassengerUnloader unloader) {
    this.id = id;
    this.capacity = capacity;
    this.speed = speed;
    this.loader = loader;
    this.unloader = unloader;
    this.passengers = new ArrayList<Passenger>();
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
  public abstract boolean isTripComplete();

  /**
   * Puts a passenger on the vehicle.
   *
   * @param newPassenger the passenger to put on the vehicle
   * @return how many passengers got on
   */
  public abstract int loadPassenger(Passenger newPassenger);

  /**
   * Moves the vehicle along its route.
   */
  public abstract void move();

  /**
   * Updates the vehicle for one time step.
   */
  public abstract void update();

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

  /**
   * Returns the CO2 the vehicle is producing right now.
   */
  public abstract int getCo2Emission();
}
