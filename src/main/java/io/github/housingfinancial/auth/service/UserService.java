package io.github.housingfinancial.auth.service;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.github.housingfinancial.auth.domain.User;
import io.github.housingfinancial.auth.repository.UserJpaRepository;

@Service
public class UserService {
    private UserJpaRepository userJpaRepository;

    public UserService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public Optional<User> findByUserId(String userId) {
        return userJpaRepository.findByUserId(userId);
    }

    public void signUp(User user) {
        userJpaRepository.save(user);
    }
}
