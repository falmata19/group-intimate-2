package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

public class RegisterVehicleCommandTest {

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
  private RegisterVehicleCommand command;
  private RecordingSession session;

  private static class RecordingSession extends WebServerSession {
    private List<JsonObject> sent = new ArrayList<JsonObject>();

    @Override
    public void sendJson(JsonObject message) {
      sent.add(message);
    }
  }

  /**
   * Makes a simulator with two active buses, a command and a recording session.
   *
   * @throws IOException if the config file cannot be written
   */
  @BeforeEach
  public void setUp() throws IOException {
    File configFile = new File(tempDir, "config.txt");
    Files.write(configFile.toPath(), CONFIG.getBytes(StandardCharsets.UTF_8));
    simulator = new VisualTransitSimulator(configFile.getPath());
    List<Integer> timings = new ArrayList<Integer>();
    timings.add(1000);
    timings.add(1000);
    simulator.start(timings, 100);
    simulator.update();
    command = new RegisterVehicleCommand(simulator);
    session = new RecordingSession();
  }

  private JsonObject json(String text) {
    return JsonParser.parseString(text).getAsJsonObject();
  }

  private JsonObject registerMessage(int id) {
    return json("{\"command\":\"registerVehicle\",\"id\":\"" + id + "\"}");
  }

  private int idOfVehicle(int index) {
    return simulator.getActiveVehicles().get(index).getId();
  }

  /**
   * Checks that registering a vehicle sends its information right away.
   */
  @Test
  public void testExecuteSendsObservedVehicle() {
    command.execute(session, registerMessage(idOfVehicle(0)));
    assertEquals(1, session.sent.size());
    JsonObject message = session.sent.get(0);
    assertEquals("observedVehicle", message.get("command").getAsString());
    String text = message.get("text").getAsString();
    assertTrue(text.contains("* Type: BUS"));
    assertTrue(text.contains("* CO2: "));
  }

  /**
   * Checks that the observed vehicle sends its information again on every update.
   */
  @Test
  public void testSendsInformationOnEveryUpdate() {
    command.execute(session, registerMessage(idOfVehicle(0)));
    simulator.update();
    simulator.update();
    assertEquals(3, session.sent.size());
  }

  /**
   * Checks that registering another vehicle stops the updates of the first one.
   */
  @Test
  public void testNewRegistrationStopsOldObservation() {
    command.execute(session, registerMessage(idOfVehicle(0)));
    command.execute(session, registerMessage(idOfVehicle(1)));
    assertEquals(2, session.sent.size());
    simulator.update();
    assertEquals(3, session.sent.size());
  }

  /**
   * Checks that ids that are not numbers, unknown, or missing are ignored.
   */
  @Test
  public void testInvalidIdsAreIgnored() {
    command.execute(session, json("{\"command\":\"registerVehicle\",\"id\":\"abc\"}"));
    command.execute(session, json("{\"command\":\"registerVehicle\",\"id\":\"99999\"}"));
    command.execute(session, json("{\"command\":\"registerVehicle\"}"));
    command.execute(session, json("{\"command\":\"registerVehicle\",\"id\":null}"));
    assertEquals(0, session.sent.size());
  }

  /**
   * Checks the text of an observed vehicle: heading, type, and CO2 with the newest value first.
   */
  @Test
  public void testBuildText() {
    Vehicle vehicle = simulator.getActiveVehicles().get(0);
    String text = VehicleInfoObserver.buildText(vehicle, new int[] {1, 2, 3, 4, 5});
    assertTrue(text.startsWith(vehicle.getName() + "\n-----------------------------\n"));
    assertTrue(text.contains("* Type: BUS"));
    assertTrue(text.contains("* Passengers: " + vehicle.getPassengers().size()));
    assertTrue(text.endsWith("* CO2: 5, 4, 3, 2, 1"));
  }
}