package zone.fothu.pets.model.adventure;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import zone.fothu.pets.model.profile.Pet;
import zone.fothu.pets.model.profile.User;

@Component
@JsonSerialize
@Entity
@Table(name = "battles", schema = "pets")
public class Battle implements Serializable {

	private static final long serialVersionUID = -22349369384602738L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "battle_type")
	private String battleType;

	@ManyToOne
	@JoinColumn(name = "attacking_user_id")
	private User attackingUser;

	@ManyToOne
	@JoinColumn(name = "defending_user_id")
	private User defendingUser;

	@ManyToOne
	@JoinColumn(name = "attacking_pet_id")
	private Pet attackingPet;

	@ManyToOne
	@JoinColumn(name = "defending_pet_id")
	private Pet defendingPet;

	@ManyToOne
	@JoinColumn(name = "winning_pet_id")
	private Pet winningPet;

	@ManyToOne
	@JoinColumn(name = "losing_pet_id")
	private Pet losingPet;

	@Column(name = "attacking_pet_current_health")
	private int attackingPetCurrentHealth;
	@Column(name = "defending_pet_current_health")
	private int defendingPetCurrentHealth;
	@Column(name = "attacking_pet_current_attack_modifier")
	private double attackingPetCurrentAttackModifier;
	@Column(name = "defending_pet_current_attack_modifier")
	private double defendingPetCurrentAttackModifier;
	@Column(name = "attacking_pet_current_armor_modifier")
	private double attackingPetCurrentArmorModifier;
	@Column(name = "defending_pet_current_armor_modifier")
	private double defendingPetCurrentArmorModifier;
	@Column(name = "attacking_pet_current_accuracy_modifier")
	private double attackingPetCurrentAccuracyModifier;
	@Column(name = "defending_pet_current_accuracy_modifier")
	private double defendingPetCurrentAccuracyModifier;
	@Column(name = "attacking_pet_base_attack_power")
	private double attackingPetBaseAttackPower;
	@Column(name = "defending_pet_base_attack_power")
	private double defendingPetBaseAttackPower;
	@Column(name = "attacking_pet_base_armor")
	private double attackingPetBaseArmor;
	@Column(name = "defending_pet_base_armor")
	private double defendingPetBaseArmor;
	@Column(name = "attacking_pet_base_speed")
	private double attackingPetBaseSpeed;
	@Column(name = "defending_pet_base_speed")
	private double defendingPetBaseSpeed;
	@Column(name = "attacking_pet_base_accuracy")
	private double attackingPetBaseAccuracy;
	@Column(name = "defending_pet_base_accuracy")
	private double defendingPetBaseAccuracy;
	@Column(name = "current_turn_count")
	private int currentTurnCount;

	@ManyToOne
	@JoinColumn(name = "current_turn_pet_id")
	private Pet currentTurnPet;

	@Column(name = "battle_finished")
	private boolean battleFinished;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@OneToMany(mappedBy = "battle")
	@JsonIgnoreProperties("battle")
	private List<BattleLog> battleLogs;

	public Battle() {
		super();
	}

	public Battle(int id, String battleType, User attackingUser, User defendingUser, Pet attackingPet, Pet defendingPet, Pet winningPet, Pet losingPet, int attackingPetCurrentHealth, int defendingPetCurrentHealth, double attackingPetCurrentAttackModifier, double defendingPetCurrentAttackModifier, double attackingPetCurrentArmorModifier, double defendingPetCurrentArmorModifier, double attackingPetCurrentAccuracyModifier, double defendingPetCurrentAccuracyModifier,
			double attackingPetBaseAttackPower, double defendingPetBaseAttackPower, double attackingPetBaseArmor, double defendingPetBaseArmor, double attackingPetBaseSpeed, double defendingPetBaseSpeed, double attackingPetBaseAccuracy, double defendingPetBaseAccuracy, int currentTurnCount, Pet currentTurnPet, boolean battleFinished, LocalDateTime createdOn, List<BattleLog> battleLogs) {
		super();
		this.id = id;
		this.battleType = battleType;
		this.attackingUser = attackingUser;
		this.defendingUser = defendingUser;
		this.attackingPet = attackingPet;
		this.defendingPet = defendingPet;
		this.winningPet = winningPet;
		this.losingPet = losingPet;
		this.attackingPetCurrentHealth = attackingPetCurrentHealth;
		this.defendingPetCurrentHealth = defendingPetCurrentHealth;
		this.attackingPetCurrentAttackModifier = attackingPetCurrentAttackModifier;
		this.defendingPetCurrentAttackModifier = defendingPetCurrentAttackModifier;
		this.attackingPetCurrentArmorModifier = attackingPetCurrentArmorModifier;
		this.defendingPetCurrentArmorModifier = defendingPetCurrentArmorModifier;
		this.attackingPetCurrentAccuracyModifier = attackingPetCurrentAccuracyModifier;
		this.defendingPetCurrentAccuracyModifier = defendingPetCurrentAccuracyModifier;
		this.attackingPetBaseAttackPower = attackingPetBaseAttackPower;
		this.defendingPetBaseAttackPower = defendingPetBaseAttackPower;
		this.attackingPetBaseArmor = attackingPetBaseArmor;
		this.defendingPetBaseArmor = defendingPetBaseArmor;
		this.attackingPetBaseSpeed = attackingPetBaseSpeed;
		this.defendingPetBaseSpeed = defendingPetBaseSpeed;
		this.attackingPetBaseAccuracy = attackingPetBaseAccuracy;
		this.defendingPetBaseAccuracy = defendingPetBaseAccuracy;
		this.currentTurnCount = currentTurnCount;
		this.currentTurnPet = currentTurnPet;
		this.battleFinished = battleFinished;
		this.createdOn = createdOn;
		this.battleLogs = battleLogs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBattleType() {
		return battleType;
	}

	public void setBattleType(String battleType) {
		this.battleType = battleType;
	}

	public User getAttackingUser() {
		return attackingUser;
	}

	public void setAttackingUser(User attackingUser) {
		this.attackingUser = attackingUser;
	}

	public User getDefendingUser() {
		return defendingUser;
	}

	public void setDefendingUser(User defendingUser) {
		this.defendingUser = defendingUser;
	}

	public Pet getAttackingPet() {
		return attackingPet;
	}

	public void setAttackingPet(Pet attackingPet) {
		this.attackingPet = attackingPet;
	}

	public Pet getDefendingPet() {
		return defendingPet;
	}

	public void setDefendingPet(Pet defendingPet) {
		this.defendingPet = defendingPet;
	}

	public Pet getWinningPet() {
		return winningPet;
	}

	public void setWinningPet(Pet winningPet) {
		this.winningPet = winningPet;
	}

	public Pet getLosingPet() {
		return losingPet;
	}

	public void setLosingPet(Pet losingPet) {
		this.losingPet = losingPet;
	}

	public int getAttackingPetCurrentHealth() {
		return attackingPetCurrentHealth;
	}

	public void setAttackingPetCurrentHealth(int attackingPetCurrentHealth) {
		this.attackingPetCurrentHealth = attackingPetCurrentHealth;
	}

	public int getDefendingPetCurrentHealth() {
		return defendingPetCurrentHealth;
	}

	public void setDefendingPetCurrentHealth(int defendingPetCurrentHealth) {
		this.defendingPetCurrentHealth = defendingPetCurrentHealth;
	}

	public double getAttackingPetCurrentAttackModifier() {
		return attackingPetCurrentAttackModifier;
	}

	public void setAttackingPetCurrentAttackModifier(double attackingPetCurrentAttackModifier) {
		this.attackingPetCurrentAttackModifier = attackingPetCurrentAttackModifier;
	}

	public double getDefendingPetCurrentAttackModifier() {
		return defendingPetCurrentAttackModifier;
	}

	public void setDefendingPetCurrentAttackModifier(double defendingPetCurrentAttackModifier) {
		this.defendingPetCurrentAttackModifier = defendingPetCurrentAttackModifier;
	}

	public double getAttackingPetCurrentArmorModifier() {
		return attackingPetCurrentArmorModifier;
	}

	public void setAttackingPetCurrentArmorModifier(double attackingPetCurrentArmorModifier) {
		this.attackingPetCurrentArmorModifier = attackingPetCurrentArmorModifier;
	}

	public double getDefendingPetCurrentArmorModifier() {
		return defendingPetCurrentArmorModifier;
	}

	public void setDefendingPetCurrentArmorModifier(double defendingPetCurrentArmorModifier) {
		this.defendingPetCurrentArmorModifier = defendingPetCurrentArmorModifier;
	}

	public double getAttackingPetCurrentAccuracyModifier() {
		return attackingPetCurrentAccuracyModifier;
	}

	public void setAttackingPetCurrentAccuracyModifier(double attackingPetCurrentAccuracyModifier) {
		this.attackingPetCurrentAccuracyModifier = attackingPetCurrentAccuracyModifier;
	}

	public double getDefendingPetCurrentAccuracyModifier() {
		return defendingPetCurrentAccuracyModifier;
	}

	public void setDefendingPetCurrentAccuracyModifier(double defendingPetCurrentAccuracyModifier) {
		this.defendingPetCurrentAccuracyModifier = defendingPetCurrentAccuracyModifier;
	}

	public double getAttackingPetBaseAttackPower() {
		return attackingPetBaseAttackPower;
	}

	public void setAttackingPetBaseAttackPower(double attackingPetBaseAttackPower) {
		this.attackingPetBaseAttackPower = attackingPetBaseAttackPower;
	}

	public double getDefendingPetBaseAttackPower() {
		return defendingPetBaseAttackPower;
	}

	public void setDefendingPetBaseAttackPower(double defendingPetBaseAttackPower) {
		this.defendingPetBaseAttackPower = defendingPetBaseAttackPower;
	}

	public double getAttackingPetBaseArmor() {
		return attackingPetBaseArmor;
	}

	public void setAttackingPetBaseArmor(double attackingPetBaseArmor) {
		this.attackingPetBaseArmor = attackingPetBaseArmor;
	}

	public double getDefendingPetBaseArmor() {
		return defendingPetBaseArmor;
	}

	public void setDefendingPetBaseArmor(double defendingPetBaseArmor) {
		this.defendingPetBaseArmor = defendingPetBaseArmor;
	}

	public double getAttackingPetBaseSpeed() {
		return attackingPetBaseSpeed;
	}

	public void setAttackingPetBaseSpeed(double attackingPetBaseSpeed) {
		this.attackingPetBaseSpeed = attackingPetBaseSpeed;
	}

	public double getDefendingPetBaseSpeed() {
		return defendingPetBaseSpeed;
	}

	public void setDefendingPetBaseSpeed(double defendingPetBaseSpeed) {
		this.defendingPetBaseSpeed = defendingPetBaseSpeed;
	}

	public double getAttackingPetBaseAccuracy() {
		return attackingPetBaseAccuracy;
	}

	public void setAttackingPetBaseAccuracy(double attackingPetBaseAccuracy) {
		this.attackingPetBaseAccuracy = attackingPetBaseAccuracy;
	}

	public double getDefendingPetBaseAccuracy() {
		return defendingPetBaseAccuracy;
	}

	public void setDefendingPetBaseAccuracy(double defendingPetBaseAccuracy) {
		this.defendingPetBaseAccuracy = defendingPetBaseAccuracy;
	}

	public int getCurrentTurnCount() {
		return currentTurnCount;
	}

	public void setCurrentTurnCount(int currentTurnCount) {
		this.currentTurnCount = currentTurnCount;
	}

	public Pet getCurrentTurnPet() {
		return currentTurnPet;
	}

	public void setCurrentTurnPet(Pet currentTurnPet) {
		this.currentTurnPet = currentTurnPet;
	}

	public boolean isBattleFinished() {
		return battleFinished;
	}

	public void setBattleFinished(boolean battleFinished) {
		this.battleFinished = battleFinished;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public List<BattleLog> getBattleLogs() {
		return battleLogs;
	}

	public void setBattleLogs(List<BattleLog> battleLogs) {
		this.battleLogs = battleLogs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attackingPet, attackingPetBaseAccuracy, attackingPetBaseArmor, attackingPetBaseAttackPower, attackingPetBaseSpeed, attackingPetCurrentAccuracyModifier, attackingPetCurrentArmorModifier, attackingPetCurrentAttackModifier, attackingPetCurrentHealth, attackingUser, battleFinished, battleLogs, battleType, createdOn, currentTurnCount, currentTurnPet, defendingPet, defendingPetBaseAccuracy, defendingPetBaseArmor, defendingPetBaseAttackPower, defendingPetBaseSpeed,
				defendingPetCurrentAccuracyModifier, defendingPetCurrentArmorModifier, defendingPetCurrentAttackModifier, defendingPetCurrentHealth, defendingUser, id, losingPet, winningPet);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Battle)) {
			return false;
		}
		Battle other = (Battle) obj;
		return Objects.equals(attackingPet, other.attackingPet) && Double.doubleToLongBits(attackingPetBaseAccuracy) == Double.doubleToLongBits(other.attackingPetBaseAccuracy) && Double.doubleToLongBits(attackingPetBaseArmor) == Double.doubleToLongBits(other.attackingPetBaseArmor) && Double.doubleToLongBits(attackingPetBaseAttackPower) == Double.doubleToLongBits(other.attackingPetBaseAttackPower)
				&& Double.doubleToLongBits(attackingPetBaseSpeed) == Double.doubleToLongBits(other.attackingPetBaseSpeed) && Double.doubleToLongBits(attackingPetCurrentAccuracyModifier) == Double.doubleToLongBits(other.attackingPetCurrentAccuracyModifier) && Double.doubleToLongBits(attackingPetCurrentArmorModifier) == Double.doubleToLongBits(other.attackingPetCurrentArmorModifier)
				&& Double.doubleToLongBits(attackingPetCurrentAttackModifier) == Double.doubleToLongBits(other.attackingPetCurrentAttackModifier) && attackingPetCurrentHealth == other.attackingPetCurrentHealth && Objects.equals(attackingUser, other.attackingUser) && battleFinished == other.battleFinished && Objects.equals(battleLogs, other.battleLogs) && Objects.equals(battleType, other.battleType) && Objects.equals(createdOn, other.createdOn) && currentTurnCount == other.currentTurnCount
				&& Objects.equals(currentTurnPet, other.currentTurnPet) && Objects.equals(defendingPet, other.defendingPet) && Double.doubleToLongBits(defendingPetBaseAccuracy) == Double.doubleToLongBits(other.defendingPetBaseAccuracy) && Double.doubleToLongBits(defendingPetBaseArmor) == Double.doubleToLongBits(other.defendingPetBaseArmor) && Double.doubleToLongBits(defendingPetBaseAttackPower) == Double.doubleToLongBits(other.defendingPetBaseAttackPower)
				&& Double.doubleToLongBits(defendingPetBaseSpeed) == Double.doubleToLongBits(other.defendingPetBaseSpeed) && Double.doubleToLongBits(defendingPetCurrentAccuracyModifier) == Double.doubleToLongBits(other.defendingPetCurrentAccuracyModifier) && Double.doubleToLongBits(defendingPetCurrentArmorModifier) == Double.doubleToLongBits(other.defendingPetCurrentArmorModifier)
				&& Double.doubleToLongBits(defendingPetCurrentAttackModifier) == Double.doubleToLongBits(other.defendingPetCurrentAttackModifier) && defendingPetCurrentHealth == other.defendingPetCurrentHealth && Objects.equals(defendingUser, other.defendingUser) && id == other.id && Objects.equals(losingPet, other.losingPet) && Objects.equals(winningPet, other.winningPet);
	}

	@Override
	public String toString() {
		return "Battle [id=" + id + ", battleType=" + battleType + ", attackingUser=" + attackingUser + ", defendingUser=" + defendingUser + ", attackingPet=" + attackingPet + ", defendingPet=" + defendingPet + ", winningPet=" + winningPet + ", losingPet=" + losingPet + ", attackingPetCurrentHealth=" + attackingPetCurrentHealth + ", defendingPetCurrentHealth=" + defendingPetCurrentHealth + ", attackingPetCurrentAttackModifier=" + attackingPetCurrentAttackModifier
				+ ", defendingPetCurrentAttackModifier=" + defendingPetCurrentAttackModifier + ", attackingPetCurrentArmorModifier=" + attackingPetCurrentArmorModifier + ", defendingPetCurrentArmorModifier=" + defendingPetCurrentArmorModifier + ", attackingPetCurrentAccuracyModifier=" + attackingPetCurrentAccuracyModifier + ", defendingPetCurrentAccuracyModifier=" + defendingPetCurrentAccuracyModifier + ", attackingPetBaseAttackPower=" + attackingPetBaseAttackPower
				+ ", defendingPetBaseAttackPower=" + defendingPetBaseAttackPower + ", attackingPetBaseArmor=" + attackingPetBaseArmor + ", defendingPetBaseArmor=" + defendingPetBaseArmor + ", attackingPetBaseSpeed=" + attackingPetBaseSpeed + ", defendingPetBaseSpeed=" + defendingPetBaseSpeed + ", attackingPetBaseAccuracy=" + attackingPetBaseAccuracy + ", defendingPetBaseAccuracy=" + defendingPetBaseAccuracy + ", currentTurnCount=" + currentTurnCount + ", currentTurnPet=" + currentTurnPet
				+ ", battleFinished=" + battleFinished + ", createdOn=" + createdOn + ", battleLogs=" + battleLogs + "]";
	}
}