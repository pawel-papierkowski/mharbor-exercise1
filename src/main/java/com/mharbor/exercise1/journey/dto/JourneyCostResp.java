package com.mharbor.exercise1.journey.dto;

import lombok.Builder;

/**
 * Journey cost calculation response.
 * @param price Final price of journey.
 */
@Builder(toBuilder = true)
public record JourneyCostResp(
    Float price
) {}
