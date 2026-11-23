package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Bus;
import edu.umn.cs.csci3081w.project.model.CO2Observer;
import edu.umn.cs.csci3081w.project.model.Vehicle;

/**
 * Sends a watched vehicle's details and recent CO2 to the visualization after each update.
 */
public class VehicleInfoObserver implements CO2Observer {

  private WebServerSession session;
  private Vehicle vehicle;

  /**
   * Makes an observer for one vehicle.
   *
   * @param session session that gets the vehicle info
   * @param vehicle vehicle to watch
   */
  public VehicleInfoObserver(WebServerSession session, Vehicle vehicle) {
    this.session = session;
    this.vehicle = vehicle;
  }

  /**
   * Sends the vehicle info after it updates.
   *
   * @param id vehicle id
   * @param values last five CO2 values, current one last
   */
  @Override
  public void onCO2Update(int id, int[] values) {
    JsonObject data = new JsonObject();
    data.addProperty("command", "observedVehicle");
    data.addProperty("text", buildText(vehicle, values));
    session.sendJson(data);
  }

  /**
   * Sends the vehicle info with the CO2 values it has right now.
   */
  public void sendCurrentInfo() {
    onCO2Update(vehicle.getId(), vehicle.getCo2History());
  }

  /**
   * Builds the text shown for a watched vehicle, with the current CO2 first.
   *
   * @param vehicle watched vehicle
   * @param values last five CO2 values, current one last
   * @return multi-line text with type, position, passengers and CO2
   */
  public static String buildText(Vehicle vehicle, int[] values) {
    String type = vehicle instanceof Bus ? "BUS" : "TRAIN";
    StringBuilder co2 = new StringBuilder();
    for (int i = values.length - 1; i >= 0; i--) {
      co2.append(values[i]);
      if (i > 0) {
        co2.append(", ");
      }
    }
    return vehicle.getName() + "\n"
        + "-----------------------------\n"
        + "* Type: " + type + "\n"
        + "* Position: (" + vehicle.getPosition().getLongitude() + ","
        + vehicle.getPosition().getLatitude() + ")\n"
        + "* Passengers: " + vehicle.getPassengers().size() + "\n"
        + "* CO2: " + co2;
  }
}