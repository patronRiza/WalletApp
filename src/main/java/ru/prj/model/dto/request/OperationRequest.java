package ru.prj.model.dto.request;

import java.util.UUID;

public record OperationRequest(String walletId, String operationType, Long amount) {}
