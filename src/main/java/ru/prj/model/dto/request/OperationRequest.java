package ru.prj.model.dto.request;

import java.util.UUID;

public record OperationRequest(UUID walletId, String operationType, Long amount) {
}
