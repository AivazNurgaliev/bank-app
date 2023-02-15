package com.derpate.bankapp.repository;

import com.derpate.bankapp.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {
    UsersEntity findByEmail(String email);
}
