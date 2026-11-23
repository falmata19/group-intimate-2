package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VehicleCo2ObserverTest {
  private Line line;

  private static class RecordingObserver implements CO2Observer {
    private int calls = 0;
    private int lastId = -1;
    private int[] lastValues = null;

    @Override
    public void onCO2Update(int id, int[] values) {
      calls++;
      lastId = id;
      lastValues = values;
    }
  }

  /**
   * Makes passenger creation predictable and builds the line used in the tests.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;

    Stop firstStop = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop lastStop = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));

    List<Stop> stopsOut = new ArrayList<>();
    stopsOut.add(firstStop);
    stopsOut.add(lastStop);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.9712663713083954);
    List<Double> probabilitiesOut = new ArrayList<>();
    probabilitiesOut.add(.15);
    probabilitiesOut.add(.0);
    Route outRoute = new Route(10, "testLine", Route.BUS_LINE, "testRouteOut", stopsOut,
        distancesOut, new RandomPassengerGenerator(stopsOut, probabilitiesOut));

    List<Stop> stopsIn = new ArrayList<>();
    stopsIn.add(lastStop);
    stopsIn.add(firstStop);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.9712663713083954);
    List<Double> probabilitiesIn = new ArrayList<>();
    probabilitiesIn.add(.3);
    probabilitiesIn.add(.0);
    Route inRoute = new Route(11, "testLine", Route.BUS_LINE, "testRouteIn", stopsIn,
        distancesIn, new RandomPassengerGenerator(stopsIn, probabilitiesIn));

    line = new Line(outRoute, inRoute);
  }

  /**
   * Checks that the CO2 history of a new vehicle holds five zeros.
   */
  @Test
  public void testHistoryStartsEmpty() {
    SmallBus bus = new SmallBus(1, line, 20, 1);
    assertArrayEquals(new int[Vehicle.CO2_HISTORY_SIZE], bus.getCo2History());
  }

  /**
   * Checks that after one update the newest history value is the current CO2.
   */
  @Test
  public void testHistoryEndsWithCurrentCo2() {
    SmallBus bus = new SmallBus(1, line, 20, 1);
    bus.update();
    int[] history = bus.getCo2History();
    assertEquals(bus.getCo2Emission(), history[Vehicle.CO2_HISTORY_SIZE - 1]);
    assertEquals(0, history[0]);
  }

  /**
   * Checks that only the last five CO2 values are kept, oldest first.
   */
  @Test
  public void testHistoryKeepsLastFiveOldestFirst() {
    final int[] next = {1};
    SmallBus bus = new SmallBus(1, line, 20, 1) {
      @Override
      public int getCo2Emission() {
        return next[0]++;
      }
    };
    for (int i = 0; i < 7; i++) {
      bus.update();
    }
    assertArrayEquals(new int[] {3, 4, 5, 6, 7}, bus.getCo2History());
  }

  /**
   * Checks that changing the returned history does not change the vehicle.
   */
  @Test
  public void testGetCo2HistoryReturnsCopy() {
    SmallBus bus = new SmallBus(1, line, 20, 1);
    bus.getCo2History()[0] = 99;
    assertEquals(0, bus.getCo2History()[0]);
  }

  /**
   * Checks that an observer gets the vehicle id and history after an update.
   */
  @Test
  public void testObserverIsNotifiedOnUpdate() {
    SmallBus bus = new SmallBus(7, line, 20, 1);
    RecordingObserver observer = new RecordingObserver();
    bus.addCO2Observer(observer);
    bus.update();
    assertEquals(1, observer.calls);
    assertEquals(7, observer.lastId);
    assertArrayEquals(bus.getCo2History(), observer.lastValues);
  }

  /**
   * Checks that every added observer is told about an update.
   */
  @Test
  public void testAllObserversAreNotified() {
    SmallBus bus = new SmallBus(1, line, 20, 1);
    RecordingObserver first = new RecordingObserver();
    RecordingObserver second = new RecordingObserver();
    bus.addCO2Observer(first);
    bus.addCO2Observer(second);
    bus.update();
    assertEquals(1, first.calls);
    assertEquals(1, second.calls);
  }

  /**
   * Checks that a removed observer is not told about updates anymore.
   */
  @Test
  public void testRemovedObserverIsNotNotified() {
    SmallBus bus = new SmallBus(1, line, 20, 1);
    RecordingObserver observer = new RecordingObserver();
    bus.addCO2Observer(observer);
    bus.update();
    bus.removeCO2Observer(observer);
    bus.update();
    assertEquals(1, observer.calls);
  }

  /**
   * Checks that adding a null observer is ignored and updates still work.
   */
  @Test
  public void testNullObserverIsIgnored() {
    SmallBus bus = new SmallBus(1, line, 20, 1);
    bus.addCO2Observer(null);
    bus.update();
    assertEquals(bus.getCo2Emission(), bus.getCo2History()[Vehicle.CO2_HISTORY_SIZE - 1]);
  }

  /**
   * Checks that adding the same observer twice only notifies it once per update.
   */
  @Test
  public void testSameObserverAddedTwiceIsNotifiedOnce() {
    SmallBus bus = new SmallBus(1, line, 20, 1);
    RecordingObserver observer = new RecordingObserver();
    bus.addCO2Observer(observer);
    bus.addCO2Observer(observer);
    bus.update();
    assertEquals(1, observer.calls);
  }
}