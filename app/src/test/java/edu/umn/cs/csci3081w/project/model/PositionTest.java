package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Position class.
 */
public class PositionTest {

  /**
   * Tests that the constructor stores the longitude (first argument) and the latitude (second
   * argument) in the right order.
   */
  @Test
  public void testConstructorStoresLongitudeThenLatitude() {
    Position position = new Position(-93.243774, 44.972392);
    assertEquals(-93.243774, position.getLongitude());
    assertEquals(44.972392, position.getLatitude());
  }
}
