package edu.umn.cs.csci3081w.project.model;

import java.time.LocalDateTime;

/**
 * Makes trains of the type chosen by the strategy for the time of day.
 */
public class TrainFactory {
  private TrainStrategy strategy;

  /**
   * Uses the day strategy from 8am up to 6pm and the night strategy the rest of the time.
   *
   * @param currentTime time used to pick the strategy
   */
  public TrainFactory(LocalDateTime currentTime) {
    if (currentTime.getHour() >= 8 && currentTime.getHour() < 18) {
      strategy = new TrainStrategyDay();
    } else {
      strategy = new TrainStrategyNight();
    }
  }

  /**
   * Returns the type of the next train to deploy.
   *
   * @return type of the next train, electric or diesel
   */
  public String getNextTrainType() {
    return strategy.getNextTrainType();
  }

  /**
   * Makes a train of the given type.
   *
   * @param trainType type of train to make
   * @param id        train id
   * @param line      line the train serves
   * @return the new train
   */
  public Train generate(String trainType, int id, Line line) {
    if (trainType.equals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE)) {
      return new ElectricTrain(id, line, Train.CAPACITY, Train.SPEED);
    }
    return new DieselTrain(id, line, Train.CAPACITY, Train.SPEED);
  }
}