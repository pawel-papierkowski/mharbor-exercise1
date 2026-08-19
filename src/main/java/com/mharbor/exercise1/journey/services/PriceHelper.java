package com.mharbor.exercise1.journey.services;

/**
 * Helper functions for price calculations.
 */
public class PriceHelper {
  private PriceHelper() {
  }

  /**
   * Round price to handle floating point precision issues. This is currency value, so cent-precision is correct.
   * @param rawPrice Price before rounding.
   * @return Rounded price.
   */
  public static float round(float rawPrice) {
    return Math.round(rawPrice * 100f) / 100f;
  }
}
