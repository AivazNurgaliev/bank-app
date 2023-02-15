package com.derpate.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    public String encode (String password) {
        String resultPassword = passwordEncoder.encode(password);
        while (passwordEncoder.upgradeEncoding(resultPassword)) {
            passwordEncoder.encode(resultPassword);
        }

        return resultPassword;
    }

    public boolean validatePassword (String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public boolean validateRepeatPassword(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }
}
