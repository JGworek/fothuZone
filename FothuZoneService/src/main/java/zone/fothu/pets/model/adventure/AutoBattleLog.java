package zone.fothu.pets.model.adventure;

import java.io.Serializable;
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

@Component
@Entity
@Table(name = "auto_battle_logs", schema = "pets")
public class AutoBattleLog implements Serializable {

	private static final long serialVersionUID = 534107614442847407L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "battle_id")
	@JsonIgnoreProperties("battleLogs")
	private AutoBattle battle;

	@Column(name = "turn_number")
	private int turnNumber;

	@Column(name = "turn_text")
	private String turnText;

	@Column(name = "turn_result")
	private String turnResult;

	@Column(name = "battle_finished")
	private Boolean battleFinished;

	public AutoBattleLog() {
		super();
	}

	public AutoBattleLog(int id, AutoBattle battle, int turnNumber, String turnText, String turnResult, Boolean battleFinished) {
		super();
		this.id = id;
		this.battle = battle;
		this.turnNumber = turnNumber;
		this.turnText = turnText;
		this.turnResult = turnResult;
		this.battleFinished = battleFinished;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AutoBattle getBattle() {
		return battle;
	}

	public void setBattle(AutoBattle battle) {
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

	@Override
	public int hashCode() {
		return Objects.hash(battle, battleFinished, id, turnNumber, turnResult, turnText);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AutoBattleLog)) {
			return false;
		}
		AutoBattleLog other = (AutoBattleLog) obj;
		return Objects.equals(battle, other.battle) && Objects.equals(battleFinished, other.battleFinished) && id == other.id && turnNumber == other.turnNumber && Objects.equals(turnResult, other.turnResult) && Objects.equals(turnText, other.turnText);
	}

	@Override
	public String toString() {
		return "AutoBattleLog [id=" + id + ", battle=" + battle + ", turnNumber=" + turnNumber + ", turnText=" + turnText + ", turnResult=" + turnResult + ", battleFinished=" + battleFinished + "]";
	}
}
