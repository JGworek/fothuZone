package zone.fothu.pets.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Entity
@Table(name="pets", schema="pets")
public class Pet implements Serializable {
	
	private static final long serialVersionUID = 1459839716503621053L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="image")
	private String image;
	@Column(name="type")
	private String type;
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
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnoreProperties("pets")
	private User owner;
	
	public Pet() {
		super();
	}

	public Pet(int id, String name, String image, String type, int hunger, int currentHealth, int maxHealth,
			int strength, int dexterity, int intelligence, int petLevel, User owner) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.type = type;
		this.hunger = hunger;
		this.currentHealth = currentHealth;
		this.maxHealth = maxHealth;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.petLevel = petLevel;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", image=" + image + ", type=" + type + ", hunger=" + hunger
				+ ", currentHealth=" + currentHealth + ", maxHealth=" + maxHealth + ", strength=" + strength
				+ ", dexterity=" + dexterity + ", intelligence=" + intelligence + ", petLevel=" + petLevel + ", owner="
				+ owner + "]";
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
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + petLevel;
		result = prime * result + strength;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (petLevel != other.petLevel)
			return false;
		if (strength != other.strength)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}
