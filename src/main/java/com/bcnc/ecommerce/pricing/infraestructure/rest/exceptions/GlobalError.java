package com.bcnc.ecommerce.pricing.infraestructure.rest.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Data
@Schema(description = "Global error")
public class GlobalError {

    @Schema(description = "Http status", example = "404")
    private Integer status;
    @Schema(description = "Error message", example = "Bad request")
    private String message;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "Current date", example = "2020-06-14T16:00:00")
    private LocalDateTime date;

}
