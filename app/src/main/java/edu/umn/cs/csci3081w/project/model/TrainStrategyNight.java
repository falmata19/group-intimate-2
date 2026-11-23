package edu.umn.cs.csci3081w.project.model;

/**
 * Deploys trains in the repeating order electric, diesel.
 */
public class TrainStrategyNight implements TrainStrategy {
  private static final String[] SEQUENCE = {
      ElectricTrain.ELECTRIC_TRAIN_VEHICLE, DieselTrain.DIESEL_TRAIN_VEHICLE
  };
  private int position = 0;

  /**
   * Creates the night strategy, which starts at the first train of its sequence.
   */
  public TrainStrategyNight() {
  }

  /**
   * Returns the type of the next train and moves on in the sequence.
   *
   * @return type of the next train, electric or diesel
   */
  @Override
  public String getNextTrainType() {
    String trainType = SEQUENCE[position];
    position = (position + 1) % SEQUENCE.length;
    return trainType;
  }
}