package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Vehicle;

/**
 * Starts watching a vehicle so its info and CO2 are sent on every time unit.
 */
public class RegisterVehicleCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;
  private Vehicle observedVehicle;
  private VehicleInfoObserver observer;

  /**
   * Makes a command that registers observers on vehicles.
   *
   * @param simulator simulator that has the vehicles
   */
  public RegisterVehicleCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Watches the vehicle the user selected and sends its info right away. The previous vehicle
   * stops being watched. A missing, invalid or unknown id is ignored.
   *
   * @param session current simulation session
   * @param command command content, with the vehicle id
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    if (command == null || !command.has("id") || !command.get("id").isJsonPrimitive()) {
      return;
    }
    int id;
    try {
      id = Integer.parseInt(command.get("id").getAsString().trim());
    } catch (NumberFormatException e) {
      System.out.println("Invalid vehicle id: " + command.get("id"));
      return;
    }
    for (Vehicle vehicle : simulator.getActiveVehicles()) {
      if (vehicle.getId() == id) {
        if (observedVehicle != null) {
          observedVehicle.removeCO2Observer(observer);
        }
        observer = new VehicleInfoObserver(session, vehicle);
        observedVehicle = vehicle;
        vehicle.addCO2Observer(observer);
        observer.sendCurrentInfo();
        return;
      }
    }
  }
}