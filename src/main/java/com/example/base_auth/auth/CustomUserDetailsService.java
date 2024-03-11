package com.example.base_auth.auth;

import com.example.base_auth.model.UserDbEntity;
import com.example.base_auth.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDbEntity userDbEntity = userRepository.findByUsername(username);
        return CustomUserDetails.fromUser(userDbEntity.toUserDTO());
    }
}
