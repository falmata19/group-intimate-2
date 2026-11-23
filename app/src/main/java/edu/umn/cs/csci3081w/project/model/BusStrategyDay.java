package edu.umn.cs.csci3081w.project.model;

/**
 * Deploys buses in the repeating sequence large, large, small during the day.
 */
public class BusStrategyDay implements BusStrategy {
  private static final String[] SEQUENCE = {
      LargeBus.LARGE_BUS_VEHICLE, LargeBus.LARGE_BUS_VEHICLE, SmallBus.SMALL_BUS_VEHICLE
  };
  private int position = 0;

  /**
   * Returns the type of the next bus to deploy and moves on to the following one in the sequence.
   *
   * @return type of the next bus, either small or large
   */
  @Override
  public String getNextBusType() {
    String busType = SEQUENCE[position];
    position = (position + 1) % SEQUENCE.length;
    return busType;
  }
}
