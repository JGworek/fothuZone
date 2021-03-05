package zone.fothu.pets.model.adventure;

import java.io.Serializable;
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

import zone.fothu.pets.model.profile.Pet;

@Component
@Entity
@Table(name = "auto_battles", schema = "pets")
public class AutoBattle implements Serializable {

	private static final long serialVersionUID = 7118617841820485090L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "attacking_pet_id")
	private Pet attackingPet;

	@ManyToOne
	@JoinColumn(name = "defending_pet_id")
	private Pet defendingPet;

	@OneToMany(mappedBy = "battle")
	@JsonIgnoreProperties("battle")
	private List<AutoBattleLog> battleLogs;

	@ManyToOne
	@JoinColumn(name = "winning_pet_id")
	private Pet winningPet;

	@ManyToOne
	@JoinColumn(name = "losing_pet_id")
	private Pet losingPet;

	public AutoBattle() {
		super();
	}

	public AutoBattle(int id, Pet attackingPet, Pet defendingPet, List<AutoBattleLog> battleLogs, Pet winningPet, Pet losingPet) {
		super();
		this.id = id;
		this.attackingPet = attackingPet;
		this.defendingPet = defendingPet;
		this.battleLogs = battleLogs;
		this.winningPet = winningPet;
		this.losingPet = losingPet;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<AutoBattleLog> getBattleLogs() {
		return battleLogs;
	}

	public void setBattleLogs(List<AutoBattleLog> battleLogs) {
		this.battleLogs = battleLogs;
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

	@Override
	public int hashCode() {
		return Objects.hash(attackingPet, battleLogs, defendingPet, id, losingPet, winningPet);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AutoBattle)) {
			return false;
		}
		AutoBattle other = (AutoBattle) obj;
		return Objects.equals(attackingPet, other.attackingPet) && Objects.equals(battleLogs, other.battleLogs) && Objects.equals(defendingPet, other.defendingPet) && id == other.id && Objects.equals(losingPet, other.losingPet) && Objects.equals(winningPet, other.winningPet);
	}

	@Override
	public String toString() {
		return "AutoBattle [id=" + id + ", attackingPet=" + attackingPet + ", defendingPet=" + defendingPet + ", battleLogs=" + battleLogs + ", winningPet=" + winningPet + ", losingPet=" + losingPet + "]";
	}
}
