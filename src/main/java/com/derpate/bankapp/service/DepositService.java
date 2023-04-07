package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.exception.InvalidPinException;
import com.derpate.bankapp.model.dto.DepositCreateRequest;
import com.derpate.bankapp.model.entity.DepositEntity;

public interface DepositService {

    DepositEntity createDeposit(DepositCreateRequest depositCreateRequest) throws Exception;
}
