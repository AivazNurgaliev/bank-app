package com.derpate.bankapp.controller;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.exception.InvalidMoneyValueException;
import com.derpate.bankapp.exception.InvalidPinException;
import com.derpate.bankapp.exception.NotEnoughMoneyException;
import com.derpate.bankapp.model.dto.WithdrawCreateRequest;
import com.derpate.bankapp.model.entity.WithdrawEntity;
import com.derpate.bankapp.service.WithdrawServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WithdrawController {

    private final WithdrawServiceImpl withdrawService;

    @Autowired
    public WithdrawController(WithdrawServiceImpl withdrawService) {
        this.withdrawService = withdrawService;
    }

    @PostMapping("/withdraw")
    public WithdrawEntity createWithdraw(@RequestBody WithdrawCreateRequest withdrawCreateRequest) throws InvalidMoneyValueException,
            NotEnoughMoneyException,
            InvalidPinException,
            CardNotFoundException {
        return withdrawService.createWithdrawal(withdrawCreateRequest);
    }
}
