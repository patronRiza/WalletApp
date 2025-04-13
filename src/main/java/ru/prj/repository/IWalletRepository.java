package ru.prj.repository;

import ru.prj.model.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IWalletRepository {
    Optional<Wallet> create(Long balance);
    List<Wallet> all();
    Optional<Wallet> find(UUID id);
    Optional<Wallet> update(UUID id, Long balance);
    int delete(UUID id);
}
