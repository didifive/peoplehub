package it.zancanela.peoplehub.exceptions;

public class BadRequestBodyException extends PersonHubException {
    public BadRequestBodyException(String message) {
        super(message);
    }
}
