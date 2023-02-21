package com.derpate.bankapp.service;

import com.derpate.bankapp.controller.auth.AuthenticationRequest;
import com.derpate.bankapp.controller.auth.AuthenticationResponse;
import com.derpate.bankapp.exception.PasswordDoNotMatchException;
import com.derpate.bankapp.exception.UserAlreadyExistsException;
import com.derpate.bankapp.exception.UserNotFoundException;
import com.derpate.bankapp.model.entity.UserEntity;
import com.derpate.bankapp.model.entity.dto.UserDTO;
import com.derpate.bankapp.model.security.Role;
import com.derpate.bankapp.model.security.Status;
import com.derpate.bankapp.repository.UserRepository;
import com.derpate.bankapp.security.JwtService;
import com.derpate.bankapp.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 PasswordService passwordService,
                                 JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // TODO: 18.02.2023 email and phone check 
    public AuthenticationResponse register(UserDTO userDTO) throws PasswordDoNotMatchException, UserAlreadyExistsException {
        if (!userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
            throw new PasswordDoNotMatchException("Your passwords do not match");

        }

        UserEntity checkUserEmail = userRepository.findByEmail(userDTO.getEmail());
        UserEntity checkUserPhone = userRepository.findByPhone(userDTO.getPhone());

        if (checkUserEmail != null || checkUserPhone != null) {
            throw new UserAlreadyExistsException("User with that phone or email exists");
        }

        Timestamp timestamp = new Timestamp(new Date().getTime());

        UserEntity user = UserEntity.builder()
                .firstName(userDTO.getFirstName())
                .secondName(userDTO.getSecondName())
                .patronymicName(userDTO.getPatronymicName())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .password(passwordService.encode(userDTO.getPassword()))
                .role(Role.USER)
                .status(Status.ACTIVE)
                .createdAt(timestamp)
                .lastLogin(timestamp)
                .build();



        userRepository.save(user);
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
        //todo created last login;
        UserEntity user = userRepository.findByEmail(authenticationRequest.getEmail());

        if (user == null) {
            throw new UserNotFoundException("User does not exist in db");
        }

        UserDetails userDetails = SecurityUser.fromUser(user);
        String jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
