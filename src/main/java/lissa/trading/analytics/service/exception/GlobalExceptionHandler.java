package lissa.trading.analytics.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmptyTickersListException.class)
    public ResponseEntity<ErrorResponse> handleEmptyTickersListException(EmptyTickersListException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", ex.getMessage(), status.value());
        return new ResponseEntity<>(errorResponse, status);
    }
}
