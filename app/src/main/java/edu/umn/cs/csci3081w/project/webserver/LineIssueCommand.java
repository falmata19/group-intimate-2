package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

/**
 * Command that starts an issue on a line, so the vehicles of the line stop moving for a while.
 */
public class LineIssueCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Makes a command that starts line issues.
   *
   * @param simulator the simulator that has the lines
   */
  public LineIssueCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Starts an issue on the line selected by the user. The line id comes as a string. A missing
   * or invalid id, or an id of an unknown line, is ignored.
   *
   * @param session current simulation session
   * @param command the line issue command content, with the id of the line
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    if (command == null || !command.has("id") || !command.get("id").isJsonPrimitive()) {
      return;
    }
    try {
      simulator.startLineIssue(Integer.parseInt(command.get("id").getAsString().trim()));
    } catch (NumberFormatException e) {
      System.out.println("Invalid line id: " + command.get("id"));
    }
  }
}
