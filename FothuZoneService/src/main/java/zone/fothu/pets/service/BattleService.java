package zone.fothu.pets.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import zone.fothu.pets.exception.BattleNotFoundException;
import zone.fothu.pets.exception.PetNotFoundException;
import zone.fothu.pets.model.Battle;
import zone.fothu.pets.model.Pet;
import zone.fothu.pets.repository.BattleLogRepository;
import zone.fothu.pets.repository.BattleRepository;
import zone.fothu.pets.repository.PetRepository;

@Service
public class BattleService implements Serializable {

	private static final long serialVersionUID = -7951721923780422911L;

	@Autowired
	BattleRepository battleRepository;
	@Autowired
	BattleLogRepository battleLogRepository;
	@Autowired
	PetRepository petRepository;
	@Autowired
	AnnotationConfigApplicationContext applicationContext;

	private double attackingAttackPower, defendingAttackPower;
	private Pet attackingPet;
	private Pet defendingPet;
	private final double attackModifier = 1.5;
	private final double armorModifier = 1.5;
	private final double speedModifier = 1.2;
	private final double accuracyModifier = 10;
	private final int accuracyHurdle = 75;
	private double attackingHealth, defendingHealth;
	private double attackingArmor, defendingArmor;
	private double attackingSpeed, defendingSpeed;
	private double attackingAccuracy, defendingAccuracy;
	
	public Battle battle(int attackerId, int defenderId) throws BattleNotFoundException, PetNotFoundException {
		// get pets
		attackingPet = petRepository.findById(attackerId);
		defendingPet = petRepository.findById(defenderId);
		// Create battle in database
		battleRepository.saveNewBattle(attackerId, defenderId);
		int currentBattleID = battleRepository.findLatestBattleID();
		// set pet attack values
		if (attackingPet.getType().equalsIgnoreCase("strength")) {
			attackingAttackPower = (attackingPet.getStrength() * attackModifier);
		} else if (attackingPet.getType().equalsIgnoreCase("dexterity")) {
			attackingAttackPower = (attackingPet.getAgility() * attackModifier);
		} else if (attackingPet.getType().equalsIgnoreCase("intelligence")) {
			attackingAttackPower = (attackingPet.getIntelligence() * attackModifier);
		}
		if (defendingPet.getType().equalsIgnoreCase("strength")) {
			defendingAttackPower = (defendingPet.getStrength() * attackModifier);
		} else if (defendingPet.getType().equalsIgnoreCase("dexterity")) {
			defendingAttackPower = (defendingPet.getAgility() * attackModifier);
		} else if (defendingPet.getType().equalsIgnoreCase("intelligence")) {
			defendingAttackPower = (defendingPet.getIntelligence() * attackModifier);
		}
		// set pet HP values
		attackingHealth = attackingPet.getCurrentHealth();
		defendingHealth = defendingPet.getCurrentHealth();
		// set pet armor values
		attackingArmor = (attackingPet.getAgility() * armorModifier);
		defendingArmor = (defendingPet.getAgility() * armorModifier);
		// set pet speed values
		attackingSpeed = (attackingPet.getAgility() * speedModifier);
		defendingSpeed = (defendingPet.getAgility() * speedModifier);
		// set pet accuracy values
		attackingAccuracy = ((attackingPet.getIntelligence() * accuracyModifier) / attackingPet.getAgility());
		defendingAccuracy = ((defendingPet.getIntelligence() * accuracyModifier) / defendingPet.getAgility());
		// battle
		int turnNumber = 1;
		int attackCounter;
		boolean battleFinished = false;
		// sets who goes first
		if (attackingSpeed >= defendingSpeed) {
			attackCounter = 1;
		} else {
			attackCounter = 2;
		}
		BATTLE_LOOP: while (attackingHealth > 0 | defendingHealth > 0) {
//			System.out.println("turn: " + turnNumber + ", attackingHealth: " + attackingHealth + ", defendingHealth: "
//					+ defendingHealth);
			// defender attacks on even numbers
			if (attackCounter % 2 == 0) {
				if (defendingAccuracy + (Math.random() * 100) >= accuracyHurdle) {
					if((attackingHealth - (defendingAttackPower - attackingArmor)) <= 0) {
						battleFinished = true;
					}
					battleLogRepository.saveNewBattleLog(currentBattleID, turnNumber,
							defendingPet.getName() + " dealt " + (int) (defendingAttackPower - attackingArmor)
									+ " damage to " + attackingPet.getName() + ".",
							attackingPet.getName() + ": " + (attackingHealth - (defendingAttackPower - attackingArmor))
									+ " health, " + defendingPet.getName() + ": " + defendingHealth + " health.",
							battleFinished);
					attackingHealth = (attackingHealth - (defendingAttackPower - attackingArmor));
				} else {

					battleLogRepository.saveNewBattleLog(currentBattleID, turnNumber,
							defendingPet.getName() + " missed.", attackingPet.getName() + ": " + attackingHealth
									+ " health, " + defendingPet.getName() + ": " + defendingHealth + " health.",
							battleFinished);
				}
			} else {
				// attacker attacks on odd numbers
				if (attackingAccuracy + (Math.random() * 100) >= accuracyHurdle) {
					if((defendingHealth - (attackingAttackPower - defendingArmor)) <= 0) {
						battleFinished = true;
					}
					battleLogRepository.saveNewBattleLog(currentBattleID, turnNumber,
							attackingPet.getName() + " dealt " + (int) (attackingAttackPower - defendingArmor)
									+ " damage to " + defendingPet.getName() + ".",
							attackingPet.getName() + ": " + attackingHealth + " health, " + defendingPet.getName()
									+ ": " + (defendingHealth - (attackingAttackPower - defendingArmor)) + " health.",
							battleFinished);
					defendingHealth = (defendingHealth - (attackingAttackPower - defendingArmor));
				} else {
					battleLogRepository.saveNewBattleLog(currentBattleID, turnNumber,
							attackingPet.getName() + " missed.", attackingPet.getName() + ": " + attackingHealth
									+ " health, " + defendingPet.getName() + ": " + defendingHealth + " health.",
							battleFinished);
				}
			}
			attackCounter++;
			turnNumber++;
			if (attackCounter >= 21) {
				battleFinished = true;
				battleLogRepository.updateLastBattleStepInTimeout(battleLogRepository.findLatestBattleLogID());
			}
			if (battleFinished == false) {
				continue BATTLE_LOOP;
			} else {
				petRepository.setPetHealth((int) attackingHealth, attackerId);
				petRepository.setPetHealth((int) defendingHealth, defenderId);
				if (defendingHealth >= attackingHealth) {
					battleRepository.setWinner(currentBattleID, defendingPet.getId(), attackingPet.getId());
				} else {
					battleRepository.setWinner(currentBattleID, attackingPet.getId(), defendingPet.getId());
				}
				break BATTLE_LOOP;
			}
		}
		Battle battleResult = battleRepository.findById(battleRepository.findLatestBattleID());
		return battleResult;
	}
}