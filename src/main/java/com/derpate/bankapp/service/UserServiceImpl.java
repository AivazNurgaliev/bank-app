package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.UserAlreadyExistException;
import com.derpate.bankapp.exception.UserNotFoundException;
import com.derpate.bankapp.model.dto.UserCreateRequest;
import com.derpate.bankapp.model.dto.UserResponse;
import com.derpate.bankapp.model.dto.UserUpdateRequest;
import com.derpate.bankapp.model.entity.UserEntity;
import com.derpate.bankapp.model.security.Role;
import com.derpate.bankapp.model.security.Status;
import com.derpate.bankapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Transactional
    public UserEntity updateLoginTime(UserEntity user) {
        user.setLastLogin(new Timestamp(new Date().getTime()));
        return user;
    }

    @Override
    public UserEntity createMe(UserCreateRequest userCreateRequest) {
        Timestamp timestamp = new Timestamp(new Date().getTime());

        UserEntity user = UserEntity.builder()
                .firstName(userCreateRequest.getFirstName())
                .secondName(userCreateRequest.getSecondName())
                .patronymicName(userCreateRequest.getPatronymicName())
                .phone(userCreateRequest.getPhone())
                .email(userCreateRequest.getEmail())
                .password(passwordService.encode(userCreateRequest.getPassword()))
                .role(Role.USER)
                .status(Status.ACTIVE)
                .createdAt(timestamp)
                .lastLogin(timestamp)
                .build();

        userRepository.save(user);

        return user;
    }

    @Override
    public UserResponse getMe() {
        UserEntity user = userRepository.findByEmail(getMyEmail());
        UserResponse userResponse = user.fromUserEntity(user);

        return userResponse;
    }

    @Override
    public void deleteMe() throws UserNotFoundException {
        var user = userRepository.findByEmail(getMyEmail());
        if (user == null) {
            throw new UserNotFoundException("user not found");
        }

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void updateMe(UserUpdateRequest userUpdateRequest) throws UserAlreadyExistException {
        UserEntity checkUserEmail = userRepository.findByEmail(userUpdateRequest.getEmail());
        UserEntity checkUserPhone = userRepository.findByPhone(userUpdateRequest.getPhone());

        if (checkUserEmail != null || checkUserPhone != null) {
            throw new UserAlreadyExistException("User with that phone or email exists");
        }

        UserEntity user = userRepository.findByEmail(getMyEmail());
        user.setEmail(userUpdateRequest.getEmail());
        user.setPhone(userUpdateRequest.getPhone());
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setSecondName(userUpdateRequest.getSecondName());
        user.setPatronymicName(userUpdateRequest.getPatronymicName());

        userRepository.save(user);
    }

    private String getMyEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
