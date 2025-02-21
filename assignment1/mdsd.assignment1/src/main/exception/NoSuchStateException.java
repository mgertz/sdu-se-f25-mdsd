package main.exception;

import java.util.NoSuchElementException;

public class NoSuchStateException extends NoSuchElementException {
    public NoSuchStateException(String s) {
        super(s);
    }
}
