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
  /** Rate of distance discount. */
  private static final float DISCOUNT_RATE = 0.1f;
  /** Price multiplier. */
  private static final float PRICE_MUL = 1f - DISCOUNT_RATE;

  /** Name of customer eligible for customer discount: TransX. */
  private static final String CUSTOMER_DISCOUNT_NAME = "TransX";
  /** Rate of customer discount. */
  private static final float CUSTOMER_DISCOUNT_RATE = 0.05f;


  /**
   * Calculate price of journey.
   * @param journeyCostReq Data needed to determine the price.
   * @return Final price of journey.
   */
  public JourneyCostResp calculate(JourneyCostReq journeyCostReq) {
    float price = calcPriceWithDistanceDiscount(journeyCostReq);
    price = calcPriceWithCustomerDiscount(price, journeyCostReq);
    return JourneyCostResp.builder()
        .price(PriceHelper.round(price))
        .build();
  }

  /**
   * Calculates price of journey. Takes distance discount in account.
   * @param journeyCostReq Data needed to determine the price.
   * @return Price of journey with distance discount.
   */
  private float calcPriceWithDistanceDiscount(JourneyCostReq journeyCostReq) {
    float price;
    if (journeyCostReq.distance() <= NODISCOUNT_DISTANCE) price = journeyCostReq.distance() * journeyCostReq.cost();
    else {
      float noDiscountPrice = NODISCOUNT_DISTANCE * journeyCostReq.cost();
      float discountedDistance = journeyCostReq.distance() - NODISCOUNT_DISTANCE;
      float discountedPrice = discountedDistance * journeyCostReq.cost() * PRICE_MUL;
      price = noDiscountPrice + discountedPrice;
    }
    return price;
  }

  /**
   * Calculate new price taking customer discount in account, if any.
   * Note: customer name is case-sensitive.
   * @param price Base price with distance discount already applied.
   * @param journeyCostReq Data needed to determine the price.
   * @return Price of journey with customer discount, if any.
   */
  private float calcPriceWithCustomerDiscount(float price, JourneyCostReq journeyCostReq) {
    if (CUSTOMER_DISCOUNT_NAME.equals(journeyCostReq.customer())) {
      price = price * (1 - CUSTOMER_DISCOUNT_RATE);
    }
    return price;
  }
}
