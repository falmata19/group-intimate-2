package edu.umn.cs.csci3081w.project.model;

/**
 * Showing a facility that contains the number of trains and buses.
 */
public class StorageFacility {
  private int busesNum;
  private int trainsNum;

  /**
   * Makes a storage facility with 0 buses and 0 trains.
   */
  public StorageFacility() {
    busesNum = 0;
    trainsNum = 0;
  }

  /**
   * Storage facility constructor.
   *
   * @param busesNum  number of buses in storage
   * @param trainsNum number of trains in storage
   */
  public StorageFacility(int busesNum, int trainsNum) {
    this.busesNum = busesNum;
    this.trainsNum = trainsNum;
  }

  /**
   * Removes one bus from storage.
   */
  public void decrementBus() {
    busesNum--;
  }

  /**
   * Adds one bus back to storage.
   */
  public void incrementBus() {
    busesNum++;
  }

  /**
   * Removes one train from storage.
   */
  public void decrementTrain() {
    trainsNum--;
  }

  /**
   * Adds one train back to storage.
   */
  public void incrementTrain() {
    trainsNum++;
  }

  /**
   * Returns the number of buses.
   *
   * @return number of buses
   */
  public int getBusesNum() {
    return busesNum;
  }

  /**
   * Returns the number of trains.
   *
   * @return number of trains
   */
  public int getTrainsNum() {
    return trainsNum;
  }

  /**
   * Sets the number of buses.
   *
   * @param busesNum number of buses
   */
  public void setBusesNum(int busesNum) {
    this.busesNum = busesNum;
  }

  /**
   * Sets the number of trains.
   *
   * @param trainsNum number of trains
   */
  public void setTrainsNum(int trainsNum) {
    this.trainsNum = trainsNum;
  }
}



