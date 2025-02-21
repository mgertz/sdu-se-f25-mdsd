package main.exception;

public class NoEventSetException extends RuntimeException {
    public NoEventSetException(final String stateName) {
        super(stateName);
    }
}
