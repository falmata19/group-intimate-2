package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

/**
 * Base class for the commands that the visualization module sends.
 */
public abstract class SimulatorCommand {
  /**
   * Runs the command.
   *
   * @param session the current simulation session
   * @param command the content of the command
   */
  public abstract void execute(WebServerSession session, JsonObject command);
}
