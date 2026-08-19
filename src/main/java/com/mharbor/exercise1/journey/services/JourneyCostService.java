package com.mharbor.exercise1.journey.services;

import com.mharbor.exercise1.journey.dto.JourneyCostReq;
import com.mharbor.exercise1.journey.dto.JourneyCostResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Handles cost calculations for journeys.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JourneyCostService {
  /** Maximum distance where discount do not apply. */
  private static final float NODISCOUNT_DISTANCE = 100f;
  /** Rate of discount. */
  private static final float DISCOUNT_RATE = 0.1f;
  /** Price multiplier. */
  private static final float PRICE_MUL = 1f - DISCOUNT_RATE;

  /**
   * Calculate price of journey.
   * @param journeyCostReq Data needed to determine the price.
   * @return Final price of journey.
   */
  public JourneyCostResp calculate(JourneyCostReq journeyCostReq) {
    float price = calcPrice(journeyCostReq);
    return JourneyCostResp.builder()
        .price(price)
        .build();
  }

  /**
   * Calculates price of journey. Takes discount in account.
   * @param journeyCostReq Data needed to determine the price.
   * @return Final price of journey.
   */
  private float calcPrice(JourneyCostReq journeyCostReq) {
    float finalPrice;
    if (journeyCostReq.distance() <= NODISCOUNT_DISTANCE) finalPrice = journeyCostReq.distance() * journeyCostReq.cost();
    else {
      float noDiscountPrice = NODISCOUNT_DISTANCE * journeyCostReq.cost();
      float discountedDistance = journeyCostReq.distance() - NODISCOUNT_DISTANCE;
      float discountedPrice = discountedDistance * journeyCostReq.cost() * PRICE_MUL;
      finalPrice = noDiscountPrice + discountedPrice;
    }

    // Handles floating point precision issues. This is currency value, so cent-precision is correct.
    return Math.round(finalPrice * 100f) / 100f;
  }
}
