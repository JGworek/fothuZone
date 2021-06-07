package zone.fothu.pets.model.profile;

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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class User implements Serializable {

	private static final long serialVersionUID = -4631968289745068642L;

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
	@Column(name = "email_address")
	private String emailAddress;
	@Column(name = "admin_status")
	private boolean adminStatus;
	@Column(name = "verified_status")
	private boolean verifiedStatus;

	@OneToMany(mappedBy = "owner")
	@JsonIgnoreProperties("owner")
	@OrderBy("id")
	private List<Pet> pets;
}