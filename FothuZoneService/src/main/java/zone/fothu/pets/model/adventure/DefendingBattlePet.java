package zone.fothu.pets.model.adventure;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import zone.fothu.pets.model.profile.Pet;

@Component
@Scope("prototype")
@Entity
@Table(name = "defending_battle_pets", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class DefendingBattlePet implements Serializable {

	private static final long serialVersionUID = -306546001632317961L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@ManyToOne
	@JoinColumn(name = "battle_id")
	@JsonIgnoreProperties("defendingBattlePets")
	private Battle battle;
	@OneToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	@Column(name = "current_health")
	private int currentHealth;
	@Column(name = "alive_status")
	private boolean aliveStatus;
}
