package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusTest {

  /**
   * Makes passenger creation predictable before every test.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
  }

  /**
   * Builds a bus on a small line with three stops going out and the same three coming back.
   *
   * @return the bus used in the tests
   */
  public Bus createBus() {
    Stop firstStop = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop middleStop = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    Stop lastStop = new Stop(2, "test stop 3", new Position(-93.226632, 44.975392));

    List<Stop> stopsOut = new ArrayList<>();
    stopsOut.add(firstStop);
    stopsOut.add(middleStop);
    stopsOut.add(lastStop);

    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.9712663713083954);
    distancesOut.add(0.961379387775189);

    List<Double> probabilitiesOut = new ArrayList<>();
    probabilitiesOut.add(.15);
    probabilitiesOut.add(.3);
    probabilitiesOut.add(.0);

    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);
    Route outRoute = new Route(10, "testLine", Route.BUS_LINE, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    List<Stop> stopsIn = new ArrayList<>();
    stopsIn.add(lastStop);
    stopsIn.add(middleStop);
    stopsIn.add(firstStop);

    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.961379387775189);
    distancesIn.add(0.9712663713083954);

    List<Double> probabilitiesIn = new ArrayList<>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(.3);
    probabilitiesIn.add(.0);

    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);
    Route inRoute = new Route(11, "testLine", Route.BUS_LINE, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    Line busLine = new Line(outRoute, inRoute);
    return new Bus(0, busLine, 5, 1);
  }

  /**
   * Checks that a new bus starts with the right id, name, capacity, speed, and first stop.
   */
  @Test
  public void testConstructorNormal() {
    Bus bus = createBus();
    assertEquals(0, bus.getId());
    assertEquals(5, bus.getCapacity());
    assertEquals(1, bus.getSpeed());
    assertEquals("testRouteOut0", bus.getName());
    assertEquals(0, bus.getNextStop().getId());
    assertEquals(44.972392, bus.getPosition().getLatitude());
    assertEquals(-93.243774, bus.getPosition().getLongitude());
    assertEquals(0, bus.getPassengers().size());
  }

  /**
   * Checks that a passenger gets on the bus when there is room.
   */
  @Test
  public void testLoadPassenger() {
    Bus bus = createBus();
    Passenger rider = new Passenger(1, "Goldy");

    int loaded = bus.loadPassenger(rider);

    assertEquals(1, loaded);
    assertEquals(1, bus.getPassengers().size());
    assertTrue(bus.getPassengers().get(0).isOnVehicle());
  }

  /**
   * Checks that nobody else can get on once the bus is full.
   */
  @Test
  public void testLoadPassengerWhenFull() {
    Bus bus = createBus();
    for (int i = 0; i < 5; i++) {
      bus.loadPassenger(new Passenger(1, "Rider" + i));
    }

    int loaded = bus.loadPassenger(new Passenger(1, "Extra"));

    assertEquals(0, loaded);
    assertEquals(5, bus.getPassengers().size());
  }

  /**
   * Checks that moving the bus sends it on to the second stop.
   */
  @Test
  public void testMove() {
    Bus bus = createBus();
    bus.move();
    assertEquals("test stop 2", bus.getNextStop().getName());
  }

  /**
   * Checks that update moves the bus the same way move does.
   */
  @Test
  public void testUpdate() {
    Bus bus = createBus();
    bus.update();
    assertEquals(1, bus.getNextStop().getId());
  }

  /**
   * Checks that a bus that was just made has not finished its trip.
   */
  @Test
  public void testIsTripCompleteInitially() {
    Bus bus = createBus();
    assertFalse(bus.isTripComplete());
  }

  /**
   * Checks that the report prints the main bus info.
   */
  @Test
  public void testBusReport() {
    try {
      Bus bus = createBus();

      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      bus.report(testStream);
      outputStream.flush();
      String output = new String(outputStream.toByteArray(), charset);

      testStream.close();
      outputStream.close();

      assertTrue(output.contains("####Bus Info Start####"));
      assertTrue(output.contains("ID: 0"));
      assertTrue(output.contains("Name: testRouteOut0"));
      assertTrue(output.contains("Speed: 1.0"));
      assertTrue(output.contains("Capacity: 5"));
      assertTrue(output.contains("Num of passengers: 0"));
      assertTrue(output.contains("####Bus Info End####"));
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Checks the CO2 for an empty bus and then for a bus with two riders.
   */
  @Test
  public void testGetCo2Emission() {
    Bus bus = createBus();
    assertEquals(4, bus.getCo2Emission());

    bus.loadPassenger(new Passenger(1, "Goldy"));
    bus.loadPassenger(new Passenger(1, "Gopher"));
    assertEquals(8, bus.getCo2Emission());
  }
}