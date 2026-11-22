package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RouteTest {

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

  private Route createRoute() {
    List<Stop> stops = new ArrayList<Stop>();
    stops.add(new Stop(0, "test stop 1", new Position(-93.243774, 44.972392)));
    stops.add(new Stop(1, "test stop 2", new Position(-93.235071, 44.973581)));
    stops.add(new Stop(2, "test stop 3", new Position(-93.226632, 44.975392)));
    List<Double> distances = new ArrayList<Double>();
    distances.add(0.5);
    distances.add(0.75);
    List<Double> probabilities = new ArrayList<Double>();
    probabilities.add(.15);
    probabilities.add(.3);
    probabilities.add(.0);
    PassengerGenerator generator = new RandomPassengerGenerator(stops, probabilities);
    return new Route(10, "testLine", "BUS_LINE", "testRoute", stops, distances, generator);
  }

  private String reportOf(Route route) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    route.report(printStream);
    printStream.flush();
    return outputStream.toString();
  }

  private String expectedReport(Route route, int markedIndex) {
    String sep = System.lineSeparator();
    StringBuilder expected = new StringBuilder();
    expected.append("####Route Info Start####").append(sep)
        .append("ID: 10").append(sep)
        .append("Line name: testLine").append(sep)
        .append("Line type: BUS_LINE").append(sep)
        .append("Name: testRoute").append(sep)
        .append("Num stops: 3").append(sep)
        .append("****Stops Info Start****").append(sep);
    for (int index = 0; index < route.getStops().size(); index++) {
      Stop stop = route.getStops().get(index);
      if (index == markedIndex) {
        expected.append("++++Next Stop Info Start++++").append(sep);
      }
      expected.append("####Stop Info Start####").append(sep)
          .append("ID: ").append(stop.getId()).append(sep)
          .append("Name: ").append(stop.getName()).append(sep)
          .append("Position: ").append(stop.getPosition().getLatitude()).append(",")
          .append(stop.getPosition().getLongitude()).append(sep)
          .append("****Passengers Info Start****").append(sep)
          .append("Num passengers waiting: 0").append(sep)
          .append("****Passengers Info End****").append(sep)
          .append("####Stop Info End####").append(sep);
      if (index == markedIndex) {
        expected.append("++++Next Stop Info End++++").append(sep);
      }
    }
    expected.append("****Stops Info End****").append(sep)
        .append("####Route Info End####").append(sep);
    return expected.toString();
  }

  /**
   * Tests the state of a route after using the constructor.
   */
  @Test
  public void testConstructor() {
    Route route = createRoute();
    assertEquals(10, route.getId());
    assertEquals("testLine", route.getLineName());
    assertEquals("BUS_LINE", route.getLineType());
    assertEquals("testRoute", route.getName());
    assertEquals(3, route.getStops().size());
    assertEquals(0, route.getNextStopIndex());
    assertSame(route.getStops().get(0), route.getNextStop());
    assertFalse(route.isAtEnd());
  }

  /**
   * Tests that a shallow copy is a new route that shares the same stops.
   */
  @Test
  public void testShallowCopy() {
    Route route = createRoute();
    route.nextStop();
    Route copy = route.shallowCopy();
    assertNotSame(route, copy);
    assertEquals(route.getId(), copy.getId());
    assertEquals(route.getName(), copy.getName());
    assertEquals(route.getLineName(), copy.getLineName());
    assertEquals(route.getLineType(), copy.getLineType());
    assertEquals(route.getStops().size(), copy.getStops().size());
    for (int index = 0; index < route.getStops().size(); index++) {
      assertSame(route.getStops().get(index), copy.getStops().get(index));
    }
    assertEquals(0, copy.getNextStopIndex());
    assertEquals(1, route.getNextStopIndex());
  }

  /**
   * Tests that updating a route ages the passengers waiting at its stops.
   */
  @Test
  public void testUpdate() {
    Route route = createRoute();
    Passenger passenger = new Passenger(1, "Goldy");
    route.getStops().get(0).addPassengers(passenger);
    route.update();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    passenger.report(printStream);
    printStream.flush();
    assertTrue(outputStream.toString().contains("Wait at stop: 1"));
  }

  /**
   * Tests the report output when the first stop is the next stop.
   */
  @Test
  public void testReportAtStart() {
    Route route = createRoute();
    assertEquals(expectedReport(route, 0), reportOf(route));
  }

  /**
   * Tests that the report marks the new next stop after the route advances.
   */
  @Test
  public void testReportAfterNextStop() {
    Route route = createRoute();
    route.nextStop();
    assertEquals(expectedReport(route, 1), reportOf(route));
  }

  /**
   * Tests that a route is at its end only after every stop has been passed.
   */
  @Test
  public void testIsAtEnd() {
    Route route = createRoute();
    assertFalse(route.isAtEnd());
    route.nextStop();
    route.nextStop();
    assertFalse(route.isAtEnd());
    route.nextStop();
    assertTrue(route.isAtEnd());
  }

  /**
   * Tests the previous stop at the start, in the middle, and at the end of the route.
   */
  @Test
  public void testPrevStop() {
    Route route = createRoute();
    assertSame(route.getStops().get(0), route.prevStop());
    route.nextStop();
    assertSame(route.getStops().get(0), route.prevStop());
    route.nextStop();
    assertSame(route.getStops().get(1), route.prevStop());
    route.nextStop();
    assertSame(route.getStops().get(2), route.prevStop());
  }

  /**
   * Tests that advancing moves the destination stop and keeps the last stop at the end.
   */
  @Test
  public void testNextStop() {
    Route route = createRoute();
    route.nextStop();
    assertEquals(1, route.getNextStopIndex());
    assertSame(route.getStops().get(1), route.getNextStop());
    route.nextStop();
    assertEquals(2, route.getNextStopIndex());
    assertSame(route.getStops().get(2), route.getNextStop());
    route.nextStop();
    assertEquals(3, route.getNextStopIndex());
    assertSame(route.getStops().get(2), route.getNextStop());
  }

  /**
   * Tests that the destination stop starts at the first stop and follows the route.
   */
  @Test
  public void testGetNextStop() {
    Route route = createRoute();
    assertSame(route.getStops().get(0), route.getNextStop());
    route.nextStop();
    assertSame(route.getStops().get(1), route.getNextStop());
  }

  /**
   * Tests the distance to the next stop at each position along the route.
   */
  @Test
  public void testGetNextStopDistance() {
    Route route = createRoute();
    assertEquals(0.0, route.getNextStopDistance());
    route.nextStop();
    assertEquals(0.5, route.getNextStopDistance());
    route.nextStop();
    assertEquals(0.75, route.getNextStopDistance());
  }

  /**
   * Tests that the number of generated passengers matches the passengers added to the stops.
   */
  @Test
  public void testGenerateNewPassengers() {
    Route route = createRoute();
    int generated = route.generateNewPassengers();
    int waiting = 0;
    for (Stop stop : route.getStops()) {
      waiting += stop.getPassengers().size();
    }
    assertEquals(waiting, generated);
  }

}
