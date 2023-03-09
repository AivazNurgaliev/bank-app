package com.derpate.bankapp.service;

import com.derpate.bankapp.controller.auth.AuthenticationRequest;
import com.derpate.bankapp.controller.auth.AuthenticationResponse;
import com.derpate.bankapp.exception.PasswordDoNotMatchException;
import com.derpate.bankapp.exception.UserAlreadyExistException;
import com.derpate.bankapp.exception.UserNotFoundException;
import com.derpate.bankapp.model.entity.UserEntity;
import com.derpate.bankapp.model.dto.UserCreateRequest;
import com.derpate.bankapp.model.security.Role;
import com.derpate.bankapp.model.security.Status;
import com.derpate.bankapp.repository.UserRepository;
import com.derpate.bankapp.security.JwtService;
import com.derpate.bankapp.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 UserServiceImpl userServiceImpl) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userServiceImpl = userServiceImpl;
    }

    public AuthenticationResponse register(UserCreateRequest userCreateRequest) throws PasswordDoNotMatchException, UserAlreadyExistException {
        if (!userCreateRequest.getPassword().equals(userCreateRequest.getRepeatPassword())) {
            throw new PasswordDoNotMatchException("Your passwords do not match");

        }

        UserEntity checkUserEmail = userRepository.findByEmail(userCreateRequest.getEmail());
        UserEntity checkUserPhone = userRepository.findByPhone(userCreateRequest.getPhone());

        if (checkUserEmail != null || checkUserPhone != null) {
            throw new UserAlreadyExistException("User with that phone or email exists");
        }


        UserEntity user = userServiceImpl.createMe(userCreateRequest);

        UserDetails userDetails = SecurityUser.fromUser(user);
        String jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    // TODO: 18.02.2023 optional maybe???
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws UserNotFoundException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        UserEntity user = userRepository.findByEmail(authenticationRequest.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User does not exist in db");
        }

        UserDetails userDetails = SecurityUser.fromUser(user);
        String jwtToken = jwtService.generateToken(userDetails);

        userServiceImpl.updateLoginTime(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
