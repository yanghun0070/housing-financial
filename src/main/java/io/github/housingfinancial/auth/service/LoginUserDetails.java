package io.github.housingfinancial.auth.service;

import org.springframework.security.core.authority.AuthorityUtils;

import io.github.housingfinancial.auth.domain.User;

public class LoginUserDetails extends org.springframework.security.core.userdetails.User {

    private final User user;

    public LoginUserDetails(User user) {
        super(user.getUserId(), user.getPassword().getPassword(),
              AuthorityUtils.createAuthorityList("ROLE_USER"));
        this.user = user;
    }

    public User getUser() {
        return user;
    }




}
