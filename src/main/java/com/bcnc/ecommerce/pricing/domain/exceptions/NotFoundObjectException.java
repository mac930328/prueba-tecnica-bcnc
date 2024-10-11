package com.bcnc.ecommerce.pricing.domain.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotFoundObjectException extends RuntimeException {

    public NotFoundObjectException(String message) {
        super(message);
    }

}
