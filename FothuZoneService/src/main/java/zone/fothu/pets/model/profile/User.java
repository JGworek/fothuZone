package zone.fothu.pets.model.profile;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
    @Column(name = "favorite_color")
    private String favoriteColor;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "secret_password")
    private String secretPassword;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    @OrderBy("id")
    private List<Pet> pets;

    public User() {
        super();
    }

	public User(int id, String username, String favoriteColor, String userPassword, String secretPassword,
			List<Pet> pets) {
		super();
		this.id = id;
		this.username = username;
		this.favoriteColor = favoriteColor;
		this.userPassword = userPassword;
		this.secretPassword = secretPassword;
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

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	@Override
	public int hashCode() {
		return Objects.hash(favoriteColor, id, pets, secretPassword, userPassword, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(favoriteColor, other.favoriteColor) && id == other.id && Objects.equals(pets, other.pets)
				&& Objects.equals(secretPassword, other.secretPassword)
				&& Objects.equals(userPassword, other.userPassword) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", favoriteColor=" + favoriteColor + ", userPassword="
				+ userPassword + ", secretPassword=" + secretPassword + ", pets=" + pets + "]";
	}
}