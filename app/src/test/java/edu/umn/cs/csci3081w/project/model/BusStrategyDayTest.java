package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BusStrategyDayTest {

  /**
   * Checks that the day strategy goes large, large, small and then repeats.
   */
  @Test
  public void testGetNextBusType() {
    BusStrategy strategy = new BusStrategyDay();
    String[] expected = {
        LargeBus.LARGE_BUS_VEHICLE, LargeBus.LARGE_BUS_VEHICLE, SmallBus.SMALL_BUS_VEHICLE,
        LargeBus.LARGE_BUS_VEHICLE, LargeBus.LARGE_BUS_VEHICLE, SmallBus.SMALL_BUS_VEHICLE,
        LargeBus.LARGE_BUS_VEHICLE
    };
    for (String busType : expected) {
      assertEquals(busType, strategy.getNextBusType());
    }
  }

  /**
   * Checks that two day strategies keep their own place in the sequence.
   */
  @Test
  public void testStrategiesKeepSeparateState() {
    BusStrategy first = new BusStrategyDay();
    BusStrategy second = new BusStrategyDay();
    first.getNextBusType();
    first.getNextBusType();
    assertEquals(LargeBus.LARGE_BUS_VEHICLE, second.getNextBusType());
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, first.getNextBusType());
  }
}
