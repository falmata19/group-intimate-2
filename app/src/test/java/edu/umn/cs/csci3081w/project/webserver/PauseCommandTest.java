package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

public class PauseCommandTest {

  /**
   * Tests that a new pause command starts in the running state.
   */
  @Test
  public void testInitiallyNotPaused() {
    PauseCommand pauseCommand = new PauseCommand();
    assertFalse(pauseCommand.isPaused());
  }

  /**
   * Tests that executing the command once pauses the simulation.
   */
  @Test
  public void testExecutePauses() {
    PauseCommand pauseCommand = new PauseCommand();
    pauseCommand.execute(null, new JsonObject());
    assertTrue(pauseCommand.isPaused());
  }

  /**
   * Tests that executing the command twice resumes the simulation.
   */
  @Test
  public void testExecuteTwiceResumes() {
    PauseCommand pauseCommand = new PauseCommand();
    pauseCommand.execute(null, new JsonObject());
    pauseCommand.execute(null, new JsonObject());
    assertFalse(pauseCommand.isPaused());
  }

}
