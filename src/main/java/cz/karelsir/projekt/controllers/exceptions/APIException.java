package cz.karelsir.projekt.controllers.exceptions;

public class APIException extends RuntimeException {

    public APIException(String message) {
        super(message);
    }
}
