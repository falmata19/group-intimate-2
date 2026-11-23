package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TrainStrategyNightTest {

  /**
   * Checks that the night strategy alternates electric and diesel.
   */
  @Test
  public void testGetNextTrainType() {
    TrainStrategy strategy = new TrainStrategyNight();
    String[] expected = {
        ElectricTrain.ELECTRIC_TRAIN_VEHICLE, DieselTrain.DIESEL_TRAIN_VEHICLE,
        ElectricTrain.ELECTRIC_TRAIN_VEHICLE, DieselTrain.DIESEL_TRAIN_VEHICLE,
        ElectricTrain.ELECTRIC_TRAIN_VEHICLE
    };
    for (String trainType : expected) {
      assertEquals(trainType, strategy.getNextTrainType());
    }
  }

  /**
   * Checks that two night strategies keep their own place in the sequence.
   */
  @Test
  public void testStrategiesKeepSeparateState() {
    TrainStrategy first = new TrainStrategyNight();
    TrainStrategy second = new TrainStrategyNight();
    first.getNextTrainType();
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, second.getNextTrainType());
    assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, first.getNextTrainType());
  }
}