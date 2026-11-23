package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the RandomPassengerGenerator class. The tests use the deterministic mode of the
 * generator, where the random value is always 0.1, so they do not depend on chance.
 */
public class RandomPassengerGeneratorTest {

  private boolean oldGeneratorDeterministic;
  private boolean oldFactoryDeterministic;
  private int oldDestinationCount;
  private int oldNamesCount;
  private List<Stop> stops;

  /**
   * Turns on the deterministic modes and builds three stops with consecutive ids.
   */
  @BeforeEach
  public void setUp() {
    oldGeneratorDeterministic = RandomPassengerGenerator.DETERMINISTIC;
    oldFactoryDeterministic = PassengerFactory.DETERMINISTIC;
    oldDestinationCount = PassengerFactory.DETERMINISTIC_DESTINATION_COUNT;
    oldNamesCount = PassengerFactory.DETERMINISTIC_NAMES_COUNT;
    RandomPassengerGenerator.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    stops = new ArrayList<Stop>();
    stops.add(new Stop(10, "test stop 1", new Position(-93.243774, 44.972392)));
    stops.add(new Stop(11, "test stop 2", new Position(-93.235071, 44.973581)));
    stops.add(new Stop(12, "test stop 3", new Position(-93.226632, 44.975392)));
  }

  /**
   * Puts the static test flags back to what they were.
   */
  @AfterEach
  public void tearDown() {
    RandomPassengerGenerator.DETERMINISTIC = oldGeneratorDeterministic;
    PassengerFactory.DETERMINISTIC = oldFactoryDeterministic;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = oldDestinationCount;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = oldNamesCount;
  }

  private List<Double> probabilities(double first, double second, double third) {
    List<Double> probabilities = new ArrayList<Double>();
    probabilities.add(first);
    probabilities.add(second);
    probabilities.add(third);
    return probabilities;
  }

  /**
   * Tests that nothing is generated, and no stop gets a passenger, when every probability is 0.
   */
  @Test
  public void testGeneratePassengersAllProbabilitiesZero() {
    RandomPassengerGenerator generator =
        new RandomPassengerGenerator(stops, probabilities(0, 0, 0));
    assertEquals(0, generator.generatePassengers());
    for (Stop stop : stops) {
      assertEquals(0, stop.getPassengers().size());
    }
  }

  /**
   * Tests that passengers are added to a stop that is not the last one when its probability is
   * above the random value, and that they are added to that stop only. With probability 0.5
   * and the random value 0.1, three passengers are generated (at 0.5, 0.25 and 0.125).
   */
  @Test
  public void testGeneratePassengersAddsPassengersToNonFinalStop() {
    RandomPassengerGenerator generator =
        new RandomPassengerGenerator(stops, probabilities(0.5, 0, 0));
    assertEquals(3, generator.generatePassengers());
    assertEquals(3, stops.get(0).getPassengers().size());
    assertEquals(0, stops.get(1).getPassengers().size());
    assertEquals(0, stops.get(2).getPassengers().size());
  }

  /**
   * Tests that the generated passengers go to a stop after the one they wait at, up to the last
   * stop.
   */
  @Test
  public void testGeneratePassengersDestinationsAreAfterTheStop() {
    RandomPassengerGenerator generator =
        new RandomPassengerGenerator(stops, probabilities(0.5, 0.5, 0));
    assertTrue(generator.generatePassengers() > 0);
    for (Passenger passenger : stops.get(0).getPassengers()) {
      assertTrue(passenger.getDestination() > 10 && passenger.getDestination() <= 12);
    }
    for (Passenger passenger : stops.get(1).getPassengers()) {
      assertTrue(passenger.getDestination() > 11 && passenger.getDestination() <= 12);
    }
  }

  /**
   * Tests that no passenger is generated at the last stop, even with a high probability there.
   */
  @Test
  public void testGeneratePassengersNothingAtLastStop() {
    RandomPassengerGenerator generator =
        new RandomPassengerGenerator(stops, probabilities(0, 0, 0.5));
    assertEquals(0, generator.generatePassengers());
    assertEquals(0, stops.get(2).getPassengers().size());
  }

  /**
   * Tests that nothing is generated when the probability is not above the random value of 0.1.
   */
  @Test
  public void testGeneratePassengersProbabilityBelowRandomValue() {
    RandomPassengerGenerator generator =
        new RandomPassengerGenerator(stops, probabilities(0.05, 0.05, 0));
    assertEquals(0, generator.generatePassengers());
  }

  /**
   * Tests that the generator keeps copies of the stop and probability lists passed to it.
   */
  @Test
  public void testConstructorCopiesLists() {
    List<Double> probabilities = probabilities(0.5, 0, 0);
    RandomPassengerGenerator generator = new RandomPassengerGenerator(stops, probabilities);
    stops.clear();
    probabilities.clear();
    assertEquals(3, generator.getStops().size());
    assertEquals(3, generator.getProbabilities().size());
  }
}
