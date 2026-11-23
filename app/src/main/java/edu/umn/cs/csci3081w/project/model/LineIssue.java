package edu.umn.cs.csci3081w.project.model;

/**
 * Keeps track of an issue on a line. While the issue is active, the vehicles of the line do not
 * move. A line and all the copies of that line share one LineIssue object.
 */
public class LineIssue {

  /**
   * Number of time units an issue lasts.
   */
  public static final int DURATION = 10;

  private int timeRemaining = 0;

  /**
   * Makes a line issue that is not active.
   */
  public LineIssue() {

  }

  /**
   * Starts an issue, or restarts the countdown if an issue is already active.
   */
  public void start() {
    timeRemaining = DURATION;
  }

  /**
   * Moves the issue forward by one time unit. Does nothing if there is no active issue.
   */
  public void update() {
    if (timeRemaining > 0) {
      timeRemaining--;
    }
  }

  /**
   * Checks if the issue is active.
   *
   * @return true if the issue is active
   */
  public boolean isActive() {
    return timeRemaining > 0;
  }

  public int getTimeRemaining() {
    return timeRemaining;
  }
}
