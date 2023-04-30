package zone.fothu.pets.model.adventure;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import zone.fothu.pets.model.profile.User;

@Component
@Scope("prototype")
@Entity
@Table(name = "challenge_requests", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class ChallengeRequest implements Serializable {

	private static final long serialVersionUID = 798867279587257409L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "accepted_status")
	private boolean acceptedStatus;
	@Column(name = "rejected_status")
	private boolean rejectedStatus;
	@ManyToOne
	@JoinColumn(name = "attacking_user_id")
	private User attackingUser;
	@ManyToOne
	@JoinColumn(name = "defending_user_id")
	private User defendingUser;
	@Column(name = "max_number_of_attacking_pets")
	private int maxNumberOfAttackingPets;
	@Column(name = "max_number_of_defending_pets")
	private int maxNumberOfDefendingPets;
	@ManyToOne
	@JoinColumn(name = "resulting_battle_id")
	private Battle resultingBattle;
	@Column(name = "created_on")
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime createdOn;
}