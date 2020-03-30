package io.github.housingfinancial.auth.presentation.vo;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthenticationRequest {
    @NotEmpty
    private String userId; //유저 id
    @NotEmpty
    private String password; //비밀번호

    public AuthenticationRequest(@NotEmpty String userId,
                                 @NotEmpty String password) {
        this.userId = userId;
        this.password = password;
    }
}
