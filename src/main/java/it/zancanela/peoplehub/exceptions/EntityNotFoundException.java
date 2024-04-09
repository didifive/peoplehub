package it.zancanela.peoplehub.exceptions;

public class EntityNotFoundException extends PersonHubException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
