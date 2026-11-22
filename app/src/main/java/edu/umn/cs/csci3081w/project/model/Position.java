package edu.umn.cs.csci3081w.project.model;

/**
 * A spot on the map with a longitude and a latitude.
 */
public class Position {

  private double longitude;
  private double latitude;

  /**
   * Makes a position.
   *
   * @param longitude the longitude of the position
   * @param latitude the latitude of the position
   */
  public Position(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public double getLatitude() {
    return latitude;
  }

}
