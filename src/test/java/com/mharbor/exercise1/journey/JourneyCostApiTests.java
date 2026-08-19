package com.mharbor.exercise1.journey;

import com.mharbor.exercise1.journey.dto.JourneyCostReq;
import com.mharbor.exercise1.journey.dto.JourneyCostResp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Testing journey cost calculations.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class JourneyCostApiTests {
  /** Used to simulate HTTP requests. */
  @Autowired
  protected MockMvc mockMvc;
  /** Used to convert Java objects to JSON. */
  protected final ObjectMapper objectMapper = new ObjectMapper();

  //

  /**
   * Call mock journey cost calculation endpoint.
   * @param req Request.
   * @return Mock HTTP response.
   * @throws Exception Thrown by mockMvc.perform().
   */
  private MockHttpServletResponse callApi(JourneyCostReq req) throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/api/v1/journey/cost")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req)))
        .andReturn();
    return mvcResult.getResponse();
  }

  //

  @Test
  public void example() throws Exception {
    // Test for example given in task description.

    // Arrange: Prepare data.
    JourneyCostReq req = new JourneyCostReq(200f, 0.25f, null);

    // Act: Call endpoint.
    MockHttpServletResponse response = callApi(req);

    // Assert: Call was successful.
    assertThat(response.getStatus()).as("HTTP status is wrong").isEqualTo(HttpStatus.OK.value());

    // Assert: Expected result is returned.
    JourneyCostResp actualResp = objectMapper.readValue(response.getContentAsString(), JourneyCostResp.class);
    JourneyCostResp expectedResp = new JourneyCostResp(47.50f);
    assertThat(actualResp).isEqualTo(expectedResp);
  }

  @Test
  public void free() throws Exception {
    // Calculate price of journey for free journey.

    // Arrange: Prepare data.
    JourneyCostReq req = new JourneyCostReq(125f, 0.0f, null);

    // Act: Call endpoint.
    MockHttpServletResponse response = callApi(req);

    // Assert: Call was successful.
    assertThat(response.getStatus()).as("HTTP status is wrong").isEqualTo(HttpStatus.OK.value());

    // Assert: Expected result is returned.
    JourneyCostResp actualResp = objectMapper.readValue(response.getContentAsString(), JourneyCostResp.class);
    JourneyCostResp expectedResp = new JourneyCostResp(0.0f);
    assertThat(actualResp).isEqualTo(expectedResp);
  }

  @Test
  public void noDiscount() throws Exception {
    // Calculate price of journey for distance where discount does NOT apply (significantly below boundary).
    // Note it also verifies rounding (without it resulting price would be 7.5000005).

    // Arrange: Prepare data.
    JourneyCostReq req = new JourneyCostReq(50f, 0.15f, null);

    // Act: Call endpoint.
    MockHttpServletResponse response = callApi(req);

    // Assert: Call was successful.
    assertThat(response.getStatus()).as("HTTP status is wrong").isEqualTo(HttpStatus.OK.value());

    // Assert: Expected result is returned.
    JourneyCostResp actualResp = objectMapper.readValue(response.getContentAsString(), JourneyCostResp.class);
    JourneyCostResp expectedResp = new JourneyCostResp(7.5f);
    assertThat(actualResp).isEqualTo(expectedResp);
  }

  @Test
  public void boundary() throws Exception {
    // Test for distance boundary - exactly 100 km.

    // Arrange: Prepare data.
    JourneyCostReq req = new JourneyCostReq(100f, 0.33f, null);

    // Act: Call endpoint.
    MockHttpServletResponse response = callApi(req);

    // Assert: Call was successful.
    assertThat(response.getStatus()).as("HTTP status is wrong").isEqualTo(HttpStatus.OK.value());

    // Assert: Expected result is returned.
    JourneyCostResp actualResp = objectMapper.readValue(response.getContentAsString(), JourneyCostResp.class);
    JourneyCostResp expectedResp = new JourneyCostResp(33.0f);
    assertThat(actualResp).isEqualTo(expectedResp);
  }

  @Test
  public void overBoundary() throws Exception {
    // Test for distance boundary - just over 100 km.

    // Arrange: Prepare data.
    JourneyCostReq req = new JourneyCostReq(101f, 0.20f, null);

    // Act: Call endpoint.
    MockHttpServletResponse response = callApi(req);

    // Assert: Call was successful.
    assertThat(response.getStatus()).as("HTTP status is wrong").isEqualTo(HttpStatus.OK.value());

    // Assert: Expected result is returned.
    JourneyCostResp actualResp = objectMapper.readValue(response.getContentAsString(), JourneyCostResp.class);
    JourneyCostResp expectedResp = new JourneyCostResp(20.18f);
    assertThat(actualResp).isEqualTo(expectedResp);
  }

  @Test
  public void withDistanceDiscount() throws Exception {
    // Calculate price of journey for distance where discount does apply (significantly above boundary).

    // Arrange: Prepare data.
    JourneyCostReq req = new JourneyCostReq(300f, 1.0f, "RedGoFast");

    // Act: Call endpoint.
    MockHttpServletResponse response = callApi(req);

    // Assert: Call was successful.
    assertThat(response.getStatus()).as("HTTP status is wrong").isEqualTo(HttpStatus.OK.value());

    // Assert: Expected result is returned.
    JourneyCostResp actualResp = objectMapper.readValue(response.getContentAsString(), JourneyCostResp.class);
    JourneyCostResp expectedResp = new JourneyCostResp(280.0f);
    assertThat(actualResp).isEqualTo(expectedResp);
  }

  @Test
  public void withCustomerDiscount() throws Exception {
    // Calculate price of journey for distance where discount does NOT apply and with customer discount.

    // Arrange: Prepare data.
    JourneyCostReq req = new JourneyCostReq(100f, 1.0f, "TransX");

    // Act: Call endpoint.
    MockHttpServletResponse response = callApi(req);

    // Assert: Call was successful.
    assertThat(response.getStatus()).as("HTTP status is wrong").isEqualTo(HttpStatus.OK.value());

    // Assert: Expected result is returned.
    JourneyCostResp actualResp = objectMapper.readValue(response.getContentAsString(), JourneyCostResp.class);
    JourneyCostResp expectedResp = new JourneyCostResp(95.0f); // 0.05 of 100 is 5
    assertThat(actualResp).isEqualTo(expectedResp);
  }

  @Test
  public void withBothDiscounts() throws Exception {
    // Calculate price of journey for distance where discount does apply and with customer discount.

    // Arrange: Prepare data.
    JourneyCostReq req = new JourneyCostReq(300f, 1.0f, "TransX");

    // Act: Call endpoint.
    MockHttpServletResponse response = callApi(req);

    // Assert: Call was successful.
    assertThat(response.getStatus()).as("HTTP status is wrong").isEqualTo(HttpStatus.OK.value());

    // Assert: Expected result is returned.
    JourneyCostResp actualResp = objectMapper.readValue(response.getContentAsString(), JourneyCostResp.class);
    JourneyCostResp expectedResp = new JourneyCostResp(266.0f); // 0.05 of 280 is 14
    assertThat(actualResp).isEqualTo(expectedResp);
  }

  //

  @Test
  public void errInvalidDistance() throws Exception {
    // Ensures endpoint rejects invalid distance in request.

    // Arrange: Prepare data.
    JourneyCostReq req = new JourneyCostReq(-10f, 0.20f, null);

    // Act: Call endpoint.
    MockHttpServletResponse response = callApi(req);

    // Assert: Call failed.
    assertThat(response.getStatus()).as("HTTP status is wrong").isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void errInvalidCost() throws Exception {
    // Ensures endpoint rejects invalid cost in request.

    // Arrange: Prepare data.
    JourneyCostReq req = new JourneyCostReq(10f, null, null);

    // Act: Call endpoint.
    MockHttpServletResponse response = callApi(req);

    // Assert: Call failed.
    assertThat(response.getStatus()).as("HTTP status is wrong").isEqualTo(HttpStatus.BAD_REQUEST.value());
  }
}
