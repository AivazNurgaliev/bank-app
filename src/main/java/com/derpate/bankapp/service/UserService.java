package com.derpate.bankapp.service;

import com.derpate.bankapp.model.entity.UserEntity;
import com.derpate.bankapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserEntity updateLoginTime(UserEntity user) {
        user.setLastLogin(new Timestamp(new Date().getTime()));
        return user;
    }
}
