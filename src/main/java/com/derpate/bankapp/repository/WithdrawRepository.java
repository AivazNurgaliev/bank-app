package com.derpate.bankapp.repository;

import com.derpate.bankapp.model.entity.DepositEntity;
import com.derpate.bankapp.model.entity.WithdrawEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawRepository extends JpaRepository<WithdrawEntity, Long> {
    List<WithdrawEntity> findAllByUserIdAndCardId(Integer userId, Long cardId);
}
