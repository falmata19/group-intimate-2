package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for the LineIssueCommand class and the timing of line issues in the simulator.
 */
public class LineIssueCommandTest {

  private static final String CONFIG = String.join("\n",
      "LINE_START, BUS_LINE, Bus One",
      "ROUTE_START, East Bound",
      "STOP, A, 44.000000, -93.200000, 0",
      "STOP, B, 45.000000, -93.200000, 0",
      "ROUTE_END",
      "ROUTE_START, West Bound",
      "STOP, B, 45.000000, -93.200000, 0",
      "STOP, A, 44.000000, -93.200000, 0",
      "ROUTE_END",
      "LINE_END",
      "LINE_START, BUS_LINE, Bus Two",
      "ROUTE_START, East Bound",
      "STOP, C, 44.000000, -93.100000, 0",
      "STOP, D, 45.000000, -93.100000, 0",
      "ROUTE_END",
      "ROUTE_START, West Bound",
      "STOP, D, 45.000000, -93.100000, 0",
      "STOP, C, 44.000000, -93.100000, 0",
      "ROUTE_END",
      "LINE_END",
      "STORAGE_FACILITY_START",
      "SMALL_BUSES, 3",
      "LARGE_BUSES, 3",
      "STORAGE_FACILITY_END");

  @TempDir
  File tempDir;

  private VisualTransitSimulator simulator;
  private LineIssueCommand command;

  /**
   * Makes a simulator from a small config file and a command for it before each test.
   *
   * @throws IOException if the config file cannot be written
   */
  @BeforeEach
  public void setUp() throws IOException {
    File configFile = new File(tempDir, "config.txt");
    Files.write(configFile.toPath(), CONFIG.getBytes(StandardCharsets.UTF_8));
    simulator = new VisualTransitSimulator(configFile.getPath());
    command = new LineIssueCommand(simulator);
  }

  private JsonObject json(String text) {
    return JsonParser.parseString(text).getAsJsonObject();
  }

  private void startSimulator() {
    // Each line gets one bus at step 1 (the first line's bus is first in the active vehicles),
    // and no other bus is made because the start timings are huge.
    List<Integer> timings = new ArrayList<Integer>();
    timings.add(1000);
    timings.add(1000);
    simulator.start(timings, 100);
  }

  /**
   * Tests that the command starts an issue on the line whose id arrives as a string, and only
   * on that line.
   */
  @Test
  public void testExecuteStartsIssueOnSelectedLine() {
    int id = simulator.getLines().get(1).getId();
    command.execute(null, json("{\"command\":\"lineIssue\",\"id\":\"" + id + "\"}"));
    assertFalse(simulator.getLines().get(0).isIssueActive());
    assertTrue(simulator.getLines().get(1).isIssueActive());
  }

  /**
   * Tests that an id that is not a number does not throw and starts no issue.
   */
  @Test
  public void testExecuteNonNumericIdDoesNotThrow() {
    command.execute(null, json("{\"command\":\"lineIssue\",\"id\":\"abc\"}"));
    for (Line line : simulator.getLines()) {
      assertFalse(line.isIssueActive());
    }
  }

  /**
   * Tests that an id of a line that does not exist does not throw and starts no issue.
   */
  @Test
  public void testExecuteUnknownIdDoesNotThrow() {
    command.execute(null, json("{\"command\":\"lineIssue\",\"id\":\"999\"}"));
    for (Line line : simulator.getLines()) {
      assertFalse(line.isIssueActive());
    }
  }

  /**
   * Tests that a message without an id, with a null id, or with an id that is an object does
   * not throw and starts no issue.
   */
  @Test
  public void testExecuteMissingOrMalformedIdDoesNotThrow() {
    command.execute(null, json("{\"command\":\"lineIssue\"}"));
    command.execute(null, json("{\"command\":\"lineIssue\",\"id\":null}"));
    command.execute(null, json("{\"command\":\"lineIssue\",\"id\":{\"a\":1}}"));
    for (Line line : simulator.getLines()) {
      assertFalse(line.isIssueActive());
    }
  }

  /**
   * Tests that startLineIssue says whether a line with the given id exists.
   */
  @Test
  public void testStartLineIssueReturnValue() {
    assertTrue(simulator.startLineIssue(simulator.getLines().get(0).getId()));
    assertFalse(simulator.startLineIssue(-5));
  }

  /**
   * Tests the timing from the spec: an issue created after time step 23 keeps the vehicles of
   * the line still at steps 24 to 32, and they move again at step 33.
   */
  @Test
  public void testVehiclesStopAtSteps24To32AndMoveAt33() {
    startSimulator();
    for (int step = 1; step <= 23; step++) {
      simulator.update();
    }
    assertEquals(2, simulator.getActiveVehicles().size());
    Vehicle vehicle = simulator.getActiveVehicles().get(0);
    double latitude = vehicle.getPosition().getLatitude();
    int lineId = simulator.getLines().get(0).getId();
    command.execute(null, json("{\"command\":\"lineIssue\",\"id\":\"" + lineId + "\"}"));
    for (int step = 24; step <= 32; step++) {
      simulator.update();
      assertEquals(latitude, vehicle.getPosition().getLatitude(),
          "vehicle should not move at step " + step);
    }
    simulator.update();
    assertNotEquals(latitude, vehicle.getPosition().getLatitude());
  }

  /**
   * Tests that a vehicle on a line without an issue keeps moving while another line has one.
   */
  @Test
  public void testIssueOnOtherLineDoesNotStopVehicle() {
    startSimulator();
    for (int step = 1; step <= 5; step++) {
      simulator.update();
    }
    Vehicle vehicle = simulator.getActiveVehicles().get(0);
    simulator.startLineIssue(simulator.getLines().get(1).getId());
    double latitude = vehicle.getPosition().getLatitude();
    simulator.update();
    assertNotEquals(latitude, vehicle.getPosition().getLatitude());
  }
}
