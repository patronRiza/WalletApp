package ru.prj.repository.implementations;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.prj.model.Wallet;
import ru.prj.repository.IWalletRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.prj.utils.Constants.*;

@Repository
public class WalletRepository implements IWalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public WalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Wallet> create(Long balance) {
        UUID id = jdbcTemplate.queryForObject(
                CREATE_WALLET,
                UUID.class,
                balance
        );
        return (id == null) ? Optional.empty() : Optional.of(new Wallet(id, balance));
    }

    @Override
    public List<Wallet> all() {
        return jdbcTemplate.query(GET_ALL_WALLET, walletRowMapper);
    }

    @Override
    public Optional<Wallet> find(UUID id) {
        return jdbcTemplate.query(GET_WALLET_BY_ID, walletRowMapper, id).stream().findFirst();
    }

    @Override
    public Optional<Wallet> update(UUID id, Long balance) {
        int update = jdbcTemplate.update(UPDATE_WALLET, balance, id);
        return (update == 0) ? Optional.empty() : Optional.of(new Wallet(id, balance));
    }

    @Override
    public int delete(UUID id) {
        return jdbcTemplate.update(DELETE_WALLET, id);
    }

    private final RowMapper<Wallet> walletRowMapper = (rs, rowNum) ->
            new Wallet(rs.getObject("id", UUID.class),
                    rs.getLong("balance")
            );
}
