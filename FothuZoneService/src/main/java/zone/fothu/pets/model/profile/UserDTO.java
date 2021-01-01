package zone.fothu.pets.model.profile;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "users", schema = "pets")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 6951903949395867726L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "favorite_color")
    private String favoriteColor;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "secret_password")
    private String secretPassword;

    public UserDTO() {
        super();
    }

    public UserDTO(int id, String username, String favoriteColor, String userPassword, String secretPassword) {
        super();
        this.id = id;
        this.username = username;
        this.favoriteColor = favoriteColor;
        this.userPassword = userPassword;
        this.secretPassword = secretPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSecretPassword() {
        return secretPassword;
    }

    public void setSecretPassword(String secretPassword) {
        this.secretPassword = secretPassword;
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoriteColor, id, secretPassword, userPassword, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserDTO)) {
            return false;
        }
        UserDTO other = (UserDTO) obj;
        return Objects.equals(favoriteColor, other.favoriteColor) && id == other.id
            && Objects.equals(secretPassword, other.secretPassword) && Objects.equals(userPassword, other.userPassword)
            && Objects.equals(username, other.username);
    }

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", username=" + username + ", favoriteColor=" + favoriteColor + ", userPassword="
            + userPassword + ", secretPassword=" + secretPassword + "]";
    }
}
