package edu.umn.cs.csci3081w.project.model;

/**
 * Gives out new ids for routes, stops, buses, and trains.
 */
public class Counter {

  public int routeIdCounter = 10;
  public int stopIdCounter = 100;
  public int busIdCounter = 1000;
  public int trainIdCounter = 2000;

  /**
   * Makes a counter that starts each id at its first number.
   */
  public Counter() {

  }

  /**
   * Gets the next route id and then adds one to the counter.
   *
   * @return the next route id
   */
  public int getRouteIdCounterAndIncrement() {
    return routeIdCounter++;
  }

  /**
   * Gets the next stop id and then adds one to the counter.
   *
   * @return the next stop id
   */
  public int getStopIdCounterAndIncrement() {
    return stopIdCounter++;
  }

  /**
   * Gets the next bus id and then adds one to the counter.
   *
   * @return the next bus id
   */
  public int getBusIdCounterAndIncrement() {
    return busIdCounter++;
  }

  /**
   * Gets the next train id and then adds one to the counter.
   *
   * @return the next train id
   */
  public int getTrainIdCounterAndIncrement() {
    return trainIdCounter++;
  }

}
