package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import java.util.List;

/**
 * Command that sends the lines to the visualization module.
 */
public class InitLinesCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Makes a command that sets up the lines.
   *
   * @param simulator the simulator to get the lines from
   */
  public InitLinesCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Initializes the lines information for the simulation.
   *
   * @param session current simulation session
   * @param command the initialize routes command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Line> lines = simulator.getLines();
    int numLines = lines.size();
    JsonObject data = new JsonObject();
    data.addProperty("command", "initLines");
    data.addProperty("numLines", numLines);
    JsonArray linesArray = new JsonArray();
    for (Line line : lines) {
      JsonObject s = new JsonObject();
      s.addProperty("id", line.getId());
      s.addProperty("name", line.getName());
      s.addProperty("type", line.getType());
      linesArray.add(s);
    }
    data.add("lines", linesArray);
    session.sendJson(data);
  }

}
