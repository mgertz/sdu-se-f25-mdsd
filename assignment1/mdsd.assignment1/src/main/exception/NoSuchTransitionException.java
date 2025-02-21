package main.exception;

import java.util.NoSuchElementException;

public class NoSuchTransitionException extends NoSuchElementException {
    public NoSuchTransitionException(String s) {
        super(s);
    }
}
