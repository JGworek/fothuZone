package zone.fothu.pets.model.profile;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Component
@Scope("prototype")
@Entity
@Table(name = "users", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 6951903949395867726L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "username")
	private String username;
	@Column(name = "favorite_color")
	private String favoriteColor;
	@Column(name = "user_password")
	private String userPassword;
	@Column(name = "secret_password")
	private String secretPassword;
}