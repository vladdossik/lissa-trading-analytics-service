package lissa.trading.analytics.service.handler;

import jakarta.validation.ConstraintViolationException;
import lissa.trading.analytics.service.dto.ErrorResponse;
import lissa.trading.analytics.service.exception.UnsupportedSourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath()
                        .toString()
                        .split("\\.")[1] + " " + violation.getMessage())
                .collect(Collectors.joining(", "));

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", errorMessage, status.value());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(UnsupportedSourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUnsupportedSourceException(UnsupportedSourceException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", ex.getMessage(),
                status.value());
        return new ResponseEntity<>(errorResponse, status);
    }
}
