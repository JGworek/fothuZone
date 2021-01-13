package zone.fothu.pets.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zone.fothu.pets.exception.WrongTurnException;
import zone.fothu.pets.model.adventure.Battle;
import zone.fothu.pets.model.adventure.BattleLog;
import zone.fothu.pets.model.adventure.ChallengeRequestDTO;
import zone.fothu.pets.model.profile.Pet;
import zone.fothu.pets.repository.BattleLogRepository;
import zone.fothu.pets.repository.BattleRepository;
import zone.fothu.pets.repository.ChallengeRequestDTORepository;
import zone.fothu.pets.repository.PetRepository;

@Service
public class BattleService implements Serializable {

	private static final long serialVersionUID = -7951721923780422911L;

	@Autowired
	BattleRepository battleRepository;
	@Autowired
	BattleLogRepository battleLogRepository;
	@Autowired
	ChallengeRequestDTORepository challengeRequestDTORepository;
	@Autowired
	PetRepository petRepository;

	private final int ACCURACY_HURDLE = 75;
	private final int DEFEND_HURDLE = 50;
	private final int AIM_HURDLE = 50;
	private final int SHARPEN_HURDLE = 50;
	private final double STARTING_ATTACK_MODIFIER = 1.5;
	private final double STARTING_ARMOR_MODIFIER = 1.5;
	private final double STARTING_ACCURACY_MODIFIER = 1.5;
	private final int HURDLE_MULTIPLIER = 3;
	private final double MODIFIER_INCREMENT = 0.5;
	private final int MAX_TURN_COUNT = 50;

	// make sure battle has flavor_text for the front end, and technical_text to
	// store in the DB for analysis

	// ROUND TO THE 4TH DECIMAL PLACE TO HANDLE REPEATING DECIMALS
	// ROUND DAMAGE VALUE AND DEFENCE VALUES DOWN (DID NOT MEET THRESHOLD FOR THE
	// NEXT VALUE)

	// set attack power for attacking and defending pet based on pet type stat
	// set armor value for attacking and defending pet based on str/int
	// set accuracy value for attacking and defending pet based on int/agi
	// set speed value for attacking and defending pet based on agi/str

	public ChallengeRequestDTO createNewChallengeRequest(int attackerId, int defenderId) {
		return challengeRequestDTORepository.createNewChallengeRequest(attackerId, defenderId);
	}

	public ChallengeRequestDTO acceptChallengeRequest(int challengeRequestId) {
		ChallengeRequestDTO acceptedChallengeRequest = challengeRequestDTORepository.acceptChallengeRequest(challengeRequestId);
		Battle newBattle = createNewBattleWithNoPets("pvp");
		acceptedChallengeRequest.setResultingBattleId(newBattle.getId());
		return acceptedChallengeRequest;
	}

	public ChallengeRequestDTO rejectChallengeRequest(int challengeRequestId) {
		return challengeRequestDTORepository.rejectChallengeRequest(challengeRequestId);
	}

	public Battle createNewBattleWithNoPets(String battleType) {
		return battleRepository.createNewBattle(battleType);
	}

	public Battle updateBattleWithAttackingPet(int battleId, int attackingPetId) {
		return battleRepository.setAttackingPet(battleId, attackingPetId);
	}

	public Battle updateBattleWithDefendingPet(int battleId, int defendingPetId) {
		return battleRepository.setDefendingPet(battleId, defendingPetId);
	}

	public Battle createNewBattleWithBothPets(String battleType, int attackingPetId, int defendingPetId) {
		Pet attackingPet = petRepository.findById(attackingPetId);
		Pet defendingPet = petRepository.findById(defendingPetId);
		int attackingPetCurrentHealth = 0;
		int defendingPetCurrentHealth = 0;
		if (battleType == "pvp") {
			attackingPetCurrentHealth = attackingPet.getMaxHealth();
			defendingPetCurrentHealth = defendingPet.getMaxHealth();
		} else {
			attackingPetCurrentHealth = attackingPet.getCurrentHealth();
			defendingPetCurrentHealth = defendingPet.getCurrentHealth();
		}
		return battleRepository.createNewBattleWithBothPets(battleType, attackingPetId, defendingPetId, attackingPetCurrentHealth, defendingPetCurrentHealth, STARTING_ATTACK_MODIFIER, STARTING_ARMOR_MODIFIER, STARTING_ACCURACY_MODIFIER, getBaseAttackPower(attackingPet), getBaseAttackPower(defendingPet), getBaseArmor(attackingPet), getBaseArmor(defendingPet), getBaseSpeed(attackingPet), getBaseSpeed(defendingPet), getBaseAccuracy(attackingPet), getBaseAccuracy(defendingPet), 1,
				getFirstTurnPetId(attackingPet, defendingPet));
	}

	double getBaseAttackPower(Pet currentPet) {
		if (currentPet.getType().toLowerCase() == "strength") {
			return currentPet.getStrength();
		} else if (currentPet.getType().toLowerCase() == "agility") {
			return currentPet.getAgility();
		} else {
			return currentPet.getIntelligence();
		}
	}

	double getBaseArmor(Pet currentPet) {
		return BigDecimal.valueOf(currentPet.getStrength() / currentPet.getIntelligence()).setScale(4, RoundingMode.DOWN).doubleValue();
	}

	double getBaseSpeed(Pet currentPet) {
		return BigDecimal.valueOf(currentPet.getAgility() / currentPet.getStrength()).setScale(4, RoundingMode.DOWN).doubleValue();
	}

	double getBaseAccuracy(Pet currentPet) {
		return BigDecimal.valueOf(currentPet.getIntelligence() / currentPet.getAgility()).setScale(4, RoundingMode.DOWN).doubleValue();
	}

	int getFirstTurnPetId(Pet attackingPet, Pet defendingPet) {
		if (getBaseSpeed(attackingPet) == getBaseSpeed(defendingPet)) {
			if (attackingPet.getAgility() == defendingPet.getAgility()) {
				List<Pet> bothPets = new ArrayList<Pet>();
				bothPets.add(attackingPet);
				bothPets.add(defendingPet);
				Random random = new Random();
				return bothPets.get(random.nextInt(bothPets.size() - 1)).getId();
			} else if (attackingPet.getAgility() > defendingPet.getAgility()) {
				return attackingPet.getId();
			} else {
				return defendingPet.getId();
			}
		} else if (getBaseSpeed(attackingPet) > getBaseSpeed(defendingPet)) {
			return attackingPet.getId();
		} else {
			return defendingPet.getId();
		}
	}

	Battle cleanOutTechnicalText(Battle currentBattle) {
		for (BattleLog battleLog : currentBattle.getBattleLogs()) {
			battleLog.setTurnTechnicalText(null);
		}
		return currentBattle;
	}

	public Battle attack(int battleId, int attackingId) {
		Battle currentBattle = battleRepository.getOne(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		if (attackingId != currentBattle.getCurrentTurnPet().getId()) {
			throw new WrongTurnException("It's not " + petRepository.getOne(attackingId).getName() + "'s turn!");
		}
		if (attackingId == currentBattle.getAttackingPet().getId()) {
			if ((currentBattle.getAttackingPetBaseAccuracy() * currentBattle.getAttackingPetCurrentAccuracyModifier()) + randomNumber >= ACCURACY_HURDLE + (currentBattle.getAttackingPet().getPetLevel() * HURDLE_MULTIPLIER)) {
				int defendingPetStartingHealth = currentBattle.getDefendingPetCurrentHealth();
				int attackingPetTotalAttack = (int) (currentBattle.getAttackingPetBaseAttackPower() * currentBattle.getAttackingPetCurrentAttackModifier());
				int defendingPetTotalArmor = (int) (currentBattle.getDefendingPetBaseArmor() * currentBattle.getDefendingPetCurrentArmorModifier());
				int defendingPetRemainingHealth = (defendingPetStartingHealth) - (attackingPetTotalAttack - defendingPetTotalArmor);
				int totalDealtDamage = defendingPetStartingHealth - defendingPetRemainingHealth;
				currentBattle.setDefendingPetCurrentHealth(defendingPetRemainingHealth);
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT | defendingPetRemainingHealth <= 0) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
					if (defendingPetRemainingHealth <= 0) {
						currentBattle.setDefendingPetCurrentHealth(0);
					}
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getDefendingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " attacked and dealt " + totalDealtDamage + " damage!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", successfully dealing " + totalDealtDamage + ", reducing " + currentBattle.getDefendingPet().getName() + "'s health from " + defendingPetStartingHealth + " to " + defendingPetRemainingHealth + ".", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			} else {
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getDefendingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " missed!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", failing to deal damage to " + currentBattle.getDefendingPet().getName() + ".", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			}
		} else {
			if ((currentBattle.getDefendingPetBaseAccuracy() * currentBattle.getDefendingPetCurrentAccuracyModifier()) + randomNumber >= ACCURACY_HURDLE + (currentBattle.getDefendingPet().getPetLevel() * HURDLE_MULTIPLIER)) {
				int attackingPetStartingHealth = currentBattle.getAttackingPetCurrentHealth();
				int defendingPetTotalAttack = (int) (currentBattle.getDefendingPetBaseAttackPower() * currentBattle.getDefendingPetCurrentAttackModifier());
				int attackingPetTotalArmor = (int) (currentBattle.getAttackingPetBaseArmor() * currentBattle.getAttackingPetCurrentArmorModifier());
				int attackingPetRemainingHealth = (attackingPetStartingHealth) - (defendingPetTotalAttack - attackingPetTotalArmor);
				int totalDealtDamage = attackingPetStartingHealth - attackingPetRemainingHealth;
				currentBattle.setDefendingPetCurrentHealth(attackingPetRemainingHealth);
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT | attackingPetRemainingHealth <= 0) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
					if (attackingPetRemainingHealth <= 0) {
						currentBattle.setDefendingPetCurrentHealth(0);
					}
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getDefendingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " attacked and dealt " + totalDealtDamage + " damage!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", successfully dealing " + totalDealtDamage + ", reducing " + currentBattle.getAttackingPet().getName() + "'s health from " + attackingPetStartingHealth + " to " + attackingPetRemainingHealth + ".", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			} else {
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getDefendingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " missed!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", failing to deal damage to " + currentBattle.getAttackingPet().getName() + ".", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			}
		}
	}

	public Battle defend(int battleId, int defendingId) {
		Battle currentBattle = battleRepository.getOne(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		if (defendingId != currentBattle.getCurrentTurnPet().getId()) {
			throw new WrongTurnException("It's not " + petRepository.getOne(defendingId).getName() + "'s turn!");
		}
		if (defendingId == currentBattle.getAttackingPet().getId()) {
			double currentArmorModifier = currentBattle.getAttackingPetCurrentArmorModifier();
			if (currentBattle.getAttackingPet().getStrength() + randomNumber >= DEFEND_HURDLE + (currentBattle.getAttackingPet().getPetLevel() * HURDLE_MULTIPLIER)) {

				currentBattle.setAttackingPetCurrentArmorModifier(currentBattle.getAttackingPetCurrentArmorModifier() + MODIFIER_INCREMENT);
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getDefendingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " armored up!", currentBattle.getAttackingPet().getName() + "has rolled a " + randomNumber + ", successfully increasing their armor modifier from " + currentArmorModifier + " to " + (currentArmorModifier + MODIFIER_INCREMENT) + ".", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			} else {
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getDefendingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " failed to armor up!", currentBattle.getAttackingPet().getName() + "has rolled a " + randomNumber + ", unsuccessfully increasing their armor modifier.", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			}
		} else {
			double currentArmorModifier = currentBattle.getDefendingPetCurrentArmorModifier();
			if (currentBattle.getDefendingPet().getStrength() + randomNumber >= DEFEND_HURDLE + (currentBattle.getDefendingPet().getPetLevel() * HURDLE_MULTIPLIER)) {

				currentBattle.setDefendingPetCurrentArmorModifier(currentBattle.getDefendingPetCurrentArmorModifier() + MODIFIER_INCREMENT);
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getAttackingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " armored up!", currentBattle.getDefendingPet().getName() + "has rolled a " + randomNumber + ", successfully increasing their armor modifier from " + currentArmorModifier + " to " + (currentArmorModifier + MODIFIER_INCREMENT) + ".", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			} else {
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getAttackingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " failed to armor up!", currentBattle.getDefendingPet().getName() + "has rolled a " + randomNumber + ", unsuccessfully increasing their armor modifier.", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			}
		}
	}

	public Battle aim(int battleId, int aimingId) {
		Battle currentBattle = battleRepository.getOne(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		if (aimingId != currentBattle.getCurrentTurnPet().getId()) {
			throw new WrongTurnException("It's not " + petRepository.getOne(aimingId).getName() + "'s turn!");
		}
		if (aimingId == currentBattle.getAttackingPet().getId()) {
			double currentAccuracyModifier = currentBattle.getAttackingPetCurrentAccuracyModifier();
			if (currentBattle.getAttackingPet().getIntelligence() + randomNumber >= AIM_HURDLE + (currentBattle.getAttackingPet().getPetLevel() * HURDLE_MULTIPLIER)) {

				currentBattle.setAttackingPetCurrentAccuracyModifier(currentBattle.getAttackingPetCurrentAccuracyModifier() + MODIFIER_INCREMENT);
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getDefendingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " took aim!", currentBattle.getAttackingPet().getName() + "has rolled a " + randomNumber + ", successfully increasing their accuracy modifier from " + currentAccuracyModifier + " to " + (currentAccuracyModifier + MODIFIER_INCREMENT) + ".", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			} else {
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getDefendingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " failed to take aim!", currentBattle.getAttackingPet().getName() + "has rolled a " + randomNumber + ", unsuccessfully increasing their accuracy modifier.", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			}
		} else {
			double currentAccuracyModifier = currentBattle.getDefendingPetCurrentAccuracyModifier();
			if (currentBattle.getDefendingPet().getIntelligence() + randomNumber >= AIM_HURDLE + (currentBattle.getDefendingPet().getPetLevel() * HURDLE_MULTIPLIER)) {

				currentBattle.setDefendingPetCurrentAccuracyModifier(currentBattle.getDefendingPetCurrentAccuracyModifier() + MODIFIER_INCREMENT);
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getAttackingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " took aim!", currentBattle.getDefendingPet().getName() + "has rolled a " + randomNumber + ", successfully increasing their accuracy modifier from " + currentAccuracyModifier + " to " + (currentAccuracyModifier + MODIFIER_INCREMENT) + ".", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			} else {
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getAttackingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " failed to take aim!", currentBattle.getDefendingPet().getName() + "has rolled a " + randomNumber + ", unsuccessfully increasing their accuracy modifier.", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			}
		}
	}

	public Battle sharpen(int battleId, int sharpeningId) {
		Battle currentBattle = battleRepository.getOne(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		if (sharpeningId != currentBattle.getCurrentTurnPet().getId()) {
			throw new WrongTurnException("It's not " + petRepository.getOne(sharpeningId).getName() + "'s turn!");
		}
		if (sharpeningId == currentBattle.getAttackingPet().getId()) {
			double currentAttackModifier = currentBattle.getAttackingPetCurrentAttackModifier();
			if (currentBattle.getAttackingPet().getAgility() + randomNumber >= SHARPEN_HURDLE + (currentBattle.getAttackingPet().getPetLevel() * HURDLE_MULTIPLIER)) {

				currentBattle.setAttackingPetCurrentAttackModifier(currentBattle.getAttackingPetCurrentAttackModifier() + MODIFIER_INCREMENT);
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getDefendingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " became more deadly!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their attack modifier from " + currentAttackModifier + " to " + (currentAttackModifier + MODIFIER_INCREMENT) + ".", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			} else {
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getDefendingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " failed to become more deadly!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their attack modifier.", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			}
		} else {
			double currentAttackModifier = currentBattle.getDefendingPetCurrentAttackModifier();
			if (currentBattle.getDefendingPet().getAgility() + randomNumber >= SHARPEN_HURDLE + (currentBattle.getDefendingPet().getPetLevel() * HURDLE_MULTIPLIER)) {

				currentBattle.setDefendingPetCurrentAttackModifier(currentBattle.getDefendingPetCurrentAttackModifier() + MODIFIER_INCREMENT);
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getAttackingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " became more deadly!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their attack modifier from " + currentAttackModifier + " to " + (currentAttackModifier + MODIFIER_INCREMENT) + ".", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			} else {
				if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
					currentBattle.setBattleFinished(true);
					battleFinished = true;
				}
				currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
				currentBattle.setCurrentTurnPet(currentBattle.getAttackingPet());
				battleRepository.save(currentBattle);
				battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " failed to become more deadly!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their attack modifier.", battleFinished);
				return cleanOutTechnicalText(battleRepository.getOne(currentBattle.getId()));
			}
		}
	}

	public void prematureEndBattle(int battleId) {
		Battle endedBattle = battleRepository.getOne(battleId);
		endedBattle.setBattleFinished(true);
		battleRepository.save(endedBattle);
	}
}