package com.derpate.bankapp.repository;

import com.derpate.bankapp.model.entity.DepositEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<DepositEntity, Long> {
    List<DepositEntity> findAllByUserIdAndCardId(Integer userId, Long cardId);
}
