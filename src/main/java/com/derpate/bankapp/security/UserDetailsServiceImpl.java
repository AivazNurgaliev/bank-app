package com.derpate.bankapp.security;

import com.derpate.bankapp.exception.UserNotFoundException;
import com.derpate.bankapp.model.entity.UsersEntity;
import com.derpate.bankapp.repository.UsersRepository;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UsersEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User does not exist in db");
        }

        return SecurityUser.fromUser(user);
    }
}
