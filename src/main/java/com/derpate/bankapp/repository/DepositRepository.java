package com.derpate.bankapp.repository;

import com.derpate.bankapp.model.entity.DepositEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<DepositEntity, Long> {

}
