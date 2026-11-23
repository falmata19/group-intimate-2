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
    this.id = id;
    this.name = name;
    this.type = type;
    this.outboundRoute = outboundRoute;
    this.inboundRoute = inboundRoute;
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
   * both routes. The copied routes share the same stops as the original routes.
   *
   * @return a shallow copy of this line
   */
  public Line shallowCopy() {
    return new Line(id, name, type, outboundRoute.shallowCopy(), inboundRoute.shallowCopy());
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
