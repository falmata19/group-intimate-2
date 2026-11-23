package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusFactoryTest {
  private Line line;

  /**
   * Makes passenger creation predictable and builds the line used in the tests.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;

    Stop firstStop = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop lastStop = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));

    List<Stop> stopsOut = new ArrayList<>();
    stopsOut.add(firstStop);
    stopsOut.add(lastStop);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.9712663713083954);
    List<Double> probabilitiesOut = new ArrayList<>();
    probabilitiesOut.add(.15);
    probabilitiesOut.add(.0);
    Route outRoute = new Route(10, "testLine", Route.BUS_LINE, "testRouteOut", stopsOut,
        distancesOut, new RandomPassengerGenerator(stopsOut, probabilitiesOut));

    List<Stop> stopsIn = new ArrayList<>();
    stopsIn.add(lastStop);
    stopsIn.add(firstStop);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.9712663713083954);
    List<Double> probabilitiesIn = new ArrayList<>();
    probabilitiesIn.add(.3);
    probabilitiesIn.add(.0);
    Route inRoute = new Route(11, "testLine", Route.BUS_LINE, "testRouteIn", stopsIn,
        distancesIn, new RandomPassengerGenerator(stopsIn, probabilitiesIn));

    line = new Line(outRoute, inRoute);
  }

  /**
   * Checks that a factory made at 8am uses the day strategy.
   */
  @Test
  public void testDayStrategyAtEightAm() {
    BusFactory factory = new BusFactory(LocalDateTime.of(2026, 11, 22, 8, 0));
    assertEquals(LargeBus.LARGE_BUS_VEHICLE, factory.getNextBusType());
    assertEquals(LargeBus.LARGE_BUS_VEHICLE, factory.getNextBusType());
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, factory.getNextBusType());
  }

  /**
   * Checks that a factory made one minute before 6pm still uses the day strategy.
   */
  @Test
  public void testDayStrategyBeforeSixPm() {
    BusFactory factory = new BusFactory(LocalDateTime.of(2026, 11, 22, 17, 59));
    assertEquals(LargeBus.LARGE_BUS_VEHICLE, factory.getNextBusType());
  }

  /**
   * Checks that a factory made at 6pm uses the night strategy.
   */
  @Test
  public void testNightStrategyAtSixPm() {
    BusFactory factory = new BusFactory(LocalDateTime.of(2026, 11, 22, 18, 0));
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, factory.getNextBusType());
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, factory.getNextBusType());
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, factory.getNextBusType());
    assertEquals(LargeBus.LARGE_BUS_VEHICLE, factory.getNextBusType());
  }

  /**
   * Checks that a factory made at midnight uses the night strategy.
   */
  @Test
  public void testNightStrategyAtMidnight() {
    BusFactory factory = new BusFactory(LocalDateTime.of(2026, 11, 22, 0, 0));
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, factory.getNextBusType());
  }

  /**
   * Checks that a factory made one minute before 8am still uses the night strategy.
   */
  @Test
  public void testNightStrategyBeforeEightAm() {
    BusFactory factory = new BusFactory(LocalDateTime.of(2026, 11, 22, 7, 59));
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, factory.getNextBusType());
  }

  /**
   * Checks that a small bus is made with the small bus capacity and the given id.
   */
  @Test
  public void testGenerateSmallBus() {
    BusFactory factory = new BusFactory(LocalDateTime.of(2026, 11, 22, 12, 0));
    Bus bus = factory.generate(SmallBus.SMALL_BUS_VEHICLE, 1000, line);
    assertTrue(bus instanceof SmallBus);
    assertEquals(1000, bus.getId());
    assertEquals(SmallBus.CAPACITY, bus.getCapacity());
  }

  /**
   * Checks that a large bus is made with the large bus capacity and the given id.
   */
  @Test
  public void testGenerateLargeBus() {
    BusFactory factory = new BusFactory(LocalDateTime.of(2026, 11, 22, 12, 0));
    Bus bus = factory.generate(LargeBus.LARGE_BUS_VEHICLE, 1001, line);
    assertTrue(bus instanceof LargeBus);
    assertEquals(1001, bus.getId());
    assertEquals(LargeBus.CAPACITY, bus.getCapacity());
  }
}
