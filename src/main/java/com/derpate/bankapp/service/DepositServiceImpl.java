package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.exception.InvalidPinException;
import com.derpate.bankapp.model.dto.DepositCreateRequest;
import com.derpate.bankapp.model.entity.DepositEntity;
import com.derpate.bankapp.repository.DepositRepository;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;
    private final UserServiceImpl userService;
    private final CardServiceImpl cardService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DepositServiceImpl(DepositRepository depositRepository,
                              UserServiceImpl userService,
                              CardServiceImpl cardService,
                              PasswordEncoder passwordEncoder) {
        this.depositRepository = depositRepository;
        this.userService = userService;
        this.cardService = cardService;
        this.passwordEncoder = passwordEncoder;
    }

    // TODO: 07.04.2023 if card expires then what???
    @Override
    @Transactional
    public DepositEntity createDeposit(DepositCreateRequest depositCreateRequest) throws Exception {
        Timestamp timestamp = new Timestamp(new Date().getTime());

        if (depositCreateRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Amount of money must be above 0");
        }

        var card = cardService.getCardById(depositCreateRequest.getCardId());

        if (!passwordEncoder.matches(depositCreateRequest.getPin(), card.getPin())) {
            throw new InvalidPinException("Invalid PIN");
        }

        var deposit = DepositEntity.builder()
                .userId(userService.getMyId())
                .cardId(depositCreateRequest.getCardId())
                .amount(depositCreateRequest.getAmount())
                .createdAt(timestamp)
                .build();

        card.setBalance(card.getBalance().add(depositCreateRequest.getAmount()));

        return depositRepository.save(deposit);
    }
}
