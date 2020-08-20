package zone.fothu.pets.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import zone.fothu.pets.exception.BattleNotFoundException;
import zone.fothu.pets.exception.PetNotFoundException;
import zone.fothu.pets.model.Battle;
import zone.fothu.pets.model.Pet;
import zone.fothu.pets.model.UserBattleResult;
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
    private final int maxTurnCount = 50;
    private final int xpHealthModifier = 2;
    private double attackingHealth, defendingHealth;
    private double attackingArmor, defendingArmor;
    private double attackingSpeed, defendingSpeed;
    private double attackingAccuracy, defendingAccuracy;
    private boolean scanned = false;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List battle(int attackerId, int defenderId) throws BattleNotFoundException, PetNotFoundException {
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
            // defender attacks on even numbers
            if (attackCounter % 2 == 0) {
                if (defendingAccuracy + (Math.random() * 100) >= accuracyHurdle) {
                    if ((attackingHealth - (defendingAttackPower - attackingArmor)) <= 0) {
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
                    if ((defendingHealth - (attackingAttackPower - defendingArmor)) <= 0) {
                        battleFinished = true;
                    }
                    battleLogRepository.saveNewBattleLog(currentBattleID, turnNumber,
                        attackingPet.getName() + " dealt " + (int) (attackingAttackPower - defendingArmor)
                            + " damage to " + defendingPet.getName() + ".",
                        attackingPet.getName() + ": " + attackingHealth + " health, " + defendingPet.getName() + ": "
                            + (defendingHealth - (attackingAttackPower - defendingArmor)) + " health.",
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
        Battle battleResult = battleRepository.findById(battleRepository.findLatestBattleID());
        List battleResultWithLevel = new ArrayList();
        battleResultWithLevel.add(battleResult);
        applicationContext.scan("zone.fothu.pets.model");
        if (scanned == false) {
            applicationContext.refresh();
            scanned = true;
        }
        UserBattleResult levelUpObject = applicationContext.getBean(UserBattleResult.class);
        levelUpObject.setNumberOfLevelUps(numberOfLevelUps);
        levelUpObject.setUserVictory(attackerVictory);
        battleResultWithLevel.add(levelUpObject);
        return battleResultWithLevel;
    }

    private int setNewXP(Pet winningPet, Pet losingPet) {
        int numberOfLevelUps = 0;
        if (winningPet.getOwner().getId() != losingPet.getOwner().getId()) {
            if (winningPet.getPetLevel() < 100) {
                int winningXP = (losingPet.getMaxHealth() * xpHealthModifier);
                winningPet.setCurrentXP(winningPet.getCurrentXP() + winningXP);
                while (winningPet.getCurrentXP() > petRepository.getXPToNextLevel(winningPet.getPetLevel() + 1)
                    & winningPet.getPetLevel() < 100) {
                    winningPet.setCurrentXP(
                        winningPet.getCurrentXP() - petRepository.getXPToNextLevel(winningPet.getPetLevel() + 1));
                    winningPet.setPetLevel(winningPet.getPetLevel() + 1);
                    numberOfLevelUps = numberOfLevelUps + 1;
                    if (winningPet.getPetLevel() == 100) {
                        winningPet.setCurrentXP(0);
                    }
                }
                petRepository.updatePetXPAndLevel(winningPet.getId(), winningPet.getCurrentXP(),
                    winningPet.getPetLevel());
            }
        }
        return numberOfLevelUps;
    }
}
