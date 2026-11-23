package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TrainStrategyDayTest {

  /**
   * Checks that the day strategy goes electric three times, then diesel, and then repeats.
   */
  @Test
  public void testGetNextTrainType() {
    TrainStrategy strategy = new TrainStrategyDay();
    String[] expected = {
        ElectricTrain.ELECTRIC_TRAIN_VEHICLE, ElectricTrain.ELECTRIC_TRAIN_VEHICLE,
        ElectricTrain.ELECTRIC_TRAIN_VEHICLE, DieselTrain.DIESEL_TRAIN_VEHICLE,
        ElectricTrain.ELECTRIC_TRAIN_VEHICLE, ElectricTrain.ELECTRIC_TRAIN_VEHICLE,
        ElectricTrain.ELECTRIC_TRAIN_VEHICLE, DieselTrain.DIESEL_TRAIN_VEHICLE
    };
    for (String trainType : expected) {
      assertEquals(trainType, strategy.getNextTrainType());
    }
  }

  /**
   * Checks that two day strategies keep their own place in the sequence.
   */
  @Test
  public void testStrategiesKeepSeparateState() {
    TrainStrategy first = new TrainStrategyDay();
    TrainStrategy second = new TrainStrategyDay();
    first.getNextTrainType();
    first.getNextTrainType();
    first.getNextTrainType();
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, second.getNextTrainType());
    assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, first.getNextTrainType());
  }
}