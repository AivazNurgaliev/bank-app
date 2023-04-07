package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.model.dto.CardCreateRequest;
import com.derpate.bankapp.model.entity.CardEntity;

import java.util.List;

public interface CardService {

    CardEntity createCard(CardCreateRequest cardCreateRequest);
    void deleteCard(Long cardId) throws CardNotFoundException;
    CardEntity getCardById(Long cardId) throws CardNotFoundException;
    List<CardEntity> getAllCards() throws CardNotFoundException;

}
