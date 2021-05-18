package zone.fothu.pets.model.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Component
@Scope("prototype")
@Entity
@Table(name = "pets", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class PetDTO implements Serializable {

	private static final long serialVersionUID = 153863922064492018L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "name")
	private String name;
	@Column(name = "image_id")
	private long imageId;
	@Column(name = "stat_type")
	private String type;
	@Column(name = "hunger")
	private int hunger;
	@Column(name = "current_health")
	private int currentHealth;
	@Column(name = "max_health")
	private int maxHealth;
	@Column(name = "strength")
	private int strength;
	@Column(name = "agility")
	private int agility;
	@Column(name = "intelligence")
	private int intelligence;
	@Column(name = "pet_level")
	private int petLevel;
	@Column(name = "current_xp")
	private int currentXP;
	@Column(name = "available_level_ups")
	private int availableLevelUps;
	@Column(name = "owner_id")
	private long ownerId;
}