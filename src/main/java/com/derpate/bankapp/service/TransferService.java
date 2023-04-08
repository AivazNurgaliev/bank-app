package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.exception.InvalidOperationException;
import com.derpate.bankapp.exception.InvalidPinException;
import com.derpate.bankapp.exception.NotEnoughMoneyException;
import com.derpate.bankapp.model.dto.TransferCreateRequest;
import com.derpate.bankapp.model.entity.TransferEntity;

public interface TransferService {

    TransferEntity createTransfer(TransferCreateRequest transferCreateRequest) throws CardNotFoundException, NotEnoughMoneyException, InvalidPinException, InvalidOperationException;
}
