package ru.prj.exceptions;

public class EmptyWalletException extends RuntimeException {
    public EmptyWalletException(String message) {
        super(message);
    }
}
