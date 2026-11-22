package edu.umn.cs.csci3081w.project.webserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps the commands a session can run, stored by command name.
 */
public class WebServerSessionState {

  private Map<String, SimulatorCommand> commands;

  /**
   * Makes a state with no commands yet.
   */
  public WebServerSessionState() {
    this.commands = new HashMap<String, SimulatorCommand>();
  }

  public Map<String, SimulatorCommand> getCommands() {
    return commands;
  }
}
