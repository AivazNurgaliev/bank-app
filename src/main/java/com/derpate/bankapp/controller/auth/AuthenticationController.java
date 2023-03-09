package com.derpate.bankapp.controller.auth;

import com.derpate.bankapp.exception.PasswordDoNotMatchException;
import com.derpate.bankapp.exception.UserAlreadyExistException;
import com.derpate.bankapp.exception.UserNotFoundException;
import com.derpate.bankapp.model.dto.UserCreateRequest;
import com.derpate.bankapp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserCreateRequest userCreateRequest
            ) throws PasswordDoNotMatchException, UserAlreadyExistException {
        return new ResponseEntity<>(authService.register(userCreateRequest), HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) throws UserNotFoundException {
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }
}
