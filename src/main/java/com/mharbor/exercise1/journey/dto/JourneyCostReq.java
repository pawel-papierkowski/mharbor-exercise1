package com.mharbor.exercise1.journey.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

/**
 * Journey cost calculation request.
 * @param distance Distance in kilometers.
 * @param cost Cost per kilometre in euro.
 */
@Builder(toBuilder = true)
public record JourneyCostReq(
    @PositiveOrZero
    @NotNull
    Float distance,
    @PositiveOrZero
    @NotNull
    Float cost
) {}
