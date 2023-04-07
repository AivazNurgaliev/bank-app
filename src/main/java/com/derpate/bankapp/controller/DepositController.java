package com.derpate.bankapp.controller;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.exception.InvalidPinException;
import com.derpate.bankapp.model.dto.DepositCreateRequest;
import com.derpate.bankapp.model.entity.DepositEntity;
import com.derpate.bankapp.service.DepositServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class DepositController {

    private final DepositServiceImpl depositService;

    @Autowired
    public DepositController(DepositServiceImpl depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/deposit")
    public DepositEntity createDeposit(@RequestBody DepositCreateRequest depositCreateRequest) throws Exception {
        return depositService.createDeposit(depositCreateRequest);
    }
}
