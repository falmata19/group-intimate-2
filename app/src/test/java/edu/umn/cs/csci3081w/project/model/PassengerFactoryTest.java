package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the PassengerFactory class.
 */
public class PassengerFactoryTest {

  private boolean oldDeterministic;
  private int oldNamesCount;
  private int oldDestinationCount;

  /**
   * Remembers the static test flags of the factory, because the tests change them.
   */
  @BeforeEach
  public void setUp() {
    oldDeterministic = PassengerFactory.DETERMINISTIC;
    oldNamesCount = PassengerFactory.DETERMINISTIC_NAMES_COUNT;
    oldDestinationCount = PassengerFactory.DETERMINISTIC_DESTINATION_COUNT;
  }

  /**
   * Puts the static test flags of the factory back to what they were.
   */
  @AfterEach
  public void tearDown() {
    PassengerFactory.DETERMINISTIC = oldDeterministic;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = oldNamesCount;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = oldDestinationCount;
  }

  /**
   * Tests that the random destination is always greater than the current stop and not greater
   * than the last stop, over 100 calls.
   */
  @Test
  public void testGenerateRandomDestinationInRange() {
    PassengerFactory.DETERMINISTIC = false;
    for (int i = 0; i < 100; i++) {
      Passenger passenger = PassengerFactory.generate(3, 8);
      assertTrue(passenger.getDestination() > 3);
      assertTrue(passenger.getDestination() <= 8);
    }
  }

  /**
   * Tests that the random destination is the last stop when it is the only stop after the
   * current one.
   */
  @Test
  public void testGenerateRandomWithOneStopLeft() {
    PassengerFactory.DETERMINISTIC = false;
    for (int i = 0; i < 20; i++) {
      assertEquals(4, PassengerFactory.generate(3, 4).getDestination());
    }
  }

  /**
   * Tests that the deterministic destination is also greater than the current stop and not
   * greater than the last stop, over 100 calls.
   */
  @Test
  public void testGenerateDeterministicDestinationInRange() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    for (int i = 0; i < 100; i++) {
      Passenger passenger = PassengerFactory.generate(3, 8);
      assertTrue(passenger.getDestination() > 3);
      assertTrue(passenger.getDestination() <= 8);
    }
  }

  /**
   * Tests that a random name is not empty and starts with a capital letter.
   */
  @Test
  public void testNameGenerationRandomIsNotEmpty() {
    PassengerFactory.DETERMINISTIC = false;
    for (int i = 0; i < 100; i++) {
      String name = PassengerFactory.nameGeneration();
      assertFalse(name.isEmpty());
      assertTrue(Character.isUpperCase(name.charAt(0)));
    }
  }

  /**
   * Tests that the deterministic names come in the fixed order and start over after the last.
   */
  @Test
  public void testNameGenerationDeterministicOrder() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    assertEquals("Goldy", PassengerFactory.nameGeneration());
    assertEquals("President", PassengerFactory.nameGeneration());
    assertEquals("Coach", PassengerFactory.nameGeneration());
    assertEquals("Goldy", PassengerFactory.nameGeneration());
  }
}
