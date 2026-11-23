package edu.umn.cs.csci3081w.project.model;

/**
 * A large bus, which holds more passengers than a regular bus.
 */
public class LargeBus extends Bus {
  public static final String LARGE_BUS_VEHICLE = "LARGE_BUS_VEHICLE";
  public static final int CAPACITY = 80;

  /**
   * Constructor for a large bus.
   *
   * @param id       bus identifier
   * @param line     line the bus serves
   * @param capacity capacity of bus
   * @param speed    speed of bus
   */
  public LargeBus(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);
  }

  /**
   * Returns the CO2 the large bus is producing right now.
   */
  @Override
  public int getCo2Emission() {
    return getPassengers().size() * 2 + 5;
  }
}
