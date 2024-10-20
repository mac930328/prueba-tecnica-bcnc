package com.bcnc.ecommerce.pricing.infraestructure.rest.controllers;

import com.bcnc.ecommerce.pricing.application.dtos.PriceDto;
import com.bcnc.ecommerce.pricing.application.usecases.PriceUseCase;
import com.bcnc.ecommerce.pricing.infraestructure.rest.exceptions.GlobalError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(value = "/api/price", produces = MediaType.APPLICATION_JSON_VALUE)
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
            @Parameter(description = "Request with the application date information", required = true)
            @RequestParam("applicationDate")
            LocalDateTime applicationDate,
            @Parameter(description = "Request with the product id information", required = true)
            @RequestParam("productId")
            @Min(value = 1, message = "productId must be greater than 0")
            Long productId,
            @Parameter(description = "Request with the brand id information", required = true)
            @RequestParam("brandId")
            @Min(value = 1, message = "brandId must be greater than 0")
            Long brandId) {
        return priceUseCase.getPriceByDate(applicationDate, productId,
                brandId);
    }
}
