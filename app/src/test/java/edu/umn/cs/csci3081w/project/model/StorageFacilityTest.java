package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StorageFacilityTest {

  /**
   * Tests that the default constructor starts with no buses and no trains.
   */
  @Test
  public void testDefaultConstructor() {
    StorageFacility storageFacility = new StorageFacility();
    assertEquals(0, storageFacility.getBusesNum());
    assertEquals(0, storageFacility.getTrainsNum());
  }

  /**
   * Tests that the constructor stores the number of buses and trains.
   */
  @Test
  public void testConstructor() {
    StorageFacility storageFacility = new StorageFacility(10, 11);
    assertEquals(10, storageFacility.getBusesNum());
    assertEquals(11, storageFacility.getTrainsNum());
  }

  /**
   * Tests that removing a bus from storage lowers the bus count by one.
   */
  @Test
  public void testDecrementBus() {
    StorageFacility storageFacility = new StorageFacility(10, 11);
    storageFacility.decrementBus();
    assertEquals(9, storageFacility.getBusesNum());
    assertEquals(11, storageFacility.getTrainsNum());
  }

  /**
   * Tests that returning a bus to storage raises the bus count by one.
   */
  @Test
  public void testIncrementBus() {
    StorageFacility storageFacility = new StorageFacility(10, 11);
    storageFacility.incrementBus();
    assertEquals(11, storageFacility.getBusesNum());
    assertEquals(11, storageFacility.getTrainsNum());
  }

  /**
   * Tests that removing a train from storage lowers the train count by one.
   */
  @Test
  public void testDecrementTrain() {
    StorageFacility storageFacility = new StorageFacility(10, 11);
    storageFacility.decrementTrain();
    assertEquals(10, storageFacility.getBusesNum());
    assertEquals(10, storageFacility.getTrainsNum());
  }

  /**
   * Tests that returning a train to storage raises the train count by one.
   */
  @Test
  public void testIncrementTrain() {
    StorageFacility storageFacility = new StorageFacility(10, 11);
    storageFacility.incrementTrain();
    assertEquals(10, storageFacility.getBusesNum());
    assertEquals(12, storageFacility.getTrainsNum());
  }

  /**
   * Tests that the setters replace the number of buses and trains.
   */
  @Test
  public void testSetters() {
    StorageFacility storageFacility = new StorageFacility();
    storageFacility.setBusesNum(4);
    storageFacility.setTrainsNum(5);
    assertEquals(4, storageFacility.getBusesNum());
    assertEquals(5, storageFacility.getTrainsNum());
  }

}
