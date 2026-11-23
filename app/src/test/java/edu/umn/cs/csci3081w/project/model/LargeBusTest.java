package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LargeBusTest {
  private LargeBus vehicle;

  /**
   * Makes passenger creation predictable and builds the large bus used in the tests.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
    vehicle = new LargeBus(1, createLine(), LargeBus.CAPACITY, Bus.SPEED);
  }

  private Line createLine() {
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

    return new Line(outRoute, inRoute);
  }

  /**
   * Checks that the constructor stores the id, the capacity, and the speed.
   */
  @Test
  public void testConstructor() {
    assertEquals(1, vehicle.getId());
    assertEquals(80, vehicle.getCapacity());
    assertEquals(Bus.SPEED, vehicle.getSpeed());
  }

  /**
   * Checks that the large bus is a kind of bus.
   */
  @Test
  public void testIsABus() {
    assertTrue(vehicle instanceof Bus);
  }

  /**
   * Checks the CO2 for an empty large bus and then with riders on board.
   */
  @Test
  public void testGetCo2Emission() {
    assertEquals(5, vehicle.getCo2Emission());

    for (int i = 0; i < 2; i++) {
      vehicle.loadPassenger(new Passenger(1, "Goldy"));
    }
    assertEquals(9, vehicle.getCo2Emission());
  }
}
