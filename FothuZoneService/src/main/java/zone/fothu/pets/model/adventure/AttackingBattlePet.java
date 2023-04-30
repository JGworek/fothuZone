package zone.fothu.pets.model.adventure;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import zone.fothu.pets.model.profile.Pet;

@Component
@Scope("prototype")
@Entity
@Table(name = "attacking_battle_pets", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class AttackingBattlePet implements Serializable {

	private static final long serialVersionUID = 6531933150017871599L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@ManyToOne
	@JoinColumn(name = "battle_id")
	@JsonIgnoreProperties("attackingBattlePets")
	private Battle battle;
	@OneToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	@Column(name = "current_health")
	private int currentHealth;
	@Column(name = "alive_status")
	private boolean aliveStatus;

}
