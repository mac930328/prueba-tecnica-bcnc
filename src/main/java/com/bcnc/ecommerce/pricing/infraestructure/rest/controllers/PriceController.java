package com.bcnc.ecommerce.pricing.infraestructure.rest.controllers;

import com.bcnc.ecommerce.pricing.application.dtos.PriceDto;
import com.bcnc.ecommerce.pricing.application.usecases.PriceUseCase;
import com.bcnc.ecommerce.pricing.infraestructure.rest.exceptions.GlobalError;
import com.bcnc.ecommerce.pricing.infraestructure.rest.requests.PriceRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/price")
@Tag(name = "Price", description = "Price consultation API")
public class PriceController {

    private final PriceUseCase priceUseCase;

    @Operation(summary = "Get price for a product",
            description = "Get the applicable price for a product at a specific date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceDto.class))}),
            @ApiResponse(responseCode = "404", description = "Price not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GlobalError.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalError.class))})
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PriceDto getPriceByDate(
            @Parameter(description = "Request with the application date, product id and brand id information", required = true)
            @ModelAttribute PriceRequest priceRequest) {
        return priceUseCase.getPriceByDate(priceRequest.getApplicationDate(), priceRequest.getProductId(),
                priceRequest.getBrandId());
    }
}
