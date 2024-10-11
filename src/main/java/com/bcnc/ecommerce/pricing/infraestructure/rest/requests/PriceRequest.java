package com.bcnc.ecommerce.pricing.infraestructure.rest.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
@Schema(description = "Query parameters for price consultation")
public class PriceRequest {

    private static final String NULL_MSM = "%s cannot be null";
    private static final String POSITIVE_MSM = "%s must be positive";
    public static final String APPLICATION_DATE_NULL = String.format(NULL_MSM, "Application date");
    public static final String PRODUCT_ID_NULL = String.format(NULL_MSM, "Product ID");
    public static final String BRAND_ID_NULL = String.format(NULL_MSM, "Brand ID");
    public static final String PRODUCT_ID_POSITIVE = String.format(POSITIVE_MSM, "Product ID");
    public static final String BRAND_ID_POSITIVE = String.format(POSITIVE_MSM, "Brand ID");

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "Application date for the price", example = "2020-06-14T16:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private final LocalDateTime applicationDate;

    @Schema(description = "Product ID", example = "35455", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Long productId;

    @Schema(description = "Brand ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Long brandId;

    public PriceRequest(LocalDateTime applicationDate, Long productId, Long brandId) {
        this.applicationDate = Objects.requireNonNull(applicationDate, APPLICATION_DATE_NULL);
        this.productId = Objects.requireNonNull(productId, PRODUCT_ID_NULL);
        this.brandId = Objects.requireNonNull(brandId, BRAND_ID_NULL);

        if (productId <= 0) {
            throw new IllegalArgumentException(PRODUCT_ID_POSITIVE);
        }
        if (brandId <= 0) {
            throw new IllegalArgumentException(BRAND_ID_POSITIVE);
        }
    }
}
