package zone.fothu.pets.model;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BattleDTO implements Serializable {

    private static final long serialVersionUID = 3156351006751846654L;
    
    @Autowired
    private Battle battle;
    
    private int numberOfLevelUps;
    
    private boolean userVictory;

    public BattleDTO() {
        super();
    }

    public BattleDTO(Battle battle, int numberOfLevelUps, boolean userVictory) {
        super();
        this.battle = battle;
        this.numberOfLevelUps = numberOfLevelUps;
        this.userVictory = userVictory;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((battle == null) ? 0 : battle.hashCode());
        result = prime * result + numberOfLevelUps;
        result = prime * result + (userVictory ? 1231 : 1237);
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
        BattleDTO other = (BattleDTO) obj;
        if (battle == null) {
            if (other.battle != null)
                return false;
        } else if (!battle.equals(other.battle))
            return false;
        if (numberOfLevelUps != other.numberOfLevelUps)
            return false;
        if (userVictory != other.userVictory)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BattleDTO [battle=" + battle + ", numberOfLevelUps=" + numberOfLevelUps + ", userVictory=" + userVictory
            + "]";
    }
}
