package lissa.trading.analytics.service.exception;

public class CandlesNotFoundException extends RuntimeException {
    public CandlesNotFoundException(String message) {
        super(message);
    }
}
