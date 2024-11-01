package lissa.trading.analytics.service.exception;

public class XmlParsingException extends RuntimeException {
    public XmlParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlParsingException(String message) {
        super(message);
    }
}
