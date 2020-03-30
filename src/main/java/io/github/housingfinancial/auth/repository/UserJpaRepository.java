package io.github.housingfinancial.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.housingfinancial.auth.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUserId(String userId);
}
