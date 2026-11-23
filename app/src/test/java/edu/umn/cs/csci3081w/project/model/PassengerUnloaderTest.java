package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the PassengerUnloader class.
 */
public class PassengerUnloaderTest {

  private PassengerUnloader unloader;
  private Stop stop;
  private List<Passenger> passengers;

  /**
   * Makes an unloader, a stop with id 5, and an empty passenger list before each test.
   */
  @BeforeEach
  public void setUp() {
    unloader = new PassengerUnloader();
    stop = new Stop(5, "test stop", new Position(-93.243774, 44.972392));
    passengers = new ArrayList<Passenger>();
  }

  /**
   * Tests that only the passengers whose destination is the stop are removed, and that the
   * method returns how many were removed.
   */
  @Test
  public void testUnloadPassengersRemovesOnlyMatchingDestination() {
    Passenger stays1 = new Passenger(6, "Goldy");
    Passenger stays2 = new Passenger(4, "President");
    passengers.add(stays1);
    passengers.add(new Passenger(5, "Coach"));
    passengers.add(stays2);
    passengers.add(new Passenger(5, "Bel"));
    assertEquals(2, unloader.unloadPassengers(passengers, stop));
    assertEquals(2, passengers.size());
    assertSame(stays1, passengers.get(0));
    assertSame(stays2, passengers.get(1));
  }

  /**
   * Tests that no passenger is removed and 0 is returned when nobody has the stop as
   * destination.
   */
  @Test
  public void testUnloadPassengersNoMatch() {
    passengers.add(new Passenger(6, "Goldy"));
    passengers.add(new Passenger(7, "Coach"));
    assertEquals(0, unloader.unloadPassengers(passengers, stop));
    assertEquals(2, passengers.size());
  }

  /**
   * Tests that every passenger is removed when all of them have the stop as destination.
   */
  @Test
  public void testUnloadPassengersAllMatch() {
    passengers.add(new Passenger(5, "Goldy"));
    passengers.add(new Passenger(5, "Coach"));
    assertEquals(2, unloader.unloadPassengers(passengers, stop));
    assertEquals(0, passengers.size());
  }

  /**
   * Tests that an empty passenger list gives 0 and stays empty.
   */
  @Test
  public void testUnloadPassengersEmptyList() {
    assertEquals(0, unloader.unloadPassengers(passengers, stop));
    assertEquals(0, passengers.size());
  }
}
