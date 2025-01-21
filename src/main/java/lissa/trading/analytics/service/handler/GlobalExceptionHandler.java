package lissa.trading.analytics.service.handler;

import jakarta.validation.ConstraintViolationException;
import lissa.trading.analytics.service.dto.ErrorResponse;
import lissa.trading.analytics.service.exception.CandlesNotFoundException;
import lissa.trading.analytics.service.exception.FinamClientException;
import lissa.trading.analytics.service.exception.Messages;
import lissa.trading.analytics.service.exception.TinkoffPulseClientException;
import lissa.trading.analytics.service.exception.TinkoffTokenException;
import lissa.trading.analytics.service.exception.UnsupportedSourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final Messages messages;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> messages.getMessage("error.constraint.violation",
                        violation.getPropertyPath().toString().split("\\.")[1],
                        violation.getMessage()))
                .collect(Collectors.joining(", "));

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", errorMessage, status.value());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(CandlesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleCandlesNotFoundException(CandlesNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND",
                messages.getMessage("error.candles.notfound"), status.value());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(FinamClientException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ErrorResponse> handleFinamClientException(FinamClientException ex) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        ErrorResponse errorResponse = new ErrorResponse("SERVICE_UNAVAILABLE",
               messages.getMessage("error.finam.client", ex.getMessage()), status.value());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(TinkoffPulseClientException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ErrorResponse> handleTinkoffPulseNewsClientException(TinkoffPulseClientException ex) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        ErrorResponse errorResponse = new ErrorResponse("SERVICE_UNAVAILABLE",
                messages.getMessage("error.pulse.client", ex.getMessage()), status.value());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(UnsupportedSourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUnsupportedSourceException(UnsupportedSourceException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST",
                messages.getMessage("error.unsupported.source"), status.value());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(TinkoffTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUnsupportedSourceException(TinkoffTokenException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST",
                messages.getMessage("error.token.notfound"), status.value());
        return new ResponseEntity<>(errorResponse, status);
    }
}

