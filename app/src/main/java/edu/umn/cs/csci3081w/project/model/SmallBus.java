package edu.umn.cs.csci3081w.project.model;

/**
 * A small bus, which holds fewer passengers than a regular bus.
 */
public class SmallBus extends Bus {
  public static final String SMALL_BUS_VEHICLE = "SMALL_BUS_VEHICLE";
  public static final int CAPACITY = 20;

  /**
   * Constructor for a small bus.
   *
   * @param id       bus identifier
   * @param line     line the bus serves
   * @param capacity capacity of bus
   * @param speed    speed of bus
   */
  public SmallBus(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);
  }

  /**
   * Returns the CO2 the small bus is producing right now.
   */
  @Override
  public int getCo2Emission() {
    return getPassengers().size() * 2 + 3;
  }
}
