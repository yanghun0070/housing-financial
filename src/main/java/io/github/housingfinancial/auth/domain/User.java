package io.github.housingfinancial.auth.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @Column(name = "user_id")
    private String userId; //유저 ID
    @Embedded
    private Password password; //비밀 번호
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST) //연관되어 매핑된 UserInfo 정의된 객체
    private List<UserAuthorization> userAuthorizations = new ArrayList<>();

    public User(String userId, String password) {
        this.userId = userId;
        this.password = new Password(password);
    }

    public String getUserId() {
        return userId;
    }

    public void addUserAuthorization(UserAuthorization userAuthorization) {
        //무한 루프에 빠지는 거 방지
        if(userAuthorization == null) {
            userAuthorizations = new ArrayList<>();
        }
        userAuthorizations.add(userAuthorization);
    }

    public List<UserAuthorization> getUserAuthorizations() {
        return userAuthorizations;
    }

}
