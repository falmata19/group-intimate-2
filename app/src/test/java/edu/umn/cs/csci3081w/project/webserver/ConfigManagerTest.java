package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import edu.umn.cs.csci3081w.project.model.Counter;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Route;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for the line building in the ConfigManager class.
 */
public class ConfigManagerTest {

  private static final String CONFIG = String.join("\n",
      "LINE_START, BUS_LINE, Campus Connector",
      "ROUTE_START, East Bound",
      "STOP, Blegen Hall, 44.972392, -93.243774, .15",
      "STOP, Coffman, 44.973580, -93.235071, 0",
      "ROUTE_END",
      "ROUTE_START, West Bound",
      "STOP, Coffman, 44.973580, -93.235071, .3",
      "STOP, Blegen Hall, 44.972638, -93.243591, 0",
      "ROUTE_END",
      "LINE_END",
      "LINE_START, TRAIN_LINE, Green Line",
      "ROUTE_START, East Bound",
      "STOP, Union Depot, 44.948800, -93.086600, .1",
      "STOP, Central, 44.945700, -93.092600, 0",
      "ROUTE_END",
      "ROUTE_START, West Bound",
      "STOP, Central, 44.945700, -93.092600, .1",
      "STOP, Union Depot, 44.948800, -93.086600, 0",
      "ROUTE_END",
      "LINE_END",
      "STORAGE_FACILITY_START",
      "SMALL_BUSES, 3",
      "STORAGE_FACILITY_END");

  @TempDir
  File tempDir;

  private ConfigManager configManager;

  /**
   * Writes a small config file and reads it before each test.
   *
   * @throws IOException if the config file cannot be written
   */
  @BeforeEach
  public void setUp() throws IOException {
    File configFile = new File(tempDir, "config.txt");
    Files.write(configFile.toPath(), CONFIG.getBytes(StandardCharsets.UTF_8));
    configManager = new ConfigManager();
    configManager.readConfig(new Counter(), configFile.getPath());
  }

  /**
   * Tests that readConfig makes one line per LINE_START block with the right id, name,
   * and type.
   */
  @Test
  public void testReadConfigBuildsLines() {
    List<Line> lines = configManager.getLines();
    assertEquals(2, lines.size());
    assertEquals(1, lines.get(0).getId());
    assertEquals("Campus Connector", lines.get(0).getName());
    assertEquals(Route.BUS_LINE, lines.get(0).getType());
    assertEquals(2, lines.get(1).getId());
    assertEquals("Green Line", lines.get(1).getName());
    assertEquals(Route.TRAIN_LINE, lines.get(1).getType());
  }

  /**
   * Tests that each line uses the first route as outbound and the second as inbound,
   * in the same order as the routes list.
   */
  @Test
  public void testReadConfigPairsRoutes() {
    List<Route> routes = configManager.getRoutes();
    List<Line> lines = configManager.getLines();
    assertEquals(4, routes.size());
    assertSame(routes.get(0), lines.get(0).getOutboundRoute());
    assertSame(routes.get(1), lines.get(0).getInboundRoute());
    assertSame(routes.get(2), lines.get(1).getOutboundRoute());
    assertSame(routes.get(3), lines.get(1).getInboundRoute());
    assertEquals("East Bound", lines.get(1).getOutboundRoute().getName());
    assertEquals("West Bound", lines.get(1).getInboundRoute().getName());
  }

  /**
   * Tests that readConfig still reads the storage facility.
   */
  @Test
  public void testReadConfigStorageFacility() {
    assertEquals(3, configManager.getStorageFacility().getSmallBusesNum());
  }
}
