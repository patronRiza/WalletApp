package ru.prj.exceptions;

public class InvalidArgumentsInRequest extends RuntimeException {
    public InvalidArgumentsInRequest(String message) {
        super(message);
    }
}
