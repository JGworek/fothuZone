package zone.fothu.pets.model.adventure;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import zone.fothu.pets.model.profile.Pet;

@Component
@Entity
@Table(name = "battles", schema = "pets")
public class BattleDTO implements Serializable {

	private static final long serialVersionUID = -283335836780804838L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "battle_type")
	private String battleType;

	@Column(name = "attacking_user_id")
	private int attackingUserId;

	@Column(name = "defending_user_id")
	private int defendingUserId;

	@Column(name = "attacking_pet_id")
	private int attackingPetId;

	@Column(name = "defending_pet_id")
	private int defendingPetId;

	@Column(name = "winning_pet_id")
	private int winningPetId;

	@Column(name = "losing_pet_id")
	private Pet losingPetId;

	@Column(name = "battle_finished")
	private boolean battleFinished;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	public BattleDTO() {
		super();
	}

	public BattleDTO(int id, String battleType, int attackingUserId, int defendingUserId, int attackingPetId, int defendingPetId, int winningPetId, Pet losingPetId, boolean battleFinished, LocalDateTime createdOn) {
		super();
		this.id = id;
		this.battleType = battleType;
		this.attackingUserId = attackingUserId;
		this.defendingUserId = defendingUserId;
		this.attackingPetId = attackingPetId;
		this.defendingPetId = defendingPetId;
		this.winningPetId = winningPetId;
		this.losingPetId = losingPetId;
		this.battleFinished = battleFinished;
		this.createdOn = createdOn;
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

	public int getAttackingUserId() {
		return attackingUserId;
	}

	public void setAttackingUserId(int attackingUserId) {
		this.attackingUserId = attackingUserId;
	}

	public int getDefendingUserId() {
		return defendingUserId;
	}

	public void setDefendingUserId(int defendingUserId) {
		this.defendingUserId = defendingUserId;
	}

	public int getAttackingPetId() {
		return attackingPetId;
	}

	public void setAttackingPetId(int attackingPetId) {
		this.attackingPetId = attackingPetId;
	}

	public int getDefendingPetId() {
		return defendingPetId;
	}

	public void setDefendingPetId(int defendingPetId) {
		this.defendingPetId = defendingPetId;
	}

	public int getWinningPetId() {
		return winningPetId;
	}

	public void setWinningPetId(int winningPetId) {
		this.winningPetId = winningPetId;
	}

	public Pet getLosingPetId() {
		return losingPetId;
	}

	public void setLosingPetId(Pet losingPetId) {
		this.losingPetId = losingPetId;
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

	@Override
	public int hashCode() {
		return Objects.hash(attackingPetId, attackingUserId, battleFinished, battleType, createdOn, defendingPetId, defendingUserId, id, losingPetId, winningPetId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof BattleDTO)) {
			return false;
		}
		BattleDTO other = (BattleDTO) obj;
		return attackingPetId == other.attackingPetId && attackingUserId == other.attackingUserId && battleFinished == other.battleFinished && Objects.equals(battleType, other.battleType) && Objects.equals(createdOn, other.createdOn) && defendingPetId == other.defendingPetId && defendingUserId == other.defendingUserId && id == other.id && Objects.equals(losingPetId, other.losingPetId) && winningPetId == other.winningPetId;
	}

	@Override
	public String toString() {
		return "BattleDTO [id=" + id + ", battleType=" + battleType + ", attackingUserId=" + attackingUserId + ", defendingUserId=" + defendingUserId + ", attackingPetId=" + attackingPetId + ", defendingPetId=" + defendingPetId + ", winningPetId=" + winningPetId + ", losingPetId=" + losingPetId + ", battleFinished=" + battleFinished + ", createdOn=" + createdOn + "]";
	}
}