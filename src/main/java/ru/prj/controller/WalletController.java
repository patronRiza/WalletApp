package ru.prj.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prj.exceptions.EmptyWalletException;
import ru.prj.exceptions.InvalidOperationException;
import ru.prj.exceptions.WalletCreationException;
import ru.prj.model.dto.response.ErrorModel;
import ru.prj.model.dto.request.OperationRequest;
import ru.prj.model.dto.response.BalanceResponseDTO;
import ru.prj.model.dto.response.ListOfWalletsResponseDTO;
import ru.prj.model.dto.response.ResponseOfOperation;
import ru.prj.service.WalletService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping(value = "/{WALLET_UUID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BalanceResponseDTO getBalanceOfWallet(@PathVariable("WALLET_UUID") UUID id) {
        return new BalanceResponseDTO(walletService.getBalance(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ListOfWalletsResponseDTO getAllWallet() {
        return new ListOfWalletsResponseDTO(walletService.getWallets());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseOfOperation changeBalance(@RequestBody OperationRequest operationRequest) {
        return new ResponseOfOperation(walletService.operate(operationRequest));
    }

    @ExceptionHandler(EmptyWalletException.class)
    public ResponseEntity<ErrorModel> handleStudentNotExistException(final EmptyWalletException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorModel(e.getMessage()));
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorModel> handleStudentNotExistException(final InvalidOperationException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorModel(e.getMessage()));
    }

    @ExceptionHandler(WalletCreationException.class)
    public ResponseEntity<ErrorModel> handleStudentNotExistException(final WalletCreationException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorModel(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> handleStudentNotExistException(final Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorModel(e.getMessage()));
    }
}
