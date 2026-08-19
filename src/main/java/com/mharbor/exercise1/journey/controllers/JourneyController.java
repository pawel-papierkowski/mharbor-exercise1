package com.mharbor.exercise1.journey.controllers;

import com.mharbor.exercise1.journey.dto.JourneyCostReq;
import com.mharbor.exercise1.journey.dto.JourneyCostResp;
import com.mharbor.exercise1.journey.services.JourneyCostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that handles journey-related features like cost calculations.
 */
@RestController
@RequestMapping("/api/v1/journey")
@RequiredArgsConstructor
public class JourneyController {
  private final JourneyCostService journeyCostService;

  /**
   * Calculates cost of journey.
   * @param journeyCostReq Data needed to calculate cost.
   * @return Result of calculations.
   */
  @PostMapping(value = "/cost", produces = "application/json")
  public ResponseEntity<JourneyCostResp> calculateCost(@Valid @RequestBody JourneyCostReq journeyCostReq) {
    JourneyCostResp resp = journeyCostService.calculate(journeyCostReq);
    return new ResponseEntity<>(resp, HttpStatus.OK);
  }
}
