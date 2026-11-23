package edu.umn.cs.csci3081w.project.model;

/**
 * Decides which type of bus is deployed next.
 */
public interface BusStrategy {

  /**
   * Returns the type of the next bus to deploy and moves on to the following one in the sequence.
   *
   * @return type of the next bus, either small or large
   */
  String getNextBusType();
}
