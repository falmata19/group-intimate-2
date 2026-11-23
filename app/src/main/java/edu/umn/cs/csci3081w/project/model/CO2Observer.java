package edu.umn.cs.csci3081w.project.model;

/**
 * Gets a vehicle's recent CO2 after each update.
 */
public interface CO2Observer {

  /**
   * Runs after the vehicle updates.
   *
   * @param id vehicle id
   * @param values last five CO2 values, current one last
   */
  void onCO2Update(int id, int[] values);
}