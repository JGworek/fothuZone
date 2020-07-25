package zone.fothu.pets.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class UserBattleResult implements Serializable {

    private static final long serialVersionUID = -6645781868341207185L;

    private int numberOfLevelUps;
    private boolean userVictory;

    public UserBattleResult() {
        super();
    }

    public UserBattleResult(int numberOfLevelUps, boolean userVictory) {
        super();
        this.numberOfLevelUps = numberOfLevelUps;
        this.userVictory = userVictory;
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
        UserBattleResult other = (UserBattleResult) obj;
        if (numberOfLevelUps != other.numberOfLevelUps)
            return false;
        if (userVictory != other.userVictory)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserBattleResult [numberOfLevelUps=" + numberOfLevelUps + ", userVictory=" + userVictory + "]";
    }
}