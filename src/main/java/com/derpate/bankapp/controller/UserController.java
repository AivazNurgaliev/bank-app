package com.derpate.bankapp.controller;

import com.derpate.bankapp.exception.PasswordDoNotMatchException;
import com.derpate.bankapp.exception.UserAlreadyExistException;
import com.derpate.bankapp.exception.UserNotFoundException;
import com.derpate.bankapp.model.dto.UserResponse;
import com.derpate.bankapp.model.dto.UserUpdatePasswordRequest;
import com.derpate.bankapp.model.dto.UserUpdateRequest;
import com.derpate.bankapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/user/me")
    public UserResponse getMe() {
        return userServiceImpl.getMe();
    }

    @DeleteMapping("/user/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMe() throws UserNotFoundException {
        userServiceImpl.deleteMe();
    }

    @PutMapping("/user/me")
    public void updateMe(@RequestBody UserUpdateRequest userUpdateRequest) throws UserAlreadyExistException {
        userServiceImpl.updateMe(userUpdateRequest);
    }

    @PatchMapping("/user/me")
    public void updatePassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest) throws PasswordDoNotMatchException {
        userServiceImpl.patchMyPassword(userUpdatePasswordRequest);
    }
}
