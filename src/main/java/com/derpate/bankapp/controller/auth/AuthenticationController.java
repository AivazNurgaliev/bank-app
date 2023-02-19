package com.derpate.bankapp.controller.auth;

import com.derpate.bankapp.exception.PasswordDoNotMatchException;
import com.derpate.bankapp.exception.UserAlreadyExistsException;
import com.derpate.bankapp.model.entity.dto.UserDTO;
import com.derpate.bankapp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    // TODO: 18.02.2023 if email or phone is present then throw an error(process it) 
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserDTO userDTO
            ) throws PasswordDoNotMatchException, UserAlreadyExistsException {
        return ResponseEntity.ok(authService.register(userDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }
}
