package zone.fothu.pets.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import zone.fothu.pets.exception.BattleNotFoundException;
import zone.fothu.pets.exception.PetNotFoundException;
import zone.fothu.pets.model.adventure.AutoBattleDTO;
import zone.fothu.pets.model.profile.Pet;
import zone.fothu.pets.repository.AutoBattleLogRepository;
import zone.fothu.pets.repository.AutoBattleRepository;
import zone.fothu.pets.repository.PetRepository;

@Service
public class AutoBattleService implements Serializable {

	private static final long serialVersionUID = -7951721923780422911L;

	@Autowired
	AutoBattleRepository battleRepository;
	@Autowired
	AutoBattleLogRepository battleLogRepository;
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
	private final int maxTurnCount = 50;
	private final int xpHealthModifier = 2;
	private double attackingHealth, defendingHealth;
	private double attackingArmor, defendingArmor;
	private double attackingSpeed, defendingSpeed;
	private double attackingAccuracy, defendingAccuracy;

	public AutoBattleDTO battle(int attackerId, int defenderId, String battleType) throws BattleNotFoundException, PetNotFoundException {
		int numberOfLevelUps = 0;
		boolean attackerVictory = false;
		// get pets
		attackingPet = petRepository.findById(attackerId);
		defendingPet = petRepository.findById(defenderId);
		// Create battle in database
		battleRepository.saveNewBattle(attackerId, defenderId);
		int currentBattleID = battleRepository.findLatestBattleID();
		// set pet attack values
		if (attackingPet.getType().equalsIgnoreCase("strength")) {
			attackingAttackPower = (attackingPet.getStrength() * attackModifier);
		} else if (attackingPet.getType().equalsIgnoreCase("agility")) {
			attackingAttackPower = (attackingPet.getAgility() * attackModifier);
		} else if (attackingPet.getType().equalsIgnoreCase("intelligence")) {
			attackingAttackPower = (attackingPet.getIntelligence() * attackModifier);
		}
		if (defendingPet.getType().equalsIgnoreCase("strength")) {
			defendingAttackPower = (defendingPet.getStrength() * attackModifier);
		} else if (defendingPet.getType().equalsIgnoreCase("agility")) {
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
			// defender attacks on even numbers
			if (attackCounter % 2 == 0) {
				if (defendingAccuracy + (Math.random() * 100) >= accuracyHurdle) {
					// battle ends if attacker health is less than 0
					if ((attackingHealth - (defendingAttackPower - attackingArmor)) <= 0) {
						battleFinished = true;
						attackingHealth = 0;
					}
					// defender hits
					if (attackingHealth > 0) {
						battleLogRepository.saveNewBattleLog(currentBattleID, turnNumber, defendingPet.getName() + " dealt " + (int) (defendingAttackPower - attackingArmor) + " damage to " + attackingPet.getName() + ". ", attackingPet.getName() + ": " + (attackingHealth - (defendingAttackPower - attackingArmor)) + " health, " + defendingPet.getName() + ": " + defendingHealth + " health.", battleFinished);
						attackingHealth = (attackingHealth - (defendingAttackPower - attackingArmor));
					} else if (attackingHealth == 0) {
						battleLogRepository.saveNewBattleLog(currentBattleID, turnNumber, defendingPet.getName() + " dealt " + (int) (defendingAttackPower - attackingArmor) + " damage to " + attackingPet.getName() + ". ", attackingPet.getName() + ": " + attackingHealth + " health, " + defendingPet.getName() + ": " + defendingHealth + " health.", battleFinished);
					}
				} else {
					// defender misses
					battleLogRepository.saveNewBattleLog(currentBattleID, turnNumber, defendingPet.getName() + " missed.", attackingPet.getName() + ": " + attackingHealth + " health, " + defendingPet.getName() + ": " + defendingHealth + " health.", battleFinished);
				}
			} else {
				// attacker attacks on odd numbers
				if (attackingAccuracy + (Math.random() * 100) >= accuracyHurdle) {
					// battle ends if defender health is less than 0
					if ((defendingHealth - (attackingAttackPower - defendingArmor)) <= 0) {
						battleFinished = true;
						defendingHealth = 0;
					}
					// attacker hits
					if (defendingHealth > 0) {
						battleLogRepository.saveNewBattleLog(currentBattleID, turnNumber, attackingPet.getName() + " dealt " + (int) (attackingAttackPower - defendingArmor) + " damage to " + defendingPet.getName() + ". ", attackingPet.getName() + ": " + attackingHealth + " health, " + defendingPet.getName() + ": " + (defendingHealth - (attackingAttackPower - defendingArmor)) + " health.", battleFinished);
						defendingHealth = (defendingHealth - (attackingAttackPower - defendingArmor));
					} else if (defendingHealth == 0) {
						battleLogRepository.saveNewBattleLog(currentBattleID, turnNumber, attackingPet.getName() + " dealt " + (int) (attackingAttackPower - defendingArmor) + " damage to " + defendingPet.getName() + ". ", attackingPet.getName() + ": " + attackingHealth + " health, " + defendingPet.getName() + ": " + defendingHealth + " health.", battleFinished);
					}
				} else {
					// attacker misses
					battleLogRepository.saveNewBattleLog(currentBattleID, turnNumber, attackingPet.getName() + " missed.", attackingPet.getName() + ": " + attackingHealth + " health, " + defendingPet.getName() + ": " + defendingHealth + " health.", battleFinished);
				}
			}
			// next turn and other pet gets a turn
			attackCounter++;
			turnNumber++;
			// battle ends if max turns are hit
			if (attackCounter > maxTurnCount) {
				battleFinished = true;
				battleLogRepository.updateLastBattleStepInTimeout(battleLogRepository.findLatestBattleLogID());
			}
			if (battleFinished == false) {
				continue BATTLE_LOOP;
			} else {
				if (attackingHealth > 0) {
					petRepository.setPetHealth((int) attackingHealth, attackerId);
				} else {
					petRepository.setPetHealth(0, attackerId);
				}
				if (defendingHealth > 0) {
					petRepository.setPetHealth((int) defendingHealth, defenderId);
				} else {
					petRepository.setPetHealth(0, defenderId);
				}
				if (defendingHealth >= attackingHealth) {
					battleRepository.setWinner(currentBattleID, defendingPet.getId(), attackingPet.getId());
					// ONLY USEFUL IF YOU WANT DEFENDER TO GET XP
//                    if (defendingPet.getId() != -1) {
//                        if (defendingPet.getPetLevel() - attackingPet.getPetLevel() <= 5) {
//                            setNewXP(defendingPet, attackingPet);
//                        }
//                    }
					attackerVictory = false;
				} else {
					battleRepository.setWinner(currentBattleID, attackingPet.getId(), defendingPet.getId());
					if (attackingPet.getId() != -1) {
						if (attackingPet.getPetLevel() - defendingPet.getPetLevel() <= 5) {
							numberOfLevelUps = setNewXP(attackingPet, defendingPet);
						}
						attackerVictory = true;
					}
				}
				if (attackingPet.getId() == -1) {
					petRepository.restoreOnePetsHealth(attackerId);
				}
				if (defendingPet.getId() == -1) {
					petRepository.restoreOnePetsHealth(defenderId);
				}
				break BATTLE_LOOP;
			}
		}

		AutoBattleDTO battleDTO = new AutoBattleDTO();

		if (battleType == "pve") {
			battleDTO.setBattle(battleRepository.findById(currentBattleID));
			battleDTO.setNumberOfLevelUps(numberOfLevelUps);
			battleDTO.setUserVictory(attackerVictory);
		}
		if (battleType == "pvp") {
			battleDTO.setBattle(battleRepository.findById(currentBattleID));
			battleDTO.setNumberOfLevelUps(0);
			battleDTO.setUserVictory(false);
		}
		return battleDTO;
	}

	// sets XP of winning pet, and levels them up appropriately if they did not
	// fight themself
	private int setNewXP(Pet winningPet, Pet losingPet) {
		int numberOfLevelUps = 0;
		if (winningPet.getOwner().getId() != losingPet.getOwner().getId()) {
			if (winningPet.getPetLevel() < 100) {
				int winningXP = (losingPet.getMaxHealth() * xpHealthModifier);
				winningPet.setCurrentXP(winningPet.getCurrentXP() + winningXP);
				while (winningPet.getCurrentXP() > petRepository.getXPToNextLevel(winningPet.getPetLevel() + 1) & winningPet.getPetLevel() < 100) {
					winningPet.setCurrentXP(winningPet.getCurrentXP() - petRepository.getXPToNextLevel(winningPet.getPetLevel() + 1));
					winningPet.setPetLevel(winningPet.getPetLevel() + 1);
					numberOfLevelUps = numberOfLevelUps + 1;
					if (winningPet.getPetLevel() == 100) {
						winningPet.setCurrentXP(0);
					}
				}
				petRepository.updatePetXPAndLevel(winningPet.getId(), winningPet.getCurrentXP(), winningPet.getPetLevel());
			}
		}
		return numberOfLevelUps;
	}

}
