package io.github.housingfinancial.auth.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Password {
    private String password;

    public Password(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
