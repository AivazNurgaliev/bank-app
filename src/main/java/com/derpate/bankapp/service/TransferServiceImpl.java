package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.exception.InvalidOperationException;
import com.derpate.bankapp.exception.InvalidPinException;
import com.derpate.bankapp.exception.NotEnoughMoneyException;
import com.derpate.bankapp.model.dto.TransferCreateRequest;
import com.derpate.bankapp.model.entity.TransferEntity;
import com.derpate.bankapp.repository.CardRepository;
import com.derpate.bankapp.repository.TransferRepository;
import com.derpate.bankapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class TransferServiceImpl implements TransferService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final TransferRepository transferRepository;
    @Autowired
    public TransferServiceImpl(CardRepository cardRepository, UserRepository userRepository, UserServiceImpl userService, PasswordEncoder passwordEncoder, TransferRepository transferRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.transferRepository = transferRepository;
    }

    @Override
    @Transactional
    public TransferEntity createTransfer(TransferCreateRequest t) throws CardNotFoundException, NotEnoughMoneyException, InvalidPinException, InvalidOperationException {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        /*System.out.println(cardRepository.existsByUserIdAndCardId(1, 3l));
        System.out.println(cardRepository.existsByUserIdAndCardId(2, 3l));
        System.out.println(cardRepository.existsByUserIdAndCardId(289, 31l));*/
        // TODO: 08.04.2023 не отправлять сендер так как мы и так знаем кто мы

        if (!cardRepository.existsByUserIdAndCardId(userService.getMyId(), t.getSenderCardId())
        || !cardRepository.existsByUserIdAndCardId(t.getReceiverId(), t.getReceiverCardId())) {
            throw new CardNotFoundException("Sender or Receiver card not found. Check senderId and ReceiverId too.");
        }

        var senderCard = cardRepository.findByCardId(t.getSenderCardId());
        var receiverCard = cardRepository.findByCardId(t.getReceiverCardId());

        if (senderCard.getBalance().compareTo(t.getAmount()) < 0) {
            throw new NotEnoughMoneyException("You don't have the money to do this operation");
        }

        if (!passwordEncoder.matches(t.getPin(), senderCard.getPin())) {
            throw new InvalidPinException("Invalid PIN");
        }

        var transfer = TransferEntity.builder()
                .senderId(userService.getMyId())
                .receiverId(t.getReceiverId())
                .senderCardId(t.getSenderCardId())
                .receiverCardId(t.getReceiverCardId())
                .createdAt(timestamp)
                .amount(t.getAmount())
                .build();

        if (userService.getMyId() == t.getReceiverId() && t.getSenderCardId() == t.getReceiverCardId()) {
            throw new InvalidOperationException("You cannot transfer money from your card to itself");
        }

        senderCard.setBalance(senderCard.getBalance().subtract(t.getAmount()));
        receiverCard.setBalance(receiverCard.getBalance().add(t.getAmount()));

        return transferRepository.save(transfer);
    }

}
