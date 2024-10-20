package com.bcnc.ecommerce.pricing.infraestructure.rest.advice;

import com.bcnc.ecommerce.pricing.domain.exceptions.NotFoundObjectException;
import com.bcnc.ecommerce.pricing.infraestructure.rest.exceptions.GlobalError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Optional;

@ControllerAdvice
@ResponseBody
public class MyControllerAdvice {

    @ExceptionHandler({NotFoundObjectException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GlobalError notFoundObjectExceptionHandler(NotFoundObjectException notFoundObjectException) {
        return GlobalError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(notFoundObjectException.getMessage())
                .date(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalError handleConstraintViolation(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .reduce((first, second) -> first + ", " + second)
                .orElse("Validation errors");
        return GlobalError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .date(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalError handleMissingParams(MissingServletRequestParameterException ex) {
        String errorMessage = "Missing parameter: " + ex.getParameterName();
        return GlobalError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .date(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalError handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("Parameter '%s' should be of type %s. Provided value: %s",
                ex.getName(),
                Optional.ofNullable(ex.getRequiredType()).map(Class::getSimpleName).orElse("unknown"),
                ex.getValue()
        );

        return GlobalError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .date(LocalDateTime.now())
                .build();
    }

}
