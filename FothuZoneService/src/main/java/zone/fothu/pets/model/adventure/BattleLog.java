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
@Table(name = "battle_logs", schema = "pets")
public class BattleLog implements Serializable {

    private static final long serialVersionUID = -2569174292055032333L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "battle_id")
    @JsonIgnoreProperties("battle_logs")
    private Battle battle;

    @Column(name = "turn_number")
    private int turnNumber;

    @Column(name = "turn_flavor_text")
    private String turnFlavorText;

    @Column(name = "turn_technical_text")
    private String turnTechnicalText;

    @Column(name = "battle_ended")
    private Boolean battleEnded;

    public BattleLog() {
        super();
    }

    public BattleLog(int id, Battle battle, int turnNumber, String turnFlavorText, String turnTechnicalText,
        Boolean battleEnded) {
        super();
        this.id = id;
        this.battle = battle;
        this.turnNumber = turnNumber;
        this.turnFlavorText = turnFlavorText;
        this.turnTechnicalText = turnTechnicalText;
        this.battleEnded = battleEnded;
    }

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

    public String getTurnFlavorText() {
        return turnFlavorText;
    }

    public void setTurnFlavorText(String turnFlavorText) {
        this.turnFlavorText = turnFlavorText;
    }

    public String getTurnTechnicalText() {
        return turnTechnicalText;
    }

    public void setTurnTechnicalText(String turnTechnicalText) {
        this.turnTechnicalText = turnTechnicalText;
    }

    public Boolean getBattleEnded() {
        return battleEnded;
    }

    public void setBattleEnded(Boolean battleEnded) {
        this.battleEnded = battleEnded;
    }

    @Override
    public int hashCode() {
        return Objects.hash(battle, battleEnded, id, turnFlavorText, turnNumber, turnTechnicalText);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BattleLog)) {
            return false;
        }
        BattleLog other = (BattleLog) obj;
        return Objects.equals(battle, other.battle) && Objects.equals(battleEnded, other.battleEnded) && id == other.id
            && Objects.equals(turnFlavorText, other.turnFlavorText) && turnNumber == other.turnNumber
            && Objects.equals(turnTechnicalText, other.turnTechnicalText);
    }

    @Override
    public String toString() {
        return "BattleLog [id=" + id + ", battle=" + battle + ", turnNumber=" + turnNumber + ", turnFlavorText="
            + turnFlavorText + ", turnTechnicalText=" + turnTechnicalText + ", battleEnded=" + battleEnded + "]";
    }
}
