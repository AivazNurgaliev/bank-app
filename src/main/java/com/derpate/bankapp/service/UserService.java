package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.PasswordDoNotMatchException;
import com.derpate.bankapp.exception.UserAlreadyExistException;
import com.derpate.bankapp.exception.UserNotFoundException;
import com.derpate.bankapp.model.dto.UserCreateRequest;
import com.derpate.bankapp.model.dto.UserResponse;
import com.derpate.bankapp.model.dto.UserUpdatePasswordRequest;
import com.derpate.bankapp.model.dto.UserUpdateRequest;
import com.derpate.bankapp.model.entity.CardEntity;
import com.derpate.bankapp.model.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity createMe(UserCreateRequest userCreateRequest);
    UserResponse getMe();
    void deleteMe() throws UserNotFoundException;
    void updateMe(UserUpdateRequest userUpdateRequest) throws UserAlreadyExistException;
    // TODO: 09.03.2023 patch password
    void patchMyPassword(UserUpdatePasswordRequest userUpdatePasswordRequest) throws PasswordDoNotMatchException;
    List<CardEntity> getUserCards();

}
