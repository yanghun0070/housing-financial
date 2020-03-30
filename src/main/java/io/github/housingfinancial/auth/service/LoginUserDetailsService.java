package io.github.housingfinancial.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.housingfinancial.auth.domain.User;
import io.github.housingfinancial.auth.repository.UserJpaRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    private UserJpaRepository userJpaRepository;

    public LoginUserDetailsService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userJpaRepository.findByUserId(userId)
                                     .orElseThrow(IllegalArgumentException::new);
        return new LoginUserDetails(user);
    }
}
