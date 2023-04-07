package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.model.dto.CardCreateRequest;
import com.derpate.bankapp.model.entity.CardEntity;
import com.derpate.bankapp.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CardServiceImpl implements CardService{

    private final CardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, PasswordEncoder passwordEncoder, UserServiceImpl userServiceImpl) {
        this.cardRepository = cardRepository;
        this.passwordEncoder = passwordEncoder;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public CardEntity createCard(CardCreateRequest cardCreateRequest) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.YEAR, 4);
        var userCard = CardEntity.builder()
                .userId(userServiceImpl.getMyId())
                .vendorId(cardCreateRequest.getVendorId())
                .cvv(generateCVV().toString())
                .pin(passwordEncoder.encode(cardCreateRequest.getPin()))
                .balance(new BigDecimal(0))
                .createdAt(timestamp)
                .expireAt(new Timestamp(cal.getTime().getTime()))
                .build();


        return cardRepository.save(userCard);
    }

    @Override
    public void deleteCard(Long cardId) throws CardNotFoundException {
        var card = cardRepository.findByCardId(cardId);
        if (card == null ) {
            throw new CardNotFoundException("Card with that ID not found");
        }

        cardRepository.delete(card);
    }

    @Override
    public CardEntity getCardById(Long cardId) throws CardNotFoundException {
        var card = cardRepository.findByCardId(cardId);
        if (card == null || card.getUserId() != userServiceImpl.getMyId() ) {
            throw new CardNotFoundException("There isn't a card");
        }

        return card;
    }

    @Override
    public List<CardEntity> getAllCards() throws CardNotFoundException {
        var cards = cardRepository.findAllByUserId(userServiceImpl.getMyId());

        if (cards == null) {
            throw new CardNotFoundException("There are no cards");
        }

        return cards;
    }

    private StringBuilder generateCVV() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 999 + 1);
        StringBuilder res = new StringBuilder(String.valueOf(randomNum));
        if (res.length() != 3) {
            String zeroes = "0".repeat(3 - res.length());
            res = res.append(zeroes);
            return res.reverse();
        }

        return res;
    }
}
