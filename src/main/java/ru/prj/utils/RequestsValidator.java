package ru.prj.utils;


import ru.prj.exceptions.InvalidArgumentsInRequest;
import ru.prj.model.dto.request.OperationRequest;

import java.util.regex.Pattern;

public class RequestsValidator {
    private RequestsValidator() {
    }

    private static final String ID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static void validateOperation(OperationRequest request) {

        validateOperationUUID(request.walletId());

        if (request.operationType() == null)
            throw new InvalidArgumentsInRequest("Operation type cannot be empty");
        if (!isValidType(request.operationType()))
            throw new InvalidArgumentsInRequest("Operation type must be either DEPOSIT or WITHDRAW");

        if (request.amount() == null)
            throw new InvalidArgumentsInRequest("Amount cannot be empty");
        if (request.amount() < 0)
            throw new InvalidArgumentsInRequest("Amount cannot be negative");
    }

    public static void validateOperationUUID(String id) {
        if (id == null)
            throw new InvalidArgumentsInRequest("ID cannot be empty");
        if (!isValidID(id))
            throw new InvalidArgumentsInRequest("Invalid UUID format");
    }

    private static boolean isValidID(String id) {
        Pattern pattern = Pattern.compile(ID_REGEX);
        return pattern.matcher(id).matches();
    }

    private static boolean isValidType(String type) {
        return Pattern.matches("^(WITHDRAW|DEPOSIT)$", type);
    }
}
