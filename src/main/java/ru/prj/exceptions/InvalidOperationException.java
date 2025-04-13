package ru.prj.exceptions;

public class WalletCreationException extends RuntimeException {
    public WalletCreationException(String message) {
        super(message);
    }
}
