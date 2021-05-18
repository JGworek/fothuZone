package zone.fothu.pets.model.adventure;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import zone.fothu.pets.model.profile.User;

@Component
@Scope("prototype")
@Entity
@Table(name = "battles", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class Battle implements Serializable {

	private static final long serialVersionUID = -22349369384602738L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "battle_type")
	private String battleType;
	@Column(name = "max_number_of_attacking_pets")
	private int maxNumberOfAttackingPets;
	@Column(name = "max_number_of_defending_pets")
	private int maxNumberOfDefendingPets;
	@ManyToOne
	@JoinColumn(name = "attacking_user_id")
	private User attackingUser;
	@OneToMany(mappedBy = "battle")
	@OrderBy("id ASC")
	@JsonIgnoreProperties("battle")
	private List<AttackingBattlePet> attackingBattlePets;
	@ManyToOne
	@JoinColumn(name = "defending_user_id")
	private User defendingUser;
	@OneToMany(mappedBy = "battle")
	@OrderBy("id ASC")
	@JsonIgnoreProperties("battle")
	private List<DefendingBattlePet> defendingBattlePets;
	@ManyToOne
	@JoinColumn(name = "next_turn_user_id")
	private User nextTurnUser;
	@Column(name = "battle_finished")
	private boolean battleFinished;
	@ManyToOne
	@JoinColumn(name = "winning_user_id")
	private User winningUser;
	@ManyToOne
	@JoinColumn(name = "losing_user_id")
	private User losingUser;
	@Column(name = "created_on")
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime createdOn;
	@OneToMany(mappedBy = "battle")
	@JsonIgnoreProperties("battle")
	@OrderBy("id ASC")
	private List<Turn> turns;
}