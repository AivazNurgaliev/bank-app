package com.derpate.bankapp.controller;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.exception.InvalidOperationException;
import com.derpate.bankapp.exception.InvalidPinException;
import com.derpate.bankapp.exception.NotEnoughMoneyException;
import com.derpate.bankapp.model.dto.TransferCreateRequest;
import com.derpate.bankapp.model.entity.TransferEntity;
import com.derpate.bankapp.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public TransferEntity createTransfer(@RequestBody TransferCreateRequest transferCreateRequest) throws NotEnoughMoneyException,
            InvalidOperationException, InvalidPinException, CardNotFoundException {
        return transferService.createTransfer(transferCreateRequest);
    }
}
