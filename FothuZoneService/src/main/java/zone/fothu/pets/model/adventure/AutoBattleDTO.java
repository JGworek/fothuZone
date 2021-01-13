package zone.fothu.pets.model.adventure;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoBattleDTO implements Serializable {

	private static final long serialVersionUID = 3156351006751846654L;

	@Autowired
	private AutoBattle battle;

	private int numberOfLevelUps;

	private boolean userVictory;

	public AutoBattleDTO() {
		super();
	}

	public AutoBattleDTO(AutoBattle battle, int numberOfLevelUps, boolean userVictory) {
		super();
		this.battle = battle;
		this.numberOfLevelUps = numberOfLevelUps;
		this.userVictory = userVictory;
	}

	public AutoBattle getBattle() {
		return battle;
	}

	public void setBattle(AutoBattle battle) {
		this.battle = battle;
	}

	public int getNumberOfLevelUps() {
		return numberOfLevelUps;
	}

	public void setNumberOfLevelUps(int numberOfLevelUps) {
		this.numberOfLevelUps = numberOfLevelUps;
	}

	public boolean isUserVictory() {
		return userVictory;
	}

	public void setUserVictory(boolean userVictory) {
		this.userVictory = userVictory;
	}

	@Override
	public int hashCode() {
		return Objects.hash(battle, numberOfLevelUps, userVictory);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AutoBattleDTO)) {
			return false;
		}
		AutoBattleDTO other = (AutoBattleDTO) obj;
		return Objects.equals(battle, other.battle) && numberOfLevelUps == other.numberOfLevelUps && userVictory == other.userVictory;
	}

	@Override
	public String toString() {
		return "AutoBattleDTO [battle=" + battle + ", numberOfLevelUps=" + numberOfLevelUps + ", userVictory=" + userVictory + "]";
	}
}
