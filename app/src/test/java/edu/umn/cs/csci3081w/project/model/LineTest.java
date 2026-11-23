package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Line class.
 */
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

  /**
   * Tests that the old constructor gives id -1 and takes the line name and type from
   * the outbound route.
   */
  @Test
  public void testConstructorWithoutIdTakesNameAndTypeFromOutbound() {
    Line line = new Line(createRoute(10, "testRouteOut"), createRoute(11, "testRouteIn"));
    assertEquals(-1, line.getId());
    assertEquals("testLine", line.getName());
    assertEquals("BUS_LINE", line.getType());
  }

  /**
   * Tests that the old constructor works with a null outbound route.
   */
  @Test
  public void testConstructorWithNullOutbound() {
    Line line = new Line(null, null);
    assertEquals(-1, line.getId());
    assertNull(line.getName());
    assertNull(line.getType());
    assertNull(line.getOutboundRoute());
  }

  /**
   * Tests that the full constructor stores the id, name, type, and both routes.
   */
  @Test
  public void testFullConstructor() {
    Route outbound = createRoute(10, "testRouteOut");
    Route inbound = createRoute(11, "testRouteIn");
    Line line = new Line(3, "Campus Connector", Route.BUS_LINE, outbound, inbound);
    assertEquals(3, line.getId());
    assertEquals("Campus Connector", line.getName());
    assertEquals(Route.BUS_LINE, line.getType());
    assertSame(outbound, line.getOutboundRoute());
    assertSame(inbound, line.getInboundRoute());
  }

  /**
   * Tests that shallowCopy keeps the id, name, and type, makes new route objects, and
   * keeps the same stops in those routes.
   */
  @Test
  public void testShallowCopy() {
    Route outbound = createRoute(10, "testRouteOut");
    Route inbound = createRoute(11, "testRouteIn");
    Line line = new Line(7, "Green Line", Route.TRAIN_LINE, outbound, inbound);
    Line copy = line.shallowCopy();
    assertNotSame(line, copy);
    assertEquals(7, copy.getId());
    assertEquals("Green Line", copy.getName());
    assertEquals(Route.TRAIN_LINE, copy.getType());
    assertNotSame(outbound, copy.getOutboundRoute());
    assertNotSame(inbound, copy.getInboundRoute());
    assertEquals(outbound.getId(), copy.getOutboundRoute().getId());
    assertEquals(inbound.getId(), copy.getInboundRoute().getId());
    assertSame(outbound.getStops().get(0), copy.getOutboundRoute().getStops().get(0));
    assertSame(inbound.getStops().get(1), copy.getInboundRoute().getStops().get(1));
  }

  /**
   * Tests that a new line has no issue.
   */
  @Test
  public void testIssueInitiallyNotActive() {
    Line line = new Line(createRoute(10, "out"), createRoute(11, "in"));
    assertFalse(line.isIssueActive());
  }

  /**
   * Tests that startIssue makes the issue exist and that it ends after 10 updates.
   */
  @Test
  public void testStartIssueAndUpdateIssue() {
    Line line = new Line(createRoute(10, "out"), createRoute(11, "in"));
    line.startIssue();
    assertTrue(line.isIssueActive());
    for (int i = 0; i < 10; i++) {
      line.updateIssue();
    }
    assertFalse(line.isIssueActive());
  }

  /**
   * Tests that an issue started on a line is visible on its shallow copy, and that an issue
   * started on the copy is visible on the line.
   */
  @Test
  public void testShallowCopySharesIssue() {
    Line line = new Line(createRoute(10, "out"), createRoute(11, "in"));
    Line copy = line.shallowCopy();
    line.startIssue();
    assertTrue(copy.isIssueActive());
    for (int i = 0; i < 10; i++) {
      copy.updateIssue();
    }
    assertFalse(line.isIssueActive());
    copy.startIssue();
    assertTrue(line.isIssueActive());
  }

}
