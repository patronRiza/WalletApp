package ru.prj.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Wallet {
    private UUID id;
    private Long balance;

    public Wallet(UUID id, Long balance) {
        this.id = id;
        this.balance = balance;
    }
}
