package ru.prj.utils;

public class Constants {
    public static final String CREATE_WALLET = """
            insert into wallets(balance)
            values(?)
            """;
    public static final String GET_ALL_WALLET = "select * from wallets";
    public static final String GET_WALLET_BY_ID = "select * from wallets where id=? for update";
    public static final String UPDATE_WALLET = "update wallets set balance = balance + ? where id=?";
    public static final String DELETE_WALLET = "delete wallets where id=?";

    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAW = "WITHDRAW";

    private Constants() {
    }
}
