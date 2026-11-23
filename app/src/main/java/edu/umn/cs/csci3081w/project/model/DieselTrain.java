package edu.umn.cs.csci3081w.project.model;

/**
 * A diesel train, which produces CO2 for every passenger on board.
 */
public class DieselTrain extends Train {
  public static final String DIESEL_TRAIN_VEHICLE = "DIESEL_TRAIN_VEHICLE";

  /**
   * Constructor for a diesel train.
   *
   * @param id       train identifier
   * @param line     line the train serves
   * @param capacity capacity of the train
   * @param speed    speed of the train
   */
  public DieselTrain(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);
  }

  /**
   * Returns the CO2 the diesel train is producing right now.
   */
  @Override
  public int getCo2Emission() {
    return getPassengers().size() * 3 + 6;
  }
}
