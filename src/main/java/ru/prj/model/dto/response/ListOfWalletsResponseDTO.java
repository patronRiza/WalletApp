package ru.prj.model.dto.response;

import ru.prj.model.Wallet;

import java.util.List;

public record ListOfWalletsResponseDTO(List<Wallet> walletList) {
}
