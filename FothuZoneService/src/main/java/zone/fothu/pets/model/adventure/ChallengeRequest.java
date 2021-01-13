package zone.fothu.pets.model.adventure;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

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

import zone.fothu.pets.model.profile.User;

@Component
@Entity
@Table(name = "challenge_requests", schema = "pets")
public class ChallengeRequest implements Serializable {

	private static final long serialVersionUID = 798867279587257409L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "accepted_status")
	private boolean acceptedStatus;
	@Column(name = "rejected_status")
	private boolean rejectedStatus;

	@ManyToOne
	@JoinColumn(name = "attacker_id")
	@JsonIgnoreProperties("challenge_requests")
	private User attackingUser;

	@ManyToOne
	@JoinColumn(name = "defender_id")
	@JsonIgnoreProperties("challenge_requests")
	private User defendingUser;

	@ManyToOne
	@JoinColumn(name = "resulting_battle")
	@JsonIgnoreProperties("challenge_requests")
	private Battle resultingBattle;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	public ChallengeRequest() {
		super();
	}

	public ChallengeRequest(int id, boolean acceptedStatus, boolean rejectedStatus, User attackingUser, User defendingUser, Battle resultingBattle, LocalDateTime createdOn) {
		super();
		this.id = id;
		this.acceptedStatus = acceptedStatus;
		this.rejectedStatus = rejectedStatus;
		this.attackingUser = attackingUser;
		this.defendingUser = defendingUser;
		this.resultingBattle = resultingBattle;
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

	public Battle getResultingBattle() {
		return resultingBattle;
	}

	public void setResultingBattle(Battle resultingBattle) {
		this.resultingBattle = resultingBattle;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(acceptedStatus, attackingUser, createdOn, defendingUser, id, rejectedStatus, resultingBattle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ChallengeRequest)) {
			return false;
		}
		ChallengeRequest other = (ChallengeRequest) obj;
		return acceptedStatus == other.acceptedStatus && Objects.equals(attackingUser, other.attackingUser) && Objects.equals(createdOn, other.createdOn) && Objects.equals(defendingUser, other.defendingUser) && id == other.id && rejectedStatus == other.rejectedStatus && Objects.equals(resultingBattle, other.resultingBattle);
	}

	@Override
	public String toString() {
		return "ChallengeRequest [id=" + id + ", acceptedStatus=" + acceptedStatus + ", rejectedStatus=" + rejectedStatus + ", attackingUser=" + attackingUser + ", defendingUser=" + defendingUser + ", resultingBattle=" + resultingBattle + ", createdOn=" + createdOn + "]";
	}
}