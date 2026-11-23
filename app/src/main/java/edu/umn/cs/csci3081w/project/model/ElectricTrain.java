package edu.umn.cs.csci3081w.project.model;

/**
 * An electric train, which does not produce any CO2.
 */
public class ElectricTrain extends Train {
  public static final String ELECTRIC_TRAIN_VEHICLE = "ELECTRIC_TRAIN_VEHICLE";

  /**
   * Constructor for an electric train.
   *
   * @param id       train identifier
   * @param line     line the train serves
   * @param capacity capacity of the train
   * @param speed    speed of the train
   */
  public ElectricTrain(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);
  }

  /**
   * Returns the CO2 the electric train is producing right now.
   */
  @Override
  public int getCo2Emission() {
    return 0;
  }
}
