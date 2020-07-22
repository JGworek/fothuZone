package zone.fothu.pets.model;

import java.io.Serializable;
import java.util.List;

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

@Component
@Entity
@Table(name="battles", schema="pets")
public class Battle implements Serializable {

	private static final long serialVersionUID = 7118617841820485090L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="attacking_pet_id")
	@JsonIgnoreProperties("battles")
	private Pet attackingPet;
	
	@ManyToOne
	@JoinColumn(name="defending_pet_id")
	@JsonIgnoreProperties("battles")
	private Pet defendingPet;
	
	@OneToMany(mappedBy="battle")
	@JsonIgnoreProperties("battle")
	private List<BattleLog> battleLogs;
	
	@ManyToOne
	@JoinColumn(name="winning_pet_id")
	@JsonIgnoreProperties("battles")
	private Pet winningPet;
	
	@ManyToOne
	@JoinColumn(name="losing_pet_id")
	@JsonIgnoreProperties("battles")
	private Pet losingPet;

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

	public List<BattleLog> getBattleLogs() {
		return battleLogs;
	}

	public void setBattleLogs(List<BattleLog> battleLogs) {
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

	public Battle() {
		super();
	}

	public Battle(int id, Pet attackingPet, Pet defendingPet, List<BattleLog> battleLogs, Pet winningPet,
			Pet losingPet) {
		super();
		this.id = id;
		this.attackingPet = attackingPet;
		this.defendingPet = defendingPet;
		this.battleLogs = battleLogs;
		this.winningPet = winningPet;
		this.losingPet = losingPet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attackingPet == null) ? 0 : attackingPet.hashCode());
		result = prime * result + ((battleLogs == null) ? 0 : battleLogs.hashCode());
		result = prime * result + ((defendingPet == null) ? 0 : defendingPet.hashCode());
		result = prime * result + id;
		result = prime * result + ((losingPet == null) ? 0 : losingPet.hashCode());
		result = prime * result + ((winningPet == null) ? 0 : winningPet.hashCode());
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
		Battle other = (Battle) obj;
		if (attackingPet == null) {
			if (other.attackingPet != null)
				return false;
		} else if (!attackingPet.equals(other.attackingPet))
			return false;
		if (battleLogs == null) {
			if (other.battleLogs != null)
				return false;
		} else if (!battleLogs.equals(other.battleLogs))
			return false;
		if (defendingPet == null) {
			if (other.defendingPet != null)
				return false;
		} else if (!defendingPet.equals(other.defendingPet))
			return false;
		if (id != other.id)
			return false;
		if (losingPet == null) {
			if (other.losingPet != null)
				return false;
		} else if (!losingPet.equals(other.losingPet))
			return false;
		if (winningPet == null) {
			if (other.winningPet != null)
				return false;
		} else if (!winningPet.equals(other.winningPet))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Battle [id=" + id + ", attackingPet=" + attackingPet + ", defendingPet=" + defendingPet
				+ ", battleLogs=" + battleLogs + ", winningPet=" + winningPet + ", losingPet=" + losingPet + "]";
	}
	
	
}

	