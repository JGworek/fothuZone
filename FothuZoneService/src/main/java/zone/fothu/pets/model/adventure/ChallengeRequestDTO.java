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


@Component
@Entity
@Table(name = "challenge_requests", schema = "pets")
public class ChallengeRequestDTO implements Serializable {

	private static final long serialVersionUID = 8422718846877792019L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "accepted_status")
    private boolean acceptedStatus;
    @Column(name = "rejected_status")
    private boolean rejectedStatus;
    @Column(name = "attacker_id")
    private int attackingUserId;
    @Column(name = "defender_id")
    private int defendingUserId;
    @Column(name = "resulting_battle")
    private int resultingBattleId;
	@Column(name = "created_on")
	private LocalDateTime createdOn;

    public ChallengeRequestDTO() {
        super();
    }

	public ChallengeRequestDTO(int id, boolean acceptedStatus, boolean rejectedStatus, int attackingUserId,
			int defendingUserId, int resultingBattleId, LocalDateTime createdOn) {
		super();
		this.id = id;
		this.acceptedStatus = acceptedStatus;
		this.rejectedStatus = rejectedStatus;
		this.attackingUserId = attackingUserId;
		this.defendingUserId = defendingUserId;
		this.resultingBattleId = resultingBattleId;
		this.createdOn = createdOn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAcceptedStatus() {
		return acceptedStatus;
	}

	public void setAcceptedStatus(boolean acceptedStatus) {
		this.acceptedStatus = acceptedStatus;
	}

	public boolean isRejectedStatus() {
		return rejectedStatus;
	}

	public void setRejectedStatus(boolean rejectedStatus) {
		this.rejectedStatus = rejectedStatus;
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

	public int getResultingBattleId() {
		return resultingBattleId;
	}

	public void setResultingBattleId(int resultingBattleId) {
		this.resultingBattleId = resultingBattleId;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(acceptedStatus, attackingUserId, createdOn, defendingUserId, id, rejectedStatus,
				resultingBattleId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ChallengeRequestDTO)) {
			return false;
		}
		ChallengeRequestDTO other = (ChallengeRequestDTO) obj;
		return acceptedStatus == other.acceptedStatus && attackingUserId == other.attackingUserId
				&& Objects.equals(createdOn, other.createdOn) && defendingUserId == other.defendingUserId
				&& id == other.id && rejectedStatus == other.rejectedStatus
				&& resultingBattleId == other.resultingBattleId;
	}

	@Override
	public String toString() {
		return "ChallengeRequestDTO [id=" + id + ", acceptedStatus=" + acceptedStatus + ", rejectedStatus="
				+ rejectedStatus + ", attackingUserId=" + attackingUserId + ", defendingUserId=" + defendingUserId
				+ ", resultingBattleId=" + resultingBattleId + ", createdOn=" + createdOn + "]";
	}
}