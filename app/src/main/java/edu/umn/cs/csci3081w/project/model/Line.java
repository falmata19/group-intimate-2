package edu.umn.cs.csci3081w.project.model;

/**
 * Shows a line that contains the outbound and inbound route.
 */
public class Line {
  private int id;
  private String name;
  private String type;
  private Route outboundRoute;
  private Route inboundRoute;
  private LineIssue issue;

  /**
   * Line constructor that takes the line id, name, and type.
   *
   * @param id            id of the line
   * @param name          name of the line
   * @param type          type of the line (bus or train line)
   * @param outboundRoute outbound route of the line
   * @param inboundRoute  inbound route of the line
   */
  public Line(int id, String name, String type, Route outboundRoute, Route inboundRoute) {
    this(id, name, type, outboundRoute, inboundRoute, new LineIssue());
  }

  private Line(int id, String name, String type, Route outboundRoute, Route inboundRoute,
               LineIssue issue) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.outboundRoute = outboundRoute;
    this.inboundRoute = inboundRoute;
    this.issue = issue;
  }

  /**
   * Line constructor. The line has id -1, and its name and type are taken from the
   * outbound route.
   *
   * @param outboundRoute outbound route of the line
   * @param inboundRoute  inbound route of the line
   */
  public Line(Route outboundRoute, Route inboundRoute) {
    this(-1,
        outboundRoute == null ? null : outboundRoute.getLineName(),
        outboundRoute == null ? null : outboundRoute.getLineType(),
        outboundRoute, inboundRoute);
  }

  /**
   * Makes a copy of this line with the same id, name, and type, and shallow copies of
   * both routes. The copied routes share the same stops as the original routes, and the copy
   * shares the issue state of this line, so an issue on the line affects its copies.
   *
   * @return a shallow copy of this line
   */
  public Line shallowCopy() {
    return new Line(id, name, type, outboundRoute.shallowCopy(), inboundRoute.shallowCopy(),
        issue);
  }

  /**
   * Starts an issue on the line. The vehicles of the line should not move while it is active.
   */
  public void startIssue() {
    issue.createIssue();
  }

  /**
   * Moves the issue of the line forward by one time unit.
   */
  public void updateIssue() {
    issue.tick();
  }

  /**
   * Checks if the line has an active issue.
   *
   * @return true if there is an active issue on the line
   */
  public boolean isIssueActive() {
    return issue.isIssueExist();
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  /**
   * Returns the outbound route.
   *
   * @return outbound route
   */
  public Route getOutboundRoute() {
    return outboundRoute;
  }

  /**
   * Returns the inbound route.
   *
   * @return inbound route
   */
  public Route getInboundRoute() {
    return inboundRoute;
  }
}
