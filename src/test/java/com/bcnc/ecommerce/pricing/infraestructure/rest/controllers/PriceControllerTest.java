package com.bcnc.ecommerce.pricing.infraestructure.rest.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({
            "2020-06-14T10:00:00, 35455, 1, 35.50, 2020-06-14T00:00:00, 2020-12-31T23:59:59",
            "2020-06-14T16:00:00, 35455, 1, 25.45, 2020-06-14T15:00:00, 2020-06-14T18:30:00",
            "2020-06-14T21:00:00, 35455, 1, 35.50, 2020-06-14T00:00:00, 2020-12-31T23:59:59",
            "2020-06-15T10:00:00, 35455, 1, 30.50, 2020-06-15T00:00:00, 2020-06-15T11:00:00",
            "2020-06-15T21:00:00, 35455, 1, 38.95, 2020-06-15T16:00:00, 2020-12-31T23:59:59"
    })
    void testPriceRequest(String applicationDate, int productId, int brandId, double expectedPrice,
                          String startDate, String endDate) throws Exception {
        mockMvc.perform(get("/api/price")
                        .param("applicationDate", applicationDate)
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.startDate").value(startDate))
                .andExpect(jsonPath("$.endDate").value(endDate));
    }

    @Test
    void testNotFoundPrice() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);

        mockMvc.perform(get("/api/price")
                        .param("applicationDate", date.toString())
                        .param("productId", "35455")
                        .param("brandId", "2"))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @MethodSource("getInvalidTestData")
    void testInvalidParameters(String applicationDate, String productId, String brandId, int httpStatus, String message) throws Exception {
        mockMvc.perform(get("/api/price")
                        .param("applicationDate", applicationDate)
                        .param("productId", productId)
                        .param("brandId", brandId))
                .andExpect(status().is(httpStatus))
                .andExpect(jsonPath("$.message").value(message));
    }


    static Stream<Arguments> getInvalidTestData() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        int httpStatusValue = HttpStatus.BAD_REQUEST.value();
        return Stream.<Arguments>builder()
                .add(Arguments.of(null, "35455", "1", httpStatusValue, "Missing parameter: applicationDate"))
                .add(Arguments.of(date, null, "1", httpStatusValue, "Missing parameter: productId"))
                .add(Arguments.of(date, "35455", null, httpStatusValue, "Missing parameter: brandId"))
                .add(Arguments.of(date, "35455", "-1", httpStatusValue, "brandId must be greater than 0"))
                .add(Arguments.of(date, "-35455", "1", httpStatusValue, "productId must be greater than 0"))
                .add(Arguments.of(date, "-35455", "HOLA", httpStatusValue, "Parameter 'brandId' should be of type Long. Provided value: HOLA"))
                .add(Arguments.of(date, "HOLA", "1", httpStatusValue, "Parameter 'productId' should be of type Long. Provided value: HOLA"))
                .build();
    }

}
