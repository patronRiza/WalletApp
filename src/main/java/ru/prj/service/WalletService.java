package ru.prj.service;

import org.springframework.stereotype.Service;
import ru.prj.exceptions.EmptyWalletException;
import ru.prj.exceptions.InvalidOperationException;
import ru.prj.exceptions.WalletCreationException;
import ru.prj.model.Wallet;
import ru.prj.model.dto.request.OperationRequest;
import ru.prj.repository.IWalletRepository;
import ru.prj.repository.implementations.WalletRepository;

import java.util.List;
import java.util.UUID;

import static ru.prj.utils.Constants.DEPOSIT;
import static ru.prj.utils.Constants.WITHDRAW;

@Service
public class WalletService {
    private final IWalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(Long balance) throws WalletCreationException {
        return walletRepository.create(balance).orElseThrow(() -> new WalletCreationException("Could not create wallet"));
    }

    public List<Wallet> getWallets() throws EmptyWalletException {
        List<Wallet> wallets = walletRepository.all();
        if (wallets.isEmpty())
            throw new EmptyWalletException("Could not get wallets");
        return wallets;
    }

    public Wallet findWalletById(UUID id) throws EmptyWalletException {
        return walletRepository.find(id).orElseThrow(() -> new EmptyWalletException("Could not find wallet"));
    }

    public Wallet update(UUID id, Long balance) throws EmptyWalletException {
        return walletRepository.update(id, balance).orElseThrow(() -> new EmptyWalletException("Could not update wallet"));
    }

    public String delete(UUID id) throws EmptyWalletException {
        return walletRepository.delete(id) == 1 ? "Successful to delete wallet" : "Failed to delete wallet";
    }

    public Long getBalance(UUID id) throws EmptyWalletException {
        return findWalletById(id).getBalance();
    }

    public String operate(OperationRequest request) throws EmptyWalletException, InvalidOperationException {

        return switch (request.operationType()){
            case DEPOSIT -> deposit(request.walletId(), request.amount());
            case WITHDRAW -> withdraw(request.walletId(), request.amount());
            default -> throw new InvalidOperationException("Invalid operation type");
        };
    }

    private String deposit(UUID id, Long amount) throws EmptyWalletException {
        Long balance = getBalance(id);
        walletRepository.update(id, balance + amount);
        return "Successfully deposited";
    }

    private String withdraw(UUID id, Long amount) throws EmptyWalletException, InvalidOperationException {
        Long balance = getBalance(id);
        if (balance - amount < 0)
            throw new InvalidOperationException("You cannot perform the operation, there are not enough funds on your balance.");
        walletRepository.update(id, balance - amount);
        return "Successfully withdrawn";
    }
}
