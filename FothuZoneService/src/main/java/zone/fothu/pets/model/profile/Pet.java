package zone.fothu.pets.model.profile;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Entity
@Table(name = "pets", schema = "pets")
public class Pet implements Serializable {

	private static final long serialVersionUID = 1459839716503621053L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "name")
	private String name;

	@OneToOne
	@JoinColumn(name = "image_id")
	@JsonIgnoreProperties("pets")
	private Image image;

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

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties("pets")
	private User owner;

	public Pet() {
		super();
	}

	public Pet(int id, String name, Image image, String type, int hunger, int currentHealth, int maxHealth, int strength, int agility, int intelligence, int petLevel, int currentXP, int availableLevelUps, User owner) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
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
		this.owner = owner;
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agility, availableLevelUps, currentHealth, currentXP, hunger, id, image, intelligence, maxHealth, name, owner, petLevel, strength, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Pet)) {
			return false;
		}
		Pet other = (Pet) obj;
		return agility == other.agility && availableLevelUps == other.availableLevelUps && currentHealth == other.currentHealth && currentXP == other.currentXP && hunger == other.hunger && id == other.id && Objects.equals(image, other.image) && intelligence == other.intelligence && maxHealth == other.maxHealth && Objects.equals(name, other.name) && Objects.equals(owner, other.owner) && petLevel == other.petLevel && strength == other.strength && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", image=" + image + ", type=" + type + ", hunger=" + hunger + ", currentHealth=" + currentHealth + ", maxHealth=" + maxHealth + ", strength=" + strength + ", agility=" + agility + ", intelligence=" + intelligence + ", petLevel=" + petLevel + ", currentXP=" + currentXP + ", availableLevelUps=" + availableLevelUps + ", owner=" + owner + "]";
	}
}