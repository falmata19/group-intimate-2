package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StorageFacilityTest {

  /**
   * Tests that the default constructor starts with no vehicles of any type.
   */
  @Test
  public void testDefaultConstructor() {
    StorageFacility storageFacility = new StorageFacility();
    assertEquals(0, storageFacility.getSmallBusesNum());
    assertEquals(0, storageFacility.getLargeBusesNum());
    assertEquals(0, storageFacility.getElectricTrainsNum());
    assertEquals(0, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that the constructor stores the number of each type of vehicle.
   */
  @Test
  public void testConstructor() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    assertEquals(10, storageFacility.getSmallBusesNum());
    assertEquals(11, storageFacility.getLargeBusesNum());
    assertEquals(12, storageFacility.getElectricTrainsNum());
    assertEquals(13, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that removing a small bus from storage lowers only that count by one.
   */
  @Test
  public void testDecrementSmallBus() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.decrementSmallBus();
    assertEquals(9, storageFacility.getSmallBusesNum());
    assertEquals(11, storageFacility.getLargeBusesNum());
    assertEquals(12, storageFacility.getElectricTrainsNum());
    assertEquals(13, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that putting a small bus back in storage raises only that count by one.
   */
  @Test
  public void testIncrementSmallBus() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.incrementSmallBus();
    assertEquals(11, storageFacility.getSmallBusesNum());
    assertEquals(11, storageFacility.getLargeBusesNum());
    assertEquals(12, storageFacility.getElectricTrainsNum());
    assertEquals(13, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that the number of small buses can be set without changing the other counts.
   */
  @Test
  public void testSetSmallBusesNum() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.setSmallBusesNum(4);
    assertEquals(4, storageFacility.getSmallBusesNum());
    assertEquals(11, storageFacility.getLargeBusesNum());
    assertEquals(12, storageFacility.getElectricTrainsNum());
    assertEquals(13, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that removing a large bus from storage lowers only that count by one.
   */
  @Test
  public void testDecrementLargeBus() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.decrementLargeBus();
    assertEquals(10, storageFacility.getSmallBusesNum());
    assertEquals(10, storageFacility.getLargeBusesNum());
    assertEquals(12, storageFacility.getElectricTrainsNum());
    assertEquals(13, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that putting a large bus back in storage raises only that count by one.
   */
  @Test
  public void testIncrementLargeBus() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.incrementLargeBus();
    assertEquals(10, storageFacility.getSmallBusesNum());
    assertEquals(12, storageFacility.getLargeBusesNum());
    assertEquals(12, storageFacility.getElectricTrainsNum());
    assertEquals(13, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that the number of large buses can be set without changing the other counts.
   */
  @Test
  public void testSetLargeBusesNum() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.setLargeBusesNum(4);
    assertEquals(10, storageFacility.getSmallBusesNum());
    assertEquals(4, storageFacility.getLargeBusesNum());
    assertEquals(12, storageFacility.getElectricTrainsNum());
    assertEquals(13, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that removing a electric train from storage lowers only that count by one.
   */
  @Test
  public void testDecrementElectricTrain() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.decrementElectricTrain();
    assertEquals(10, storageFacility.getSmallBusesNum());
    assertEquals(11, storageFacility.getLargeBusesNum());
    assertEquals(11, storageFacility.getElectricTrainsNum());
    assertEquals(13, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that putting a electric train back in storage raises only that count by one.
   */
  @Test
  public void testIncrementElectricTrain() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.incrementElectricTrain();
    assertEquals(10, storageFacility.getSmallBusesNum());
    assertEquals(11, storageFacility.getLargeBusesNum());
    assertEquals(13, storageFacility.getElectricTrainsNum());
    assertEquals(13, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that the number of electric trains can be set without changing the other counts.
   */
  @Test
  public void testSetElectricTrainsNum() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.setElectricTrainsNum(4);
    assertEquals(10, storageFacility.getSmallBusesNum());
    assertEquals(11, storageFacility.getLargeBusesNum());
    assertEquals(4, storageFacility.getElectricTrainsNum());
    assertEquals(13, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that removing a diesel train from storage lowers only that count by one.
   */
  @Test
  public void testDecrementDieselTrain() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.decrementDieselTrain();
    assertEquals(10, storageFacility.getSmallBusesNum());
    assertEquals(11, storageFacility.getLargeBusesNum());
    assertEquals(12, storageFacility.getElectricTrainsNum());
    assertEquals(12, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that putting a diesel train back in storage raises only that count by one.
   */
  @Test
  public void testIncrementDieselTrain() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.incrementDieselTrain();
    assertEquals(10, storageFacility.getSmallBusesNum());
    assertEquals(11, storageFacility.getLargeBusesNum());
    assertEquals(12, storageFacility.getElectricTrainsNum());
    assertEquals(14, storageFacility.getDieselTrainsNum());
  }

  /**
   * Tests that the number of diesel trains can be set without changing the other counts.
   */
  @Test
  public void testSetDieselTrainsNum() {
    StorageFacility storageFacility = new StorageFacility(10, 11, 12, 13);
    storageFacility.setDieselTrainsNum(4);
    assertEquals(10, storageFacility.getSmallBusesNum());
    assertEquals(11, storageFacility.getLargeBusesNum());
    assertEquals(12, storageFacility.getElectricTrainsNum());
    assertEquals(4, storageFacility.getDieselTrainsNum());
  }
}
