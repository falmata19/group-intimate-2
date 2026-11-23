package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BusStrategyNightTest {

  /**
   * Checks that the night strategy goes small, small, small, large and then repeats.
   */
  @Test
  public void testGetNextBusType() {
    BusStrategy strategy = new BusStrategyNight();
    String[] expected = {
        SmallBus.SMALL_BUS_VEHICLE, SmallBus.SMALL_BUS_VEHICLE, SmallBus.SMALL_BUS_VEHICLE,
        LargeBus.LARGE_BUS_VEHICLE, SmallBus.SMALL_BUS_VEHICLE, SmallBus.SMALL_BUS_VEHICLE,
        SmallBus.SMALL_BUS_VEHICLE, LargeBus.LARGE_BUS_VEHICLE
    };
    for (String busType : expected) {
      assertEquals(busType, strategy.getNextBusType());
    }
  }

  /**
   * Checks that two night strategies keep their own place in the sequence.
   */
  @Test
  public void testStrategiesKeepSeparateState() {
    BusStrategy first = new BusStrategyNight();
    BusStrategy second = new BusStrategyNight();
    first.getNextBusType();
    first.getNextBusType();
    first.getNextBusType();
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, second.getNextBusType());
    assertEquals(LargeBus.LARGE_BUS_VEHICLE, first.getNextBusType());
  }
}
