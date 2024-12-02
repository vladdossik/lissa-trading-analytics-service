package lissa.trading.analytics.service.handler;

import jakarta.validation.ConstraintViolationException;
import lissa.trading.analytics.service.dto.ErrorResponse;
import lissa.trading.analytics.service.exception.CandlesNotFoundException;
import lissa.trading.analytics.service.exception.FinamClientException;
import lissa.trading.analytics.service.exception.TinkoffPulseClientException;
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

    @ExceptionHandler(CandlesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleCandlesNotFoundException(CandlesNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", ex.getMessage(), status.value());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(FinamClientException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ErrorResponse> handleFinamClientException(FinamClientException ex) {
        String errorMessage = "Проблема с подключением к клиенту Finam: " + ex.getMessage();
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        ErrorResponse errorResponse = new ErrorResponse("SERVICE_UNAVAILABLE", errorMessage, status.value());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(TinkoffPulseClientException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ErrorResponse> handleTinkoffPulseNewsClientException(TinkoffPulseClientException ex) {
        String errorMessage = "Проблема с подключением к клиенту Tinkoff-pulse: " + ex.getMessage();
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        ErrorResponse errorResponse = new ErrorResponse("SERVICE_UNAVAILABLE", errorMessage, status.value());
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
