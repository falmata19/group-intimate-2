package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

public class PassengerTest {

  private String reportOf(Passenger passenger) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    passenger.report(printStream);
    printStream.flush();
    return outputStream.toString();
  }

  /**
   * Tests the state of a passenger after using the constructor.
   */
  @Test
  public void testConstructor() {
    Passenger passenger = new Passenger(3, "Goldy");
    assertEquals(3, passenger.getDestination());
    assertFalse(passenger.isOnVehicle());
  }

  /**
   * Tests that a passenger waiting at a stop accumulates wait time on each update.
   */
  @Test
  public void testPasUpdateWaitingAtStop() {
    Passenger passenger = new Passenger(3, "Goldy");
    passenger.pasUpdate();
    passenger.pasUpdate();
    String report = reportOf(passenger);
    assertTrue(report.contains("Wait at stop: 2"));
    assertTrue(report.contains("Time on vehicle: 0"));
  }

  /**
   * Tests that a passenger on a vehicle accumulates ride time on each update.
   */
  @Test
  public void testPasUpdateOnVehicle() {
    Passenger passenger = new Passenger(3, "Goldy");
    passenger.setOnVehicle();
    passenger.pasUpdate();
    String report = reportOf(passenger);
    assertTrue(report.contains("Wait at stop: 0"));
    assertTrue(report.contains("Time on vehicle: 2"));
  }

  /**
   * Tests that setting a passenger on a vehicle starts the ride time at one.
   */
  @Test
  public void testSetOnVehicle() {
    Passenger passenger = new Passenger(3, "Goldy");
    passenger.setOnVehicle();
    assertTrue(passenger.isOnVehicle());
    assertTrue(reportOf(passenger).contains("Time on vehicle: 1"));
  }

  /**
   * Tests that a passenger is not on a vehicle until set on one.
   */
  @Test
  public void testIsOnVehicle() {
    Passenger passenger = new Passenger(3, "Goldy");
    assertFalse(passenger.isOnVehicle());
    passenger.setOnVehicle();
    assertTrue(passenger.isOnVehicle());
  }

  /**
   * Tests that the destination stop id passed to the constructor is returned.
   */
  @Test
  public void testGetDestination() {
    Passenger passenger = new Passenger(7, "Gopher");
    assertEquals(7, passenger.getDestination());
  }

  /**
   * Tests the report output of a new passenger.
   */
  @Test
  public void testReport() {
    Passenger passenger = new Passenger(3, "Goldy");
    String expected =
        "####Passenger Info Start####" + System.lineSeparator()
            + "Name: Goldy" + System.lineSeparator()
            + "Destination: 3" + System.lineSeparator()
            + "Wait at stop: 0" + System.lineSeparator()
            + "Time on vehicle: 0" + System.lineSeparator()
            + "####Passenger Info End####" + System.lineSeparator();
    assertEquals(expected, reportOf(passenger));
  }

}
