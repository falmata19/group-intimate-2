package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

/**
 * A bus that drives the outbound route and then the inbound route, and picks up and drops off
 * passengers at each stop.
 */
public class Bus extends Vehicle {
  public static final String BUS_VEHICLE = "BUS_VEHICLE";
  public static final double SPEED = 0.5;
  public static final int CAPACITY = 60;


  /**
   * Constructor for a bus.
   *
   * @param id       bus identifier
   * @param line     line the bus serves
   * @param capacity capacity of bus
   * @param speed    speed of bus
   */
  public Bus(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed, new PassengerLoader(), new PassengerUnloader());
  }


  /**
   * Report statistics for the bus.
   *
   * @param out stream for printing
   */
  public void report(PrintStream out) {
    out.println("####Bus Info Start####");
    out.println("ID: " + getId());
    out.println("Name: " + getName());
    out.println("Speed: " + getSpeed());
    out.println("Capacity: " + getCapacity());
    out.println("Position: " + (getPosition().getLatitude() + "," + getPosition().getLongitude()));
    out.println("Distance to next stop: " + getDistanceRemaining());
    out.println("****Passengers Info Start****");
    out.println("Num of passengers: " + getPassengers().size());
    for (Passenger pass : getPassengers()) {
      pass.report(out);
    }
    out.println("****Passengers Info End****");
    out.println("####Bus Info End####");
  }

  /**
   * Returns the CO2 the bus is producing right now.
   */
  public int getCo2Emission() {
    return getPassengers().size() * 2 + 4;
  }
}
