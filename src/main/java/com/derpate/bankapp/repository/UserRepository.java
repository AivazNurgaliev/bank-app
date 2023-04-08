package com.derpate.bankapp.repository;

import com.derpate.bankapp.model.entity.CardEntity;
import com.derpate.bankapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
    UserEntity findByPhone(String phone);
    UserEntity findByUserId(Integer userId);
    List<CardEntity> findAllByUserId(Integer userId);

    boolean existsByUserId(Integer userId);
}
