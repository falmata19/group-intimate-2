package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the PassengerLoader class.
 */
public class PassengerLoaderTest {

  private PassengerLoader loader;
  private List<Passenger> passengers;

  /**
   * Makes a loader and an empty passenger list before each test.
   */
  @BeforeEach
  public void setUp() {
    loader = new PassengerLoader();
    passengers = new ArrayList<Passenger>();
  }

  /**
   * Tests that a passenger is added and put on the vehicle when there is room, and that the
   * method returns 1.
   */
  @Test
  public void testLoadPassengerWithRoom() {
    Passenger passenger = new Passenger(3, "Goldy");
    assertEquals(1, loader.loadPassenger(passenger, 2, passengers));
    assertEquals(1, passengers.size());
    assertSame(passenger, passengers.get(0));
    assertTrue(passenger.isOnVehicle());
  }

  /**
   * Tests that a passenger is still added when the vehicle has exactly one free place.
   */
  @Test
  public void testLoadPassengerFillsLastPlace() {
    passengers.add(new Passenger(2, "President"));
    assertEquals(1, loader.loadPassenger(new Passenger(3, "Coach"), 2, passengers));
    assertEquals(2, passengers.size());
  }

  /**
   * Tests that nothing is added and 0 is returned when the vehicle is full.
   */
  @Test
  public void testLoadPassengerWhenFull() {
    passengers.add(new Passenger(2, "President"));
    passengers.add(new Passenger(3, "Coach"));
    Passenger extra = new Passenger(4, "Goldy");
    assertEquals(0, loader.loadPassenger(extra, 2, passengers));
    assertEquals(2, passengers.size());
    assertFalse(passengers.contains(extra));
    assertFalse(extra.isOnVehicle());
  }
}
