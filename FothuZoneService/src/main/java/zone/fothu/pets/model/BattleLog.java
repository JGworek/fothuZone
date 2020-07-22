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
@Table(name="battle_logs", schema="pets")
public class BattleLog implements Serializable {

	private static final long serialVersionUID = 534107614442847407L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="battle_id")
	@JsonIgnoreProperties("battle_logs")
	private Battle battle;
	
	@Column(name="turn_number")
	private int turnNumber;
	
	@Column(name="turn_text")
	private String turnText;
	
	@Column(name="turn_result")
	private String turnResult;
	
	@Column(name="battle_finished")
	private Boolean battleFinished;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Battle getBattle() {
		return battle;
	}

	public void setBattle(Battle battle) {
		this.battle = battle;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public String getTurnText() {
		return turnText;
	}

	public void setTurnText(String turnText) {
		this.turnText = turnText;
	}

	public String getTurnResult() {
		return turnResult;
	}

	public void setTurnResult(String turnResult) {
		this.turnResult = turnResult;
	}

	public Boolean getBattleFinished() {
		return battleFinished;
	}

	public void setBattleFinished(Boolean battleFinished) {
		this.battleFinished = battleFinished;
	}

	public BattleLog() {
		super();
	}

	public BattleLog(int id, Battle battle, int turnNumber, String turnText, String turnResult,
			Boolean battleFinished) {
		super();
		this.id = id;
		this.battle = battle;
		this.turnNumber = turnNumber;
		this.turnText = turnText;
		this.turnResult = turnResult;
		this.battleFinished = battleFinished;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((battle == null) ? 0 : battle.hashCode());
		result = prime * result + ((battleFinished == null) ? 0 : battleFinished.hashCode());
		result = prime * result + id;
		result = prime * result + turnNumber;
		result = prime * result + ((turnResult == null) ? 0 : turnResult.hashCode());
		result = prime * result + ((turnText == null) ? 0 : turnText.hashCode());
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
		BattleLog other = (BattleLog) obj;
		if (battle == null) {
			if (other.battle != null)
				return false;
		} else if (!battle.equals(other.battle))
			return false;
		if (battleFinished == null) {
			if (other.battleFinished != null)
				return false;
		} else if (!battleFinished.equals(other.battleFinished))
			return false;
		if (id != other.id)
			return false;
		if (turnNumber != other.turnNumber)
			return false;
		if (turnResult == null) {
			if (other.turnResult != null)
				return false;
		} else if (!turnResult.equals(other.turnResult))
			return false;
		if (turnText == null) {
			if (other.turnText != null)
				return false;
		} else if (!turnText.equals(other.turnText))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BattleLog [id=" + id + ", battle=" + battle + ", turnNumber=" + turnNumber + ", turnText=" + turnText
				+ ", turnResult=" + turnResult + ", battleFinished=" + battleFinished + "]";
	}
	
}
