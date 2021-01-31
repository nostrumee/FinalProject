package com.leverx.tradingview.service.auth;

import com.leverx.tradingview.model.CustomUserDetails;
import com.leverx.tradingview.model.jpa.User;
import com.leverx.tradingview.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Retrieving user by name {}", username);
        return this.userRepository.findByEmail(username)
                .map(this::mapToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }

    private CustomUserDetails mapToUserDetails(User user) {
        return CustomUserDetails.builder()
                .userRole(user.getRole())
                .password(user.getPassword())
                .username(user.getEmail())
                .build();
    }
}
