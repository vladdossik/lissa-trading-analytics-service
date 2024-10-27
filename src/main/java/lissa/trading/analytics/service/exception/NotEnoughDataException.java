package lissa.trading.analytics.service.exception;

public class NotEnoughDataException extends RuntimeException{
    public NotEnoughDataException(String message){
        super(message);
    }
}
