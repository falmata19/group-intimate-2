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

public class TrainTest {

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
   * Builds a train on a small line with three stops going out and the same three coming back.
   *
   * @return the train used in the tests
   */
  public Train createTrain() {
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
    Route outRoute = new Route(10, "testLine", Route.TRAIN_LINE, "testRouteOut",
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
    Route inRoute = new Route(11, "testLine", Route.TRAIN_LINE, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    Line trainLine = new Line(outRoute, inRoute);
    return new Train(0, trainLine, 5, 1);
  }

  /**
   * Checks that a new train starts with the right id, name, capacity, speed, and first stop.
   */
  @Test
  public void testConstructorNormal() {
    Train train = createTrain();
    assertEquals(0, train.getId());
    assertEquals(5, train.getCapacity());
    assertEquals(1, train.getSpeed());
    assertEquals("testRouteOut0", train.getName());
    assertEquals(0, train.getNextStop().getId());
    assertEquals(44.972392, train.getPosition().getLatitude());
    assertEquals(-93.243774, train.getPosition().getLongitude());
    assertEquals(0, train.getPassengers().size());
  }

  /**
   * Checks that a passenger gets on the train when there is room.
   */
  @Test
  public void testLoadPassenger() {
    Train train = createTrain();
    Passenger rider = new Passenger(1, "Goldy");

    int loaded = train.loadPassenger(rider);

    assertEquals(1, loaded);
    assertEquals(1, train.getPassengers().size());
    assertTrue(train.getPassengers().get(0).isOnVehicle());
  }

  /**
   * Checks that nobody else can get on once the train is full.
   */
  @Test
  public void testLoadPassengerWhenFull() {
    Train train = createTrain();
    for (int i = 0; i < 5; i++) {
      train.loadPassenger(new Passenger(1, "Rider" + i));
    }

    int loaded = train.loadPassenger(new Passenger(1, "Extra"));

    assertEquals(0, loaded);
    assertEquals(5, train.getPassengers().size());
  }

  /**
   * Checks that moving the train sends it on to the second stop.
   */
  @Test
  public void testMove() {
    Train train = createTrain();
    train.move();
    assertEquals("test stop 2", train.getNextStop().getName());
  }

  /**
   * Checks that update moves the train the same way move does.
   */
  @Test
  public void testUpdate() {
    Train train = createTrain();
    train.update();
    assertEquals(1, train.getNextStop().getId());
  }

  /**
   * Checks that a train that was just made has not finished its trip.
   */
  @Test
  public void testIsTripCompleteInitially() {
    Train train = createTrain();
    assertFalse(train.isTripComplete());
  }

  /**
   * Checks that the report prints the main train info.
   */
  @Test
  public void testTrainReport() {
    try {
      Train train = createTrain();

      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      train.report(testStream);
      outputStream.flush();
      String output = new String(outputStream.toByteArray(), charset);

      testStream.close();
      outputStream.close();

      assertTrue(output.contains("####Train Info Start####"));
      assertTrue(output.contains("ID: 0"));
      assertTrue(output.contains("Name: testRouteOut0"));
      assertTrue(output.contains("Speed: 1.0"));
      assertTrue(output.contains("Capacity: 5"));
      assertTrue(output.contains("Num of passengers: 0"));
      assertTrue(output.contains("####Train Info End####"));
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Checks the CO2 for an empty train and then for a train with one rider.
   */
  @Test
  public void testGetCo2Emission() {
    Train train = createTrain();
    assertEquals(6, train.getCo2Emission());

    train.loadPassenger(new Passenger(1, "Goldy"));
    assertEquals(9, train.getCo2Emission());
  }
}