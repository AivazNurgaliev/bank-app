package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.exception.InvalidMoneyValueException;
import com.derpate.bankapp.exception.InvalidPinException;
import com.derpate.bankapp.exception.NotEnoughMoneyException;
import com.derpate.bankapp.model.dto.WithdrawCreateRequest;
import com.derpate.bankapp.model.entity.WithdrawEntity;
import com.derpate.bankapp.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class WithdrawServiceImpl implements WithdrawService {

    private final WithdrawRepository withdrawRepository;
    private final UserServiceImpl userService;
    private final CardServiceImpl cardService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WithdrawServiceImpl(WithdrawRepository withdrawRepository,
                               UserServiceImpl userService,
                               CardServiceImpl cardService,
                               PasswordEncoder passwordEncoder) {
        this.withdrawRepository = withdrawRepository;
        this.userService = userService;
        this.cardService = cardService;
        this.passwordEncoder = passwordEncoder;
    }

    //// TODO: 08.04.2023 check if money available
    @Override
    @Transactional
    public WithdrawEntity createWithdrawal(WithdrawCreateRequest withdrawCreateRequest) throws InvalidMoneyValueException,
            InvalidPinException,
            CardNotFoundException,
            NotEnoughMoneyException {
        Timestamp timestamp = new Timestamp(new Date().getTime());

        if (withdrawCreateRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidMoneyValueException("Amount of money must be above 0");
        }

        var card = cardService.getCardById(withdrawCreateRequest.getCardId());


        if (card.getBalance().compareTo(withdrawCreateRequest.getAmount()) < 0) {
            throw new NotEnoughMoneyException("You don't have the money to do this operation");
        }

        if (!passwordEncoder.matches(withdrawCreateRequest.getPin(), card.getPin())) {
            throw new InvalidPinException("Invalid PIN");
        }

        var withdrawal = WithdrawEntity.builder()
                .userId(userService.getMyId())
                .cardId(withdrawCreateRequest.getCardId())
                .amount(withdrawCreateRequest.getAmount())
                .createdAt(timestamp)
                .build();

        card.setBalance(card.getBalance().subtract(withdrawCreateRequest.getAmount()));

        return withdrawRepository.save(withdrawal);
    }
}
