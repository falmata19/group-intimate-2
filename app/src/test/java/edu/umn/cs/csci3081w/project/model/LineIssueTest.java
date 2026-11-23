package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the LineIssue class.
 */
public class LineIssueTest {

  private LineIssue issue;

  /**
   * Makes a new issue before each test.
   */
  @BeforeEach
  public void setUp() {
    issue = new LineIssue();
  }

  /**
   * Tests that a new issue does not exist yet.
   */
  @Test
  public void testInitiallyNoIssue() {
    assertFalse(issue.isIssueExist());
    assertEquals(0, issue.getTimeRemaining());
  }

  /**
   * Tests that creating an issue makes it exist and sets the countdown to 10.
   */
  @Test
  public void testCreateIssue() {
    issue.createIssue();
    assertTrue(issue.isIssueExist());
    assertEquals(10, issue.getTimeRemaining());
  }

  /**
   * Tests that the issue exists until it has been ticked 10 times and is gone after that.
   */
  @Test
  public void testTickEndsIssueAfterTenTicks() {
    issue.createIssue();
    for (int i = 0; i < 9; i++) {
      issue.tick();
      assertTrue(issue.isIssueExist());
    }
    issue.tick();
    assertFalse(issue.isIssueExist());
  }

  /**
   * Tests that ticking when there is no issue keeps the countdown at 0.
   */
  @Test
  public void testTickWithoutIssueStaysAtZero() {
    issue.tick();
    issue.tick();
    assertEquals(0, issue.getTimeRemaining());
    assertFalse(issue.isIssueExist());
  }

  /**
   * Tests that ticking after the issue ended does not make the countdown negative.
   */
  @Test
  public void testTickNeverGoesBelowZero() {
    issue.createIssue();
    for (int i = 0; i < 15; i++) {
      issue.tick();
    }
    assertEquals(0, issue.getTimeRemaining());
  }

  /**
   * Tests that creating an issue again restarts the countdown at 10.
   */
  @Test
  public void testCreateIssueAgainRestarts() {
    issue.createIssue();
    issue.tick();
    issue.tick();
    issue.createIssue();
    assertEquals(10, issue.getTimeRemaining());
  }
}
