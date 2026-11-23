package edu.umn.cs.csci3081w.project.model;

/**
 * Showing a facility that contains the number of small buses, large buses, electric trains, and
 * diesel trains.
 */
public class StorageFacility {
  private int smallBusesNum;
  private int largeBusesNum;
  private int electricTrainsNum;
  private int dieselTrainsNum;

  /**
   * Makes a storage facility with no vehicles.
   */
  public StorageFacility() {
    smallBusesNum = 0;
    largeBusesNum = 0;
    electricTrainsNum = 0;
    dieselTrainsNum = 0;
  }

  /**
   * Storage facility constructor.
   *
   * @param smallBusesNum     number of small buses in storage
   * @param largeBusesNum     number of large buses in storage
   * @param electricTrainsNum number of electric trains in storage
   * @param dieselTrainsNum   number of diesel trains in storage
   */
  public StorageFacility(int smallBusesNum, int largeBusesNum, int electricTrainsNum,
                         int dieselTrainsNum) {
    this.smallBusesNum = smallBusesNum;
    this.largeBusesNum = largeBusesNum;
    this.electricTrainsNum = electricTrainsNum;
    this.dieselTrainsNum = dieselTrainsNum;
  }

  /**
   * Removes one small bus from storage.
   */
  public void decrementSmallBus() {
    smallBusesNum--;
  }

  /**
   * Adds one small bus back to storage.
   */
  public void incrementSmallBus() {
    smallBusesNum++;
  }

  /**
   * Removes one large bus from storage.
   */
  public void decrementLargeBus() {
    largeBusesNum--;
  }

  /**
   * Adds one large bus back to storage.
   */
  public void incrementLargeBus() {
    largeBusesNum++;
  }

  /**
   * Removes one electric train from storage.
   */
  public void decrementElectricTrain() {
    electricTrainsNum--;
  }

  /**
   * Adds one electric train back to storage.
   */
  public void incrementElectricTrain() {
    electricTrainsNum++;
  }

  /**
   * Removes one diesel train from storage.
   */
  public void decrementDieselTrain() {
    dieselTrainsNum--;
  }

  /**
   * Adds one diesel train back to storage.
   */
  public void incrementDieselTrain() {
    dieselTrainsNum++;
  }

  /**
   * Returns the number of small buses.
   *
   * @return number of small buses
   */
  public int getSmallBusesNum() {
    return smallBusesNum;
  }

  /**
   * Returns the number of large buses.
   *
   * @return number of large buses
   */
  public int getLargeBusesNum() {
    return largeBusesNum;
  }

  /**
   * Returns the number of electric trains.
   *
   * @return number of electric trains
   */
  public int getElectricTrainsNum() {
    return electricTrainsNum;
  }

  /**
   * Returns the number of diesel trains.
   *
   * @return number of diesel trains
   */
  public int getDieselTrainsNum() {
    return dieselTrainsNum;
  }

  /**
   * Sets the number of small buses.
   *
   * @param smallBusesNum number of small buses
   */
  public void setSmallBusesNum(int smallBusesNum) {
    this.smallBusesNum = smallBusesNum;
  }

  /**
   * Sets the number of large buses.
   *
   * @param largeBusesNum number of large buses
   */
  public void setLargeBusesNum(int largeBusesNum) {
    this.largeBusesNum = largeBusesNum;
  }

  /**
   * Sets the number of electric trains.
   *
   * @param electricTrainsNum number of electric trains
   */
  public void setElectricTrainsNum(int electricTrainsNum) {
    this.electricTrainsNum = electricTrainsNum;
  }

  /**
   * Sets the number of diesel trains.
   *
   * @param dieselTrainsNum number of diesel trains
   */
  public void setDieselTrainsNum(int dieselTrainsNum) {
    this.dieselTrainsNum = dieselTrainsNum;
  }
}
