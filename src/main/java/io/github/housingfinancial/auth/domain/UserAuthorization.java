package io.github.housingfinancial.auth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserAuthorization {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //유저 정보
    private String roleName; //권한 명

    private UserAuthorization() {}

    public UserAuthorization(User user, String roleName) {
        this.user = user;
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setUser(User user) {
        this.user = user;
        //무한 루프에 빠지는 거 방지
        if(this.user.getUserAuthorizations().contains(this)) {
            this.user.getUserAuthorizations().add(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserAuthorization{" +
               "id=" + id +
               ", user=" + user +
               ", roleName='" + roleName + '\'' +
               '}';
    }
}
