package edu.umn.cs.csci3081w.project.model;

import java.time.LocalDateTime;

/**
 * Makes buses of the type chosen by the deployment strategy for the time of day.
 */
public class BusFactory {
  private BusStrategy strategy;

  /**
   * Makes a bus factory that uses the day strategy from 8am up to 6pm and the night strategy
   * the rest of the time.
   *
   * @param currentTime local time used to pick the strategy
   */
  public BusFactory(LocalDateTime currentTime) {
    if (currentTime.getHour() >= 8 && currentTime.getHour() < 18) {
      strategy = new BusStrategyDay();
    } else {
      strategy = new BusStrategyNight();
    }
  }

  /**
   * Returns the type of the next bus to deploy according to the strategy in effect.
   *
   * @return type of the next bus, either small or large
   */
  public String getNextBusType() {
    return strategy.getNextBusType();
  }

  /**
   * Makes a bus of the given type.
   *
   * @param busType type of bus to make
   * @param id      bus identifier
   * @param line    line the bus serves
   * @return the new bus
   */
  public Bus generate(String busType, int id, Line line) {
    if (busType.equals(SmallBus.SMALL_BUS_VEHICLE)) {
      return new SmallBus(id, line, SmallBus.CAPACITY, SmallBus.SPEED);
    }
    return new LargeBus(id, line, LargeBus.CAPACITY, LargeBus.SPEED);
  }
}
