package lissa.trading.analytics.service.exception;

public class EmptyTickersListException extends RuntimeException {
    public EmptyTickersListException(String message) {
        super(message);
    }

    public EmptyTickersListException(String message, Throwable cause) {
        super(message, cause);
    }
}
