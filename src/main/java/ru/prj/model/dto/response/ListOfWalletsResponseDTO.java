package ru.prj.model.dto.request;

import ru.prj.model.Wallet;

import java.util.List;

public record ListOfWalletsResponseDTO(List<Wallet> walletList) {
}
