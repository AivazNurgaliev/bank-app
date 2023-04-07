package com.derpate.bankapp.controller;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.model.dto.CardCreateRequest;
import com.derpate.bankapp.model.entity.CardEntity;
import com.derpate.bankapp.service.CardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {

    private final CardServiceImpl cardServiceImpl;

    @Autowired
    public CardController(CardServiceImpl cardServiceImpl) {
        this.cardServiceImpl = cardServiceImpl;
    }

    @GetMapping("/cards")
    public List<CardEntity> getAllCards() throws CardNotFoundException {
        return cardServiceImpl.getAllCards();
    }

    @GetMapping("/card/{cardID}")
    public CardEntity getCard(@PathVariable(name = "cardID") Long cardId) throws CardNotFoundException {
        return cardServiceImpl.getCardById(cardId);
    }

    @PostMapping("/card")
    public CardEntity createCard(@RequestBody CardCreateRequest cardCreateRequest) {
        return cardServiceImpl.createCard(cardCreateRequest);
    }

    @DeleteMapping("/card/{cardID}")
    public void deleteCard(@PathVariable(name = "cardID") Long cardId) throws CardNotFoundException {
        cardServiceImpl.deleteCard(cardId);
    }
}
