package zone.fothu.pets.model;

import java.io.Serializable;

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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((favoriteColor == null) ? 0 : favoriteColor.hashCode());
        result = prime * result + id;
        result = prime * result + ((secretPassword == null) ? 0 : secretPassword.hashCode());
        result = prime * result + ((userPassword == null) ? 0 : userPassword.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDTO other = (UserDTO) obj;
        if (favoriteColor == null) {
            if (other.favoriteColor != null)
                return false;
        } else if (!favoriteColor.equals(other.favoriteColor))
            return false;
        if (id != other.id)
            return false;
        if (secretPassword == null) {
            if (other.secretPassword != null)
                return false;
        } else if (!secretPassword.equals(other.secretPassword))
            return false;
        if (userPassword == null) {
            if (other.userPassword != null)
                return false;
        } else if (!userPassword.equals(other.userPassword))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", username=" + username + ", favoriteColor=" + favoriteColor + ", userPassword="
            + userPassword + ", secretPassword=" + secretPassword + "]";
    }
}
