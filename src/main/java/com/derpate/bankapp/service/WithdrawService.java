package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.exception.InvalidMoneyValueException;
import com.derpate.bankapp.exception.InvalidPinException;
import com.derpate.bankapp.exception.NotEnoughMoneyException;
import com.derpate.bankapp.model.dto.WithdrawCreateRequest;
import com.derpate.bankapp.model.entity.WithdrawEntity;

public interface WithdrawService {

    WithdrawEntity createWithdrawal(WithdrawCreateRequest withdrawCreateRequest) throws InvalidMoneyValueException,
            InvalidPinException, CardNotFoundException, NotEnoughMoneyException;

}
