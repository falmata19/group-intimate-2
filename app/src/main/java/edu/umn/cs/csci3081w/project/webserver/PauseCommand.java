package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

/**
 * Command that pauses or resumes the simulation.
 */
public class PauseCommand extends SimulatorCommand {

  private boolean paused = false;

  /**
   * Pauses the simulation, or resumes it if it is already paused.
   *
   * @param session current simulation session
   * @param command the pause simulation command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    paused = !paused;
  }

  /**
   * Checks if the simulation is paused.
   *
   * @return true if the simulation is paused
   */
  public boolean isPaused() {
    return paused;
  }

}
