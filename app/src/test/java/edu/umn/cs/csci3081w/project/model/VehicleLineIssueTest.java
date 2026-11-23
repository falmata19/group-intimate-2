package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that vehicles do not move while their line has an issue.
 */
public class VehicleLineIssueTest {

  private Line line;
  private SmallBus bus;

  /**
   * Makes passenger creation predictable and builds a bus on a long line, so the trip does not
   * finish during the tests. The bus gets a shallow copy of the line, like in the simulator.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
    line = new Line(createRoute(10, "testRouteOut", 44.0, 45.0),
        createRoute(11, "testRouteIn", 45.0, 44.0));
    bus = new SmallBus(1, line.shallowCopy(), SmallBus.CAPACITY, Bus.SPEED);
  }

  private Route createRoute(int id, String name, double fromLatitude, double toLatitude) {
    List<Stop> stops = new ArrayList<Stop>();
    stops.add(new Stop(id * 10, name + " stop 1", new Position(-93.2, fromLatitude)));
    stops.add(new Stop(id * 10 + 1, name + " stop 2", new Position(-93.2, toLatitude)));
    List<Double> distances = new ArrayList<Double>();
    distances.add(Math.abs(toLatitude - fromLatitude) * 69 * 2);
    List<Double> probabilities = new ArrayList<Double>();
    probabilities.add(.0);
    probabilities.add(.0);
    PassengerGenerator generator = new RandomPassengerGenerator(stops, probabilities);
    return new Route(id, "testLine", Route.BUS_LINE, name, stops, distances, generator);
  }

  /**
   * Tests that a vehicle moves when its line has no issue.
   */
  @Test
  public void testMovesWithoutIssue() {
    bus.update();
    double latitude = bus.getPosition().getLatitude();
    bus.update();
    assertTrue(bus.getPosition().getLatitude() != latitude);
  }

  /**
   * Tests that the position of a vehicle does not change across several updates while its line
   * has an issue, even if the issue was started on the original line and not on the copy.
   */
  @Test
  public void testDoesNotMoveDuringIssue() {
    bus.update();
    bus.update();
    line.startIssue();
    double latitude = bus.getPosition().getLatitude();
    double longitude = bus.getPosition().getLongitude();
    for (int i = 0; i < 5; i++) {
      bus.update();
      assertEquals(latitude, bus.getPosition().getLatitude());
      assertEquals(longitude, bus.getPosition().getLongitude());
    }
  }

  /**
   * Tests that the vehicle moves again after the issue of its line ends.
   */
  @Test
  public void testMovesAgainAfterIssueEnds() {
    bus.update();
    line.startIssue();
    double latitude = bus.getPosition().getLatitude();
    bus.update();
    assertEquals(latitude, bus.getPosition().getLatitude());
    for (int i = 0; i < 10; i++) {
      line.updateIssue();
    }
    bus.update();
    assertTrue(bus.getPosition().getLatitude() != latitude);
  }

  /**
   * Tests that the move method alone also does nothing while the line has an issue.
   */
  @Test
  public void testMoveDoesNothingDuringIssue() {
    bus.update();
    line.startIssue();
    double latitude = bus.getPosition().getLatitude();
    bus.move();
    assertEquals(latitude, bus.getPosition().getLatitude());
  }
}
