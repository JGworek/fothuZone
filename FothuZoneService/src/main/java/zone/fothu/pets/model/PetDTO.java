package zone.fothu.pets.model;

import java.io.Serializable;

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
    @Column(name = "owner_id")
    private int ownerId;
    
    public PetDTO() {
        super();
    }

    public PetDTO(int id, String name, int imageId, String type, int hunger, int currentHealth, int maxHealth,
        int strength, int agility, int intelligence, int petLevel, int currentXP, int ownerId) {
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

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + agility;
        result = prime * result + currentHealth;
        result = prime * result + currentXP;
        result = prime * result + hunger;
        result = prime * result + id;
        result = prime * result + imageId;
        result = prime * result + intelligence;
        result = prime * result + maxHealth;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ownerId;
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
        PetDTO other = (PetDTO) obj;
        if (agility != other.agility)
            return false;
        if (currentHealth != other.currentHealth)
            return false;
        if (currentXP != other.currentXP)
            return false;
        if (hunger != other.hunger)
            return false;
        if (id != other.id)
            return false;
        if (imageId != other.imageId)
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
        if (ownerId != other.ownerId)
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

    @Override
    public String toString() {
        return "PetDTO [id=" + id + ", name=" + name + ", imageId=" + imageId + ", type=" + type + ", hunger=" + hunger
            + ", currentHealth=" + currentHealth + ", maxHealth=" + maxHealth + ", strength=" + strength + ", agility="
            + agility + ", intelligence=" + intelligence + ", petLevel=" + petLevel + ", currentXP=" + currentXP
            + ", ownerId=" + ownerId + "]";
    }

}
