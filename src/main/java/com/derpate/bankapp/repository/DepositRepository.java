package com.derpate.bankapp.repository;

import com.derpate.bankapp.model.entity.DepositEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<DepositEntity, Long> {

}
