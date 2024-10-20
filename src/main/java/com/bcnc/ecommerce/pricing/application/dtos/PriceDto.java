package com.bcnc.ecommerce.pricing.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Price information")
@Builder
public class PriceDto {


    @Schema(description = "Price list identifier", example = "1")
    private Long priceList;
    @Schema(description = "Product ID", example = "35455")
    private Long productId;
    @Schema(description = "Brand ID", example = "1")
    private Long brandId;
    @Schema(description = "Start date of price application", example = "2020-06-14T00:00:00")
    private LocalDateTime startDate;
    @Schema(description = "End date of price application", example = "2020-12-31T23:59:59")
    private LocalDateTime endDate;
    @Schema(description = "Final price", example = "35.50")
    private BigDecimal price;

}
