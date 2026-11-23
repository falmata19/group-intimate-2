package edu.umn.cs.csci3081w.project.webserver;

import edu.umn.cs.csci3081w.project.model.BusFactory;
import edu.umn.cs.csci3081w.project.model.Counter;
import edu.umn.cs.csci3081w.project.model.DieselTrain;
import edu.umn.cs.csci3081w.project.model.ElectricTrain;
import edu.umn.cs.csci3081w.project.model.LargeBus;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.SmallBus;
import edu.umn.cs.csci3081w.project.model.StorageFacility;
import edu.umn.cs.csci3081w.project.model.TrainFactory;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Runs the simulation by making vehicles, moving them along their routes, and updating the routes
 * and stops.
 */
public class VisualTransitSimulator {

  private static boolean LOGGING = false;
  private int numTimeSteps = 0;
  private int simulationTimeElapsed = 0;
  private Counter counter;
  private List<Line> lines;
  private List<Vehicle> activeVehicles;
  private List<Vehicle> completedTripVehicles;
  private List<Integer> vehicleStartTimings;
  private List<Integer> timeSinceLastVehicle;
  private StorageFacility storageFacility;
  private BusFactory busFactory;
  private TrainFactory trainFactory;
  
  /**
   * Constructor for Simulation.
   *
   * @param configFile file containing the simulation configuration
   */
  public VisualTransitSimulator(String configFile) {
    this.counter = new Counter();
    ConfigManager configManager = new ConfigManager();
    configManager.readConfig(counter, configFile);
    this.lines = configManager.getLines();
    this.storageFacility = configManager.getStorageFacility();
    this.busFactory = new BusFactory(LocalDateTime.now());
    this.trainFactory = new TrainFactory(LocalDateTime.now());
    this.activeVehicles = new ArrayList<Vehicle>();
    this.completedTripVehicles = new ArrayList<Vehicle>();
    this.vehicleStartTimings = new ArrayList<Integer>();
    this.timeSinceLastVehicle = new ArrayList<Integer>();
    if (VisualTransitSimulator.LOGGING) {
      System.out.println("////Simulation Routes////");
      for (int i = 0; i < lines.size(); i++) {
        lines.get(i).getOutboundRoute().report(System.out);
        lines.get(i).getInboundRoute().report(System.out);
      }
    }
  }

  /**
   * Starts the simulation.
   *
   * @param vehicleStartTimings start timings of bus
   * @param numTimeSteps        number of time steps
   */
  public void start(List<Integer> vehicleStartTimings, int numTimeSteps) {
    this.vehicleStartTimings = vehicleStartTimings;
    this.numTimeSteps = numTimeSteps;
    for (int i = 0; i < vehicleStartTimings.size(); i++) {
      this.timeSinceLastVehicle.add(i, 0);
    }
    simulationTimeElapsed = 0;
  }

  /**
   * Updates the simulation at each step.
   */
  public void update() {
    simulationTimeElapsed++;
    if (simulationTimeElapsed > numTimeSteps) {
      return;
    }
    System.out.println("~~~~The simulation time is now at time step "
        + simulationTimeElapsed + "~~~~");
    // update the line issues
    for (int i = 0; i < lines.size(); i++) {
      lines.get(i).updateIssue();
    }
    // generate vehicles
    for (int i = 0; i < timeSinceLastVehicle.size(); i++) {
      if (timeSinceLastVehicle.get(i) <= 0) {
        Line line = lines.get(i);
        if (line.getType().equals(Route.BUS_LINE)) {
          String busType = busFactory.getNextBusType();
          if (busType.equals(SmallBus.SMALL_BUS_VEHICLE)
              && storageFacility.getSmallBusesNum() > 0) {
            activeVehicles.add(busFactory.generate(busType,
                    counter.getBusIdCounterAndIncrement(),
                    line.shallowCopy()));
            storageFacility.decrementSmallBus();
          } else if (busType.equals(LargeBus.LARGE_BUS_VEHICLE)
              && storageFacility.getLargeBusesNum() > 0) {
            activeVehicles.add(busFactory.generate(busType,
                    counter.getBusIdCounterAndIncrement(),
                    line.shallowCopy()));
            storageFacility.decrementLargeBus();
          }
          timeSinceLastVehicle.set(i, vehicleStartTimings.get(i));
        } else if (line.getType().equals(Route.TRAIN_LINE)) {
          String trainType = trainFactory.getNextTrainType();
          if (trainType.equals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE)
              && storageFacility.getElectricTrainsNum() > 0) {
            activeVehicles.add(trainFactory.generate(trainType,
                counter.getTrainIdCounterAndIncrement(),
                line.shallowCopy()));
            storageFacility.decrementElectricTrain();
          } else if (trainType.equals(DieselTrain.DIESEL_TRAIN_VEHICLE)
              && storageFacility.getDieselTrainsNum() > 0) {
            activeVehicles.add(trainFactory.generate(trainType,
                counter.getTrainIdCounterAndIncrement(),
                line.shallowCopy()));
            storageFacility.decrementDieselTrain();
          }
          timeSinceLastVehicle.set(i, vehicleStartTimings.get(i));
          timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
        }
      } else {
        timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
      }
    }
    // update vehicles
    for (int i = activeVehicles.size() - 1; i >= 0; i--) {
      Vehicle currVehicle = activeVehicles.get(i);
      currVehicle.update();
      if (currVehicle.isTripComplete()) {
        Vehicle completedTripVehicle = activeVehicles.remove(i);
        completedTripVehicles.add(completedTripVehicle);
        if (completedTripVehicle instanceof SmallBus) {
          storageFacility.incrementSmallBus();
        } else if (completedTripVehicle instanceof LargeBus) {
          storageFacility.incrementLargeBus();
        } else if (completedTripVehicle instanceof ElectricTrain) {
          storageFacility.incrementElectricTrain();
        } else if (completedTripVehicle instanceof DieselTrain) {
          storageFacility.incrementDieselTrain();
        }
      } else {
        if (VisualTransitSimulator.LOGGING) {
          currVehicle.report(System.out);
        }
      }
    }
    // update routes
    for (int i = 0; i < lines.size(); i++) {
      Route[] lineRoutes = {lines.get(i).getOutboundRoute(), lines.get(i).getInboundRoute()};
      for (Route currRoute : lineRoutes) {
        currRoute.update();
        if (VisualTransitSimulator.LOGGING) {
          currRoute.report(System.out);
        }
      }
    }
  }

  public List<Line> getLines() {
    return lines;
  }

  /**
   * Starts an issue on a line, so its vehicles do not move for a while.
   *
   * @param lineId id of the line
   * @return true if a line with that id exists
   */
  public boolean startLineIssue(int lineId) {
    for (Line line : lines) {
      if (line.getId() == lineId) {
        line.startIssue();
        return true;
      }
    }
    return false;
  }

  public List<Vehicle> getActiveVehicles() {
    return activeVehicles;
  }
}