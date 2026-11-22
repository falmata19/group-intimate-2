package edu.umn.cs.csci3081w.project.model;

/**
 * Shows a line that contains the outbound and inbound route.
 */
public class Line {
  private Route outboundRoute;
  private Route inboundRoute;

  /**
   * Line constructor.
   *
   * @param outboundRoute outbound route of the line
   * @param inboundRoute  inbound route of the line
   */
  public Line(Route outboundRoute, Route inboundRoute) {
    this.outboundRoute = outboundRoute;
    this.inboundRoute = inboundRoute;
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