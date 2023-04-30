package zone.fothu.pets.model.profile;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Component
@Scope("prototype")
@Entity
@Table(name = "verification_tokens", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class VerificationToken implements Serializable {

	private static final long serialVersionUID = 7015622305482359566L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "verification_token")
	private String verificationToken;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

}
