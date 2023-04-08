package com.derpate.bankapp.repository;

import com.derpate.bankapp.model.entity.CardEntity;
import com.derpate.bankapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
    CardEntity findByCardId(Long cardId);
    List<CardEntity> findAllByUserId(Integer userId);
    boolean existsByCardId(Integer cardId);
    boolean existsByUserIdAndCardId(Integer userId, Long cardId);
}
