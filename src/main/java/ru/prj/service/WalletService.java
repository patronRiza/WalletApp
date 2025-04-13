package ru.prj.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prj.exceptions.EmptyWalletException;
import ru.prj.exceptions.InvalidOperationException;
import ru.prj.exceptions.WalletCreationException;
import ru.prj.model.Wallet;
import ru.prj.model.dto.request.OperationRequest;
import ru.prj.repository.IWalletRepository;
import ru.prj.repository.implementations.WalletRepository;
import ru.prj.utils.RequestsValidator;

import java.util.List;
import java.util.UUID;

import static ru.prj.utils.Constants.DEPOSIT;
import static ru.prj.utils.Constants.WITHDRAW;

@Service
@Transactional
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
        return walletRepository.findWithBlock(id).orElseThrow(() -> new EmptyWalletException("Could not find wallet"));
    }

    public String delete(UUID id) throws EmptyWalletException {
        return walletRepository.delete(id) == 1 ? "Successful to delete wallet" : "Failed to delete wallet";
    }

    public Long getBalance(String request) throws EmptyWalletException {
        RequestsValidator.validateOperationUUID(request);
        UUID id = UUID.fromString(request);
        return findWalletById(id).getBalance();
    }

    public String operate(OperationRequest request) throws EmptyWalletException, InvalidOperationException {
        RequestsValidator.validateOperation(request);
        UUID id = UUID.fromString(request.walletId());
        return switch (request.operationType()) {
            case DEPOSIT -> deposit(id, request.amount());
            case WITHDRAW -> withdraw(id, request.amount());
            default -> throw new InvalidOperationException("Invalid operation type");
        };
    }

    private String deposit(UUID id, Long amount) throws EmptyWalletException {
        findWalletById(id);
        Boolean success = walletRepository.update(id, amount);
        if (Boolean.FALSE.equals(success))
            throw new InvalidOperationException("Could not deposit");
        return "Successfully deposited";
    }

    private String withdraw(UUID id, Long amount) throws EmptyWalletException, InvalidOperationException {
        Wallet wallet = findWalletById(id);
        if (wallet.getBalance() - amount < 0)
            throw new InvalidOperationException("You cannot perform the operation, there are not enough funds on your balance.");
        walletRepository.update(id, -amount);
        return "Successfully withdrawn";
    }
}
