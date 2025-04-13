package ru.prj.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.prj.exceptions.EmptyWalletException;
import ru.prj.exceptions.InvalidOperationException;
import ru.prj.model.Wallet;
import ru.prj.model.dto.request.OperationRequest;
import ru.prj.model.dto.response.BalanceResponseDTO;
import ru.prj.model.dto.response.ErrorModel;
import ru.prj.model.dto.response.ListOfWalletsResponseDTO;
import ru.prj.service.WalletService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ru.prj.utils.Constants.DEPOSIT;

@ExtendWith(MockitoExtension.class)
class WalletControllerTest {

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @Test
    @DisplayName("Получение баланса кошелька")
    void getBalanceOfWallet() {
        UUID id = UUID.randomUUID();
        Long expectedBalance = 100L;
        when(walletService.getBalance(id)).thenReturn(expectedBalance);

        BalanceResponseDTO responseDTO = walletController.getBalanceOfWallet(id);

        assertEquals(expectedBalance, responseDTO.balance());
        verify(walletService, times(1)).getBalance(id);
    }

    @Test
    @DisplayName("Получение всех кошельков")
    void getAllWallet() {
        List<Wallet> wallets = List.of(new Wallet(UUID.randomUUID(), 100L), new Wallet(UUID.randomUUID(), 200L));
        when(walletService.getWallets()).thenReturn(wallets);

        ListOfWalletsResponseDTO allWallet = walletController.getAllWallet();
        assertEquals(wallets, allWallet.walletList());
        verify(walletService, times(1)).getWallets();
    }

    @Test
    @DisplayName("Изменение баланса кошелька")
    void changeBalance() {
        OperationRequest request = new OperationRequest(UUID.randomUUID(), DEPOSIT, 50L);
        when(walletService.operate(request)).thenReturn("Operation Success");

        String response = walletController.changeBalance(request).response();

        assertEquals("Operation Success", response);
        verify(walletService, times(1)).operate(request);
    }

    @Test
    @DisplayName("Проверка метода по отслеживания ошибки пустого кошелька")
    void handleEmptyWalletException_shouldReturnNotFound() {
        String errorMessage = "Wallet not found";
        EmptyWalletException exception = new EmptyWalletException(errorMessage);

        ResponseEntity<ErrorModel> response = walletController.handleStudentNotExistException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, Objects.requireNonNull(response.getBody()).message());
    }

    @Test
    @DisplayName("Проверка метода по отслеживания ошибки неверной операции")
    void handleInvalidOperationException_shouldReturnNotFound() {
        String errorMessage = "Invalid operation";
        InvalidOperationException exception = new InvalidOperationException(errorMessage);

        ResponseEntity<ErrorModel> response = walletController.handleStudentNotExistException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, Objects.requireNonNull(response.getBody()).message());
    }
}