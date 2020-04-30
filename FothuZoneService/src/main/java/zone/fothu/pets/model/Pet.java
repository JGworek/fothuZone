package zone.fothu.pets.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="pets")
public class Pet implements Serializable {
	
	private static final long serialVersionUID = 1459839716503621053L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="image")
	private String image;
	@Column(name="hunger")
	private int hunger;
	@Column(name="current_health")
	private int currentHealth;
	@Column(name="max_health")
	private int maxHealth;
	@Column(name="strength")
	private int strength;
	@Column(name="dexterity")
	private int dexterity;
	@Column(name="intelligence")
	private int intelligence;
	@Column(name="pet_level")
	private int petLevel;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=true)
	private User petsUser;
	
	public Pet() {
		super();
	}
	
	public Pet(int id, String name, String image, int hunger, int currentHealth, int maxHealth, int strength,
			int dexterity, int intelligence, int petLevel, User petsUser) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.hunger = hunger;
		this.currentHealth = currentHealth;
		this.maxHealth = maxHealth;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.petLevel = petLevel;
		this.petsUser = petsUser;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
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

	public User getUserid() {
		return petsUser;
	}

	public void setUserid(User petsUser) {
		this.petsUser = petsUser;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", image=" + image + ", hunger=" + hunger + ", currentHealth="
				+ currentHealth + ", maxHealth=" + maxHealth + ", strength=" + strength + ", dexterity=" + dexterity
				+ ", intelligence=" + intelligence + ", petLevel=" + petLevel + ", petsUser=" + petsUser + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentHealth;
		result = prime * result + dexterity;
		result = prime * result + hunger;
		result = prime * result + id;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + intelligence;
		result = prime * result + maxHealth;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + petLevel;
		result = prime * result + ((petsUser == null) ? 0 : petsUser.hashCode());
		result = prime * result + strength;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		if (currentHealth != other.currentHealth)
			return false;
		if (dexterity != other.dexterity)
			return false;
		if (hunger != other.hunger)
			return false;
		if (id != other.id)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (intelligence != other.intelligence)
			return false;
		if (maxHealth != other.maxHealth)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (petLevel != other.petLevel)
			return false;
		if (petsUser == null) {
			if (other.petsUser != null)
				return false;
		} else if (!petsUser.equals(other.petsUser))
			return false;
		if (strength != other.strength)
			return false;
		return true;
	}

	
	
}
