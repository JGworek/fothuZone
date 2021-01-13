package zone.fothu.pets.model.profile;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "pets", schema = "pets")
public class PetDTO implements Serializable {

	private static final long serialVersionUID = 153863922064492018L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "image_id")
	private int imageId;
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
	private int ownerId;

	public PetDTO() {
		super();
	}

	public PetDTO(int id, String name, int imageId, String type, int hunger, int currentHealth, int maxHealth, int strength, int agility, int intelligence, int petLevel, int currentXP, int availableLevelUps, int ownerId) {
		super();
		this.id = id;
		this.name = name;
		this.imageId = imageId;
		this.type = type;
		this.hunger = hunger;
		this.currentHealth = currentHealth;
		this.maxHealth = maxHealth;
		this.strength = strength;
		this.agility = agility;
		this.intelligence = intelligence;
		this.petLevel = petLevel;
		this.currentXP = currentXP;
		this.availableLevelUps = availableLevelUps;
		this.ownerId = ownerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getPetLevel() {
		return petLevel;
	}

	public void setPetLevel(int petLevel) {
		this.petLevel = petLevel;
	}

	public int getCurrentXP() {
		return currentXP;
	}

	public void setCurrentXP(int currentXP) {
		this.currentXP = currentXP;
	}

	public int getAvailableLevelUps() {
		return availableLevelUps;
	}

	public void setAvailableLevelUps(int availableLevelUps) {
		this.availableLevelUps = availableLevelUps;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agility, availableLevelUps, currentHealth, currentXP, hunger, id, imageId, intelligence, maxHealth, name, ownerId, petLevel, strength, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PetDTO)) {
			return false;
		}
		PetDTO other = (PetDTO) obj;
		return agility == other.agility && availableLevelUps == other.availableLevelUps && currentHealth == other.currentHealth && currentXP == other.currentXP && hunger == other.hunger && id == other.id && imageId == other.imageId && intelligence == other.intelligence && maxHealth == other.maxHealth && Objects.equals(name, other.name) && ownerId == other.ownerId && petLevel == other.petLevel && strength == other.strength && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "PetDTO [id=" + id + ", name=" + name + ", imageId=" + imageId + ", type=" + type + ", hunger=" + hunger + ", currentHealth=" + currentHealth + ", maxHealth=" + maxHealth + ", strength=" + strength + ", agility=" + agility + ", intelligence=" + intelligence + ", petLevel=" + petLevel + ", currentXP=" + currentXP + ", availableLevelUps=" + availableLevelUps + ", ownerId=" + ownerId + "]";
	}
}