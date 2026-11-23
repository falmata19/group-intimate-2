package edu.umn.cs.csci3081w.project.model;

/**
 * Deploys trains in the repeating order electric, electric, electric, diesel.
 */
public class TrainStrategyDay implements TrainStrategy {
  private static final String[] SEQUENCE = {
      ElectricTrain.ELECTRIC_TRAIN_VEHICLE, ElectricTrain.ELECTRIC_TRAIN_VEHICLE,
      ElectricTrain.ELECTRIC_TRAIN_VEHICLE, DieselTrain.DIESEL_TRAIN_VEHICLE
  };
  private int position = 0;

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