package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LineTest {

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
  }

  private Route createRoute(int id, String name) {
    List<Stop> stops = new ArrayList<Stop>();
    stops.add(new Stop(0, "test stop 1", new Position(-93.243774, 44.972392)));
    stops.add(new Stop(1, "test stop 2", new Position(-93.235071, 44.973581)));
    List<Double> distances = new ArrayList<Double>();
    distances.add(0.5);
    List<Double> probabilities = new ArrayList<Double>();
    probabilities.add(.15);
    probabilities.add(.0);
    PassengerGenerator generator = new RandomPassengerGenerator(stops, probabilities);
    return new Route(id, "testLine", "BUS_LINE", name, stops, distances, generator);
  }

  /**
   * Tests that a line returns the outbound and inbound routes passed to the constructor.
   */
  @Test
  public void testConstructor() {
    Route outbound = createRoute(10, "testRouteOut");
    Route inbound = createRoute(11, "testRouteIn");
    Line line = new Line(outbound, inbound);
    assertSame(outbound, line.getOutboundRoute());
    assertSame(inbound, line.getInboundRoute());
  }

}
