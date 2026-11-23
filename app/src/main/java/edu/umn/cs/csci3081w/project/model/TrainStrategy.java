package edu.umn.cs.csci3081w.project.model;

/**
 * Decides which type of train is deployed next.
 */
public interface TrainStrategy {

  /**
   * Returns the type of the next train and moves on in the sequence.
   *
   * @return type of the next train, electric or diesel
   */
  String getNextTrainType();
}