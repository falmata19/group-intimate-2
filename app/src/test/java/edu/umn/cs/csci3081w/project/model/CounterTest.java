package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Counter class.
 */
public class CounterTest {

  private Counter counter;

  /**
   * Makes a new counter before each test.
   */
  @BeforeEach
  public void setUp() {
    counter = new Counter();
  }

  /**
   * Tests that line ids start at 1 and go up by one each call.
   */
  @Test
  public void testGetLineIdCounterAndIncrement() {
    assertEquals(1, counter.getLineIdCounterAndIncrement());
    assertEquals(2, counter.getLineIdCounterAndIncrement());
    assertEquals(3, counter.lineIdCounter);
  }

  /**
   * Tests that route ids start at 10 and go up by one each call.
   */
  @Test
  public void testGetRouteIdCounterAndIncrement() {
    assertEquals(10, counter.getRouteIdCounterAndIncrement());
    assertEquals(11, counter.getRouteIdCounterAndIncrement());
  }

  /**
   * Tests that stop ids start at 100 and go up by one each call.
   */
  @Test
  public void testGetStopIdCounterAndIncrement() {
    assertEquals(100, counter.getStopIdCounterAndIncrement());
    assertEquals(101, counter.getStopIdCounterAndIncrement());
  }

  /**
   * Tests that bus ids start at 1000 and go up by one each call.
   */
  @Test
  public void testGetBusIdCounterAndIncrement() {
    assertEquals(1000, counter.getBusIdCounterAndIncrement());
    assertEquals(1001, counter.getBusIdCounterAndIncrement());
  }

  /**
   * Tests that train ids start at 2000 and go up by one each call.
   */
  @Test
  public void testGetTrainIdCounterAndIncrement() {
    assertEquals(2000, counter.getTrainIdCounterAndIncrement());
    assertEquals(2001, counter.getTrainIdCounterAndIncrement());
  }
}
