package zone.fothu.pets.model.adventure;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
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
@Table(name = "turns", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class Turn implements Serializable {

	private static final long serialVersionUID = -5134212715227063402L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@ManyToOne
	@JoinColumn(name = "battle_id")
	@JsonIgnoreProperties("turns")
	private Battle battle;
	@Column(name = "turn_number")
	private int turnNumber;
	@ManyToOne
	@JoinColumn(name = "attacking_pet_id")
	private Pet attackingPet;
	@ManyToOne
	@JoinColumn(name = "defending_pet_id")
	private Pet defendingPet;
	@Column(name = "attacking_pet_current_health")
	private int attackingPetCurrentHealth;
	@Column(name = "defending_pet_current_health")
	private int defendingPetCurrentHealth;
	@Column(name = "attacking_pet_attack_modifier")
	private double attackingPetAttackModifier;
	@Column(name = "defending_pet_attack_modifier")
	private double defendingPetAttackModifier;
	@Column(name = "attacking_pet_armor_modifier")
	private double attackingPetArmorModifier;
	@Column(name = "defending_pet_armor_modifier")
	private double defendingPetArmorModifier;
	@Column(name = "attacking_pet_accuracy_modifier")
	private double attackingPetAccuracyModifier;
	@Column(name = "defending_pet_accuracy_modifier")
	private double defendingPetAccuracyModifier;
	@Column(name = "attacking_pet_evasion_modifier")
	private double attackingPetEvasionModifier;
	@Column(name = "defending_pet_evasion_modifier")
	private double defendingPetEvasionModifier;
	@Column(name = "turn_flavor_text")
	private String turnFlavorText;
	@Column(name = "turn_technical_text")
	private String turnTechnicalText;
	@Column(name = "attacker_replaced_dead_pet")
	private boolean attackerReplacedDeadPet;
	@Column(name = "defender_replaced_dead_pet")
	private boolean defenderReplacedDeadPet;
	@Column(name = "battle_finished")
	private boolean battleFinished;
	@Column(name = "created_on")
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime createdOn;
}