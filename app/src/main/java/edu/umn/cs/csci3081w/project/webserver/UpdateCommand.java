package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

/**
 * Command that moves the simulation forward one time step unless it is paused.
 */
public class UpdateCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;
  private PauseCommand pauseCommand;

  /**
   * Makes a command that updates the simulation.
   *
   * @param simulator the simulator to update
   * @param pauseCommand the command that keeps track of whether the simulation is paused
   */
  public UpdateCommand(VisualTransitSimulator simulator, PauseCommand pauseCommand) {
    this.simulator = simulator;
    this.pauseCommand = pauseCommand;
  }

  /**
   * Updates the state of the simulation.
   *
   * @param session current simulation session
   * @param command the update simulation command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    if (!pauseCommand.isPaused()) {
      simulator.update();
    }
  }

}
