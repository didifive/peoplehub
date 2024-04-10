package it.zancanela.peoplehub.exceptions;

public class DataIntegrityViolationException extends PersonHubException {
    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
