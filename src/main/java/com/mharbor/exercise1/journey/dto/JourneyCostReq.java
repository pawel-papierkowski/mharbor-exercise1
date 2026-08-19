package com.mharbor.exercise1.journey.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

/**
 * Journey cost calculation request.
 * @param distance Distance in kilometres.
 * @param cost Cost per kilometre in euro.
 * @param customer Customer name. Can be null.
 */
@Builder(toBuilder = true)
public record JourneyCostReq(
    @PositiveOrZero
    @NotNull
    Float distance,
    @PositiveOrZero
    @NotNull
    Float cost,
    String customer
) {}
