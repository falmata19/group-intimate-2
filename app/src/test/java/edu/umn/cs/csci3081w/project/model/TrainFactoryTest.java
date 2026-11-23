package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainFactoryTest {
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
    Route outRoute = new Route(10, "testLine", Route.TRAIN_LINE, "testRouteOut", stopsOut,
        distancesOut, new RandomPassengerGenerator(stopsOut, probabilitiesOut));

    List<Stop> stopsIn = new ArrayList<>();
    stopsIn.add(lastStop);
    stopsIn.add(firstStop);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.9712663713083954);
    List<Double> probabilitiesIn = new ArrayList<>();
    probabilitiesIn.add(.3);
    probabilitiesIn.add(.0);
    Route inRoute = new Route(11, "testLine", Route.TRAIN_LINE, "testRouteIn", stopsIn,
        distancesIn, new RandomPassengerGenerator(stopsIn, probabilitiesIn));

    line = new Line(outRoute, inRoute);
  }

  /**
   * Checks that a factory made at 8am uses the day strategy.
   */
  @Test
  public void testDayStrategyAtEightAm() {
    TrainFactory factory = new TrainFactory(LocalDateTime.of(2026, 11, 22, 8, 0));
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, factory.getNextTrainType());
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, factory.getNextTrainType());
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, factory.getNextTrainType());
    assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, factory.getNextTrainType());
  }

  /**
   * Checks that a factory made one minute before 6pm still uses the day strategy.
   */
  @Test
  public void testDayStrategyBeforeSixPm() {
    TrainFactory factory = new TrainFactory(LocalDateTime.of(2026, 11, 22, 17, 59));
    factory.getNextTrainType();
    factory.getNextTrainType();
    factory.getNextTrainType();
    assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, factory.getNextTrainType());
  }

  /**
   * Checks that a factory made at 6pm uses the night strategy.
   */
  @Test
  public void testNightStrategyAtSixPm() {
    TrainFactory factory = new TrainFactory(LocalDateTime.of(2026, 11, 22, 18, 0));
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, factory.getNextTrainType());
    assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, factory.getNextTrainType());
  }

  /**
   * Checks that a factory made one minute before 8am still uses the night strategy.
   */
  @Test
  public void testNightStrategyBeforeEightAm() {
    TrainFactory factory = new TrainFactory(LocalDateTime.of(2026, 11, 22, 7, 59));
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, factory.getNextTrainType());
    assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, factory.getNextTrainType());
  }

  /**
   * Checks that an electric train is made with the given id.
   */
  @Test
  public void testGenerateElectricTrain() {
    TrainFactory factory = new TrainFactory(LocalDateTime.of(2026, 11, 22, 12, 0));
    Train train = factory.generate(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, 2000, line);
    assertTrue(train instanceof ElectricTrain);
    assertEquals(2000, train.getId());
    assertEquals(Train.CAPACITY, train.getCapacity());
  }

  /**
   * Checks that a diesel train is made with the given id.
   */
  @Test
  public void testGenerateDieselTrain() {
    TrainFactory factory = new TrainFactory(LocalDateTime.of(2026, 11, 22, 12, 0));
    Train train = factory.generate(DieselTrain.DIESEL_TRAIN_VEHICLE, 2001, line);
    assertTrue(train instanceof DieselTrain);
    assertEquals(2001, train.getId());
    assertEquals(Train.CAPACITY, train.getCapacity());
  }
}