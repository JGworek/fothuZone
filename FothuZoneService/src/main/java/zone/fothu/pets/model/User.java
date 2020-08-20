package zone.fothu.pets.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Entity
@Table(name = "users", schema = "pets")
public class User implements Serializable {

    private static final long serialVersionUID = -4631968289745068642L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "favorite_color")
    private String favoriteColor;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    @OrderBy("id")
    private List<Pet> pets;

    public User() {
        super();
    }

    public User(int id, String username, String userPassword, String favoriteColor, List<Pet> pets) {
        super();
        this.id = id;
        this.username = username;
        this.userPassword = userPassword;
        this.favoriteColor = favoriteColor;
        this.pets = pets;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((favoriteColor == null) ? 0 : favoriteColor.hashCode());
        result = prime * result + id;
        result = prime * result + ((pets == null) ? 0 : pets.hashCode());
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
        User other = (User) obj;
        if (favoriteColor == null) {
            if (other.favoriteColor != null)
                return false;
        } else if (!favoriteColor.equals(other.favoriteColor))
            return false;
        if (id != other.id)
            return false;
        if (pets == null) {
            if (other.pets != null)
                return false;
        } else if (!pets.equals(other.pets))
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
        return "User [id=" + id + ", username=" + username + ", userPassword=" + userPassword + ", favoriteColor="
            + favoriteColor + ", pets=" + pets + "]";
    }

}