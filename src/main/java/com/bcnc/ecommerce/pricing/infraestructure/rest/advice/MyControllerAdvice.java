package com.bcnc.ecommerce.pricing.infraestructure.rest.advice;

import com.bcnc.ecommerce.pricing.domain.exceptions.NotFoundObjectException;
import com.bcnc.ecommerce.pricing.infraestructure.rest.exceptions.GlobalError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(NotFoundObjectException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GlobalError notFoundObjectExceptionHandler(NotFoundObjectException notFoundObjectException) {
        return GlobalError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(notFoundObjectException.getMessage())
                .date(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalError nullPointerExceptionHandler(NullPointerException nullPointerException) {
        return GlobalError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(nullPointerException.getMessage())
                .date(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalError illegalArgumentExceptionHandler(IllegalArgumentException illegalArgumentException) {
        return GlobalError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(illegalArgumentException.getMessage())
                .date(LocalDateTime.now())
                .build();
    }


}
