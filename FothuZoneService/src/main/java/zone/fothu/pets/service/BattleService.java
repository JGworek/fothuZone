package zone.fothu.pets.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import zone.fothu.pets.controller.WebSocketBrokerController;
import zone.fothu.pets.exception.NotAttackingPetException;
import zone.fothu.pets.exception.NotDefendingPetException;
import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.exception.WrongBattlePetException;
import zone.fothu.pets.exception.WrongTurnException;
import zone.fothu.pets.model.adventure.Battle;
import zone.fothu.pets.model.adventure.BattleLog;
import zone.fothu.pets.model.adventure.ChallengeRequest;
import zone.fothu.pets.model.profile.Image;
import zone.fothu.pets.model.profile.Pet;
import zone.fothu.pets.model.profile.User;
import zone.fothu.pets.repository.BattleLogRepository;
import zone.fothu.pets.repository.BattleRepository;
import zone.fothu.pets.repository.ChallengeRequestRepository;
import zone.fothu.pets.repository.PetRepository;

@Service
public class BattleService implements Serializable {

	private static final long serialVersionUID = -7951721923780422911L;

	@Autowired
	BattleRepository battleRepository;
	@Autowired
	BattleLogRepository battleLogRepository;
	@Autowired
	ChallengeRequestRepository challengeRequestRepository;
	@Autowired
	PetRepository petRepository;
	@Autowired
	WebSocketBrokerController webSocketBrokerController;

	private final int ACCURACY_HURDLE = 50;
	private final int DEFEND_HURDLE = 50;
	private final int AIM_HURDLE = 50;
	private final int SHARPEN_HURDLE = 50;
	private final double STARTING_ATTACK_MODIFIER = 1;
	private final double STARTING_ARMOR_MODIFIER = 1;
	private final double STARTING_ACCURACY_MODIFIER = 1;
	private final int HURDLE_MULTIPLIER = 1;
	private final double MODIFIER_INCREMENT = 1;
	private final int MAX_TURN_COUNT = 100;
	private final int XP_HEALTH_MODIFIER = 2;
	private final Pet EMPTY_PET = new Pet(0, "", new Image(), "", 0, 0, 0, 0, 0, 0, 0, 0, 0, new User());
	// make sure battle has flavor_text for the front end, and technical_text to
	// store in the DB for analysis

	// ROUND TO THE 4TH DECIMAL PLACE TO HANDLE REPEATING DECIMALS
	// ROUND DAMAGE VALUE AND DEFENCE VALUES DOWN (DID NOT MEET THRESHOLD FOR THE
	// NEXT VALUE)

	// set attack power for attacking and defending pet based on pet type stat
	// set armor value for attacking and defending pet based on str/int
	// set accuracy value for attacking and defending pet based on int/agi
	// set speed value for attacking and defending pet based on agi/str

	public ChallengeRequest createNewChallengeRequest(int attackerId, int defenderId) {
		challengeRequestRepository.createNewChallengeRequest(attackerId, defenderId);
		webSocketBrokerController.updateChallengeSubscription(defenderId);
		return challengeRequestRepository.findById(challengeRequestRepository.getMostRecentChallengeRequestId()).get();
	}

	// NOT MAKING THE BATTLE WHEN ACCEPTED, FFS
	public ChallengeRequest acceptChallengeRequest(int challengeRequestId) {
		challengeRequestRepository.acceptChallengeRequest(challengeRequestId);
		ChallengeRequest acceptedChallengeRequest = challengeRequestRepository.findById(challengeRequestId).get();
		Battle newBattle = createNewBattleWithNoPets("pvp", acceptedChallengeRequest.getAttackingUser().getId(), acceptedChallengeRequest.getDefendingUser().getId());
		acceptedChallengeRequest.setResultingBattle(newBattle);
		challengeRequestRepository.save(acceptedChallengeRequest);
		webSocketBrokerController.updateChallengeSubscription(acceptedChallengeRequest.getDefendingUser().getId());
		webSocketBrokerController.updateCurrentBattleSubscription(newBattle.getAttackingUser().getId(), newBattle.getDefendingUser().getId());
		return acceptedChallengeRequest;
	}

	public ChallengeRequest rejectChallengeRequest(int challengeRequestId) {
		challengeRequestRepository.rejectChallengeRequest(challengeRequestId);
		ChallengeRequest rejectedChallengeRequest = challengeRequestRepository.findById(challengeRequestId).get();
		webSocketBrokerController.updateChallengeSubscription(rejectedChallengeRequest.getDefendingUser().getId());
		return rejectedChallengeRequest;
	}

	public Battle createNewBattleWithNoPets(String battleType, int attackingUserId, int defendingUserId) {
		battleRepository.createNewBattle(battleType, attackingUserId, defendingUserId);
		return battleRepository.findById(battleRepository.getMostRecentBattleId()).get();
	}

	public Battle createNewPVEBattleWithDefendingPet(int attackingUserId, int defendingPetId) {
		battleRepository.createNewPVEBattleWithDefendingPet(attackingUserId, petRepository.findById(defendingPetId).get().getOwner().getId(), defendingPetId);
		return battleRepository.findById(battleRepository.getMostRecentBattleId()).get();
	}

	public Battle updateBattleWithAttackingPet(int battleId, int attackingPetId) throws WrongBattlePetException {
		if (battleRepository.findById(battleId).get().getAttackingUser().getId() != petRepository.findById(attackingPetId).get().getOwner().getId()) {
			throw new WrongBattlePetException("That attacking user cannot set that pet for battle!");
		}
		battleRepository.setAttackingPet(battleId, attackingPetId, petRepository.findById(attackingPetId).get().getMaxHealth());
		Battle updatedBattle = getBattleById(battleId);
		if (updatedBattle.getAttackingPet().getId() != 0 & updatedBattle.getDefendingPet().getId() != 0) {
			return addBattleStatsForBattle(updatedBattle);
		}
		return updatedBattle;
	}

	public Battle updateBattleWithDefendingPet(int battleId, int defendingPetId) throws WrongBattlePetException {
		if (battleRepository.findById(battleId).get().getDefendingUser().getId() != petRepository.findById(defendingPetId).get().getOwner().getId()) {
			throw new WrongBattlePetException("That defending user cannot set that pet for battle!");
		}
		battleRepository.setDefendingPet(battleId, defendingPetId, petRepository.findById(defendingPetId).get().getMaxHealth());
		Battle updatedBattle = getBattleById(battleId);
		if (updatedBattle.getAttackingPet().getId() != 0 & updatedBattle.getDefendingPet().getId() != 0) {
			return addBattleStatsForBattle(updatedBattle);
		}
		return updatedBattle;
	}

	public Battle createNewBattleWithBothPets(String battleType, int attackingPetId, int defendingPetId) {
		Pet attackingPet = petRepository.findById(attackingPetId).get();
		Pet defendingPet = petRepository.findById(defendingPetId).get();
		int attackingPetCurrentHealth = 0;
		int defendingPetCurrentHealth = 0;
		if (battleType.equalsIgnoreCase("pvp")) {
			attackingPetCurrentHealth = attackingPet.getMaxHealth();
			defendingPetCurrentHealth = defendingPet.getMaxHealth();
		} else {
			attackingPetCurrentHealth = attackingPet.getCurrentHealth();
			defendingPetCurrentHealth = defendingPet.getCurrentHealth();
		}
		battleRepository.createNewBattleWithBothPets(battleType, attackingPetId, defendingPetId, attackingPetCurrentHealth, defendingPetCurrentHealth, STARTING_ATTACK_MODIFIER, STARTING_ARMOR_MODIFIER, STARTING_ACCURACY_MODIFIER, getBaseAttackPower(attackingPet), getBaseAttackPower(defendingPet), getBaseArmor(attackingPet), getBaseArmor(defendingPet), getBaseSpeed(attackingPet), getBaseSpeed(defendingPet), getBaseAccuracy(attackingPet), getBaseAccuracy(defendingPet), 1,
				getFirstTurnPetId(attackingPet, defendingPet), attackingPet.getOwner().getId(), defendingPet.getOwner().getId());
		return getBattleById(battleRepository.getMostRecentBattleId());
	}

	public Battle addBattleStatsForBattle(Battle newBattle) {
		Pet attackingPet = petRepository.findById(newBattle.getAttackingPet().getId()).get();
		Pet defendingPet = petRepository.findById(newBattle.getDefendingPet().getId()).get();
		int attackingPetCurrentHealth = 0;
		int defendingPetCurrentHealth = 0;
		if (newBattle.getBattleType().equalsIgnoreCase("pvp")) {
			attackingPetCurrentHealth = attackingPet.getMaxHealth();
			defendingPetCurrentHealth = defendingPet.getMaxHealth();
		} else {
			attackingPetCurrentHealth = attackingPet.getCurrentHealth();
			defendingPetCurrentHealth = defendingPet.getCurrentHealth();
		}

		battleRepository.addBattleStatsForBattle(newBattle.getId(), attackingPetCurrentHealth, defendingPetCurrentHealth, STARTING_ATTACK_MODIFIER, STARTING_ARMOR_MODIFIER, STARTING_ACCURACY_MODIFIER, getBaseAttackPower(attackingPet), getBaseAttackPower(defendingPet), getBaseArmor(attackingPet), getBaseArmor(defendingPet), getBaseSpeed(attackingPet), getBaseSpeed(defendingPet), getBaseAccuracy(attackingPet), getBaseAccuracy(defendingPet), 1, getFirstTurnPetId(attackingPet, defendingPet));
		return getBattleById(newBattle.getId());
	}

	double getBaseAttackPower(Pet currentPet) {
		if (currentPet.getType().equalsIgnoreCase("strength")) {
			return currentPet.getStrength();
		} else if (currentPet.getType().equalsIgnoreCase("agility")) {
			return currentPet.getAgility();
		} else {
			return currentPet.getIntelligence();
		}
	}

	double getBaseArmor(Pet currentPet) {
		return BigDecimal.valueOf((double) currentPet.getStrength() / (double) currentPet.getIntelligence()).setScale(4, RoundingMode.DOWN).doubleValue();
	}

	double getBaseSpeed(Pet currentPet) {
		return BigDecimal.valueOf((double) currentPet.getAgility() / (double) currentPet.getStrength()).setScale(4, RoundingMode.DOWN).doubleValue();
	}

	double getBaseAccuracy(Pet currentPet) {
		return BigDecimal.valueOf((double) currentPet.getIntelligence() / (double) currentPet.getAgility()).setScale(4, RoundingMode.DOWN).doubleValue();
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

	public void checkCurrentTurnPet(Battle currentBattle, int petId) {
		if (currentBattle.getCurrentTurnPet().getId() != petId) {
			throw new WrongTurnException("It's not " + petRepository.findById(petId).get().getName() + "'s turn!");
		}
	}

	public void checkAttackingPet(Battle currentBattle, int petId) {
		if (currentBattle.getAttackingPet().getId() != petId) {
			throw new NotAttackingPetException(petRepository.findById(petId).get().getName() + " is not the Attacking Pet in this battle!");
		}
	}

	public void checkDefendingPet(Battle currentBattle, int petId) {
		if (currentBattle.getDefendingPet().getId() != petId) {
			throw new NotDefendingPetException(petRepository.findById(petId).get().getName() + " is not the Defending Pet in this battle!");
		}
	}

	public boolean nextTurnAndSaveBattle(Battle currentBattle, Pet otherPet, boolean battleFinished) throws MessagingException, UserNotFoundException {
		if (currentBattle.getCurrentTurnCount() + 1 > MAX_TURN_COUNT) {
			currentBattle.setBattleFinished(true);
			if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
				currentBattle.setWinningPet(currentBattle.getDefendingPet());
				currentBattle.setLosingPet(currentBattle.getAttackingPet());
			} else if (currentBattle.getAttackingPetCurrentHealth() > currentBattle.getDefendingPetCurrentHealth()) {
				currentBattle.setWinningPet(currentBattle.getAttackingPet());
				currentBattle.setLosingPet(currentBattle.getDefendingPet());
			} else if (currentBattle.getAttackingPetCurrentHealth() < currentBattle.getDefendingPetCurrentHealth()) {
				currentBattle.setWinningPet(currentBattle.getDefendingPet());
				currentBattle.setLosingPet(currentBattle.getAttackingPet());
			}
			battleFinished = true;
		}
		currentBattle.setCurrentTurnCount(currentBattle.getCurrentTurnCount() + 1);
		currentBattle.setCurrentTurnPet(otherPet);
		int affectedHealth = 0;
		if (otherPet.getId() == currentBattle.getAttackingPet().getId()) {
			affectedHealth = currentBattle.getAttackingPetCurrentHealth();
		} else {
			affectedHealth = currentBattle.getDefendingPetCurrentHealth();
		}

		if (affectedHealth <= 0) {
			if (otherPet.getId() == currentBattle.getAttackingPet().getId()) {
				currentBattle.setAttackingPetCurrentHealth(0);
			} else {
				currentBattle.setDefendingPetCurrentHealth(0);
			}
			currentBattle.setBattleFinished(true);
			battleFinished = true;
		}
		if (battleFinished != true) {
			currentBattle = cleanOutWinnerAndLoser(currentBattle);
		} else {
			if (otherPet.getId() == currentBattle.getAttackingPet().getId()) {
				currentBattle.setWinningPet(currentBattle.getDefendingPet());
				currentBattle.setLosingPet(currentBattle.getAttackingPet());
			} else {
				currentBattle.setWinningPet(currentBattle.getAttackingPet());
				currentBattle.setLosingPet(currentBattle.getDefendingPet());
				if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
					giveWinningPetXP(currentBattle.getAttackingPet().getId(), currentBattle.getDefendingPet().getId());
				}
			}
		}
		battleRepository.save(currentBattle);
		if (battleFinished == true) {
			webSocketBrokerController.updateCurrentBattleSubscription(currentBattle.getAttackingUser().getId(), currentBattle.getDefendingUser().getId());
		}
		return battleFinished;
	}

	public void giveWinningPetXP(int winningPetId, int losingPetId) throws MessagingException, UserNotFoundException {
		Pet winningPet = petRepository.findById(winningPetId).get();
		Pet losingPet = petRepository.findById(losingPetId).get();
		if (winningPet.getPetLevel() < 100) {
			int winningXP = (losingPet.getMaxHealth() * XP_HEALTH_MODIFIER);
			winningPet.setCurrentXP(winningPet.getCurrentXP() + winningXP);
			while ((winningPet.getCurrentXP() > petRepository.getXPToNextLevel(winningPet.getPetLevel())) & winningPet.getPetLevel() < 100) {
				winningPet.setCurrentXP(winningPet.getCurrentXP() - petRepository.getXPToNextLevel(winningPet.getPetLevel()));
				winningPet.setPetLevel(winningPet.getPetLevel() + 1);
				if (winningPet.getPetLevel() == 100) {
					winningPet.setCurrentXP(0);
				}
			}
			petRepository.save(winningPet);
			webSocketBrokerController.updateUserSubscription(winningPet.getOwner().getId());
		}
	}

	public Battle attackingPetAttack(int battleId, int attackingId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = getBattleById(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		checkCurrentTurnPet(currentBattle, attackingId);
		checkAttackingPet(currentBattle, attackingId);
		if ((currentBattle.getAttackingPetBaseAccuracy() * currentBattle.getAttackingPetCurrentAccuracyModifier()) + randomNumber >= ACCURACY_HURDLE + (currentBattle.getAttackingPet().getPetLevel() * HURDLE_MULTIPLIER)) {
			int defendingPetStartingHealth = currentBattle.getDefendingPetCurrentHealth();
			int attackingPetTotalAttack = (int) (currentBattle.getAttackingPetBaseAttackPower() * currentBattle.getAttackingPetCurrentAttackModifier());
			int defendingPetTotalArmor = (int) (currentBattle.getDefendingPetBaseArmor() * currentBattle.getDefendingPetCurrentArmorModifier());
			int defendingPetRemainingHealth = (defendingPetStartingHealth) - (attackingPetTotalAttack - defendingPetTotalArmor);
			int totalDealtDamage = defendingPetStartingHealth - defendingPetRemainingHealth;
			currentBattle.setDefendingPetCurrentHealth(defendingPetRemainingHealth);
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getDefendingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " attacked and dealt " + totalDealtDamage + " damage!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", successfully dealing " + totalDealtDamage + ", reducing " + currentBattle.getDefendingPet().getName() + "'s health from " + defendingPetStartingHealth + " to " + defendingPetRemainingHealth + ".", battleFinished);
			System.out.println(currentBattle.getAttackingPet().getName() + " attacked and dealt " + totalDealtDamage + " damage!");
			System.out.println(currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", successfully dealing " + totalDealtDamage + ", reducing " + currentBattle.getDefendingPet().getName() + "'s health from " + defendingPetStartingHealth + " to " + defendingPetRemainingHealth + ".");
			return cleanOutTechnicalText(getBattleById(battleId));
		} else {
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getDefendingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " missed!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", failing to deal damage to " + currentBattle.getDefendingPet().getName() + ".", battleFinished);
			System.out.println(currentBattle.getAttackingPet().getName() + " missed!");
			System.out.println(currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", failing to deal damage to " + currentBattle.getDefendingPet().getName() + ".");
			return cleanOutTechnicalText(getBattleById(battleId));
		}
	}

	public Battle defendingPetAttack(int battleId, int attackingId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = getBattleById(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		checkCurrentTurnPet(currentBattle, attackingId);
		checkDefendingPet(currentBattle, attackingId);
		if ((currentBattle.getDefendingPetBaseAccuracy() * currentBattle.getDefendingPetCurrentAccuracyModifier()) + randomNumber >= ACCURACY_HURDLE + (currentBattle.getDefendingPet().getPetLevel() * HURDLE_MULTIPLIER)) {
			int attackingPetStartingHealth = currentBattle.getAttackingPetCurrentHealth();
			int defendingPetTotalAttack = (int) (currentBattle.getDefendingPetBaseAttackPower() * currentBattle.getDefendingPetCurrentAttackModifier());
			int attackingPetTotalArmor = (int) (currentBattle.getAttackingPetBaseArmor() * currentBattle.getAttackingPetCurrentArmorModifier());
			int attackingPetRemainingHealth = (attackingPetStartingHealth) - (defendingPetTotalAttack - attackingPetTotalArmor);
			int totalDealtDamage = attackingPetStartingHealth - attackingPetRemainingHealth;
			currentBattle.setAttackingPetCurrentHealth(attackingPetRemainingHealth);
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getAttackingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " attacked and dealt " + totalDealtDamage + " damage!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", successfully dealing " + totalDealtDamage + ", reducing " + currentBattle.getAttackingPet().getName() + "'s health from " + attackingPetStartingHealth + " to " + attackingPetRemainingHealth + ".", battleFinished);
			if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
				Pet hurtUserPet = petRepository.findById(currentBattle.getAttackingPet().getId()).get();
				hurtUserPet.setCurrentHealth(currentBattle.getAttackingPetCurrentHealth());
				petRepository.save(hurtUserPet);
				webSocketBrokerController.updateUserSubscription(currentBattle.getAttackingUser().getId());
			}
			System.out.println(currentBattle.getDefendingPet().getName() + " attacked and dealt " + totalDealtDamage + " damage!");
			System.out.println(currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", successfully dealing " + totalDealtDamage + ", reducing " + currentBattle.getAttackingPet().getName() + "'s health from " + attackingPetStartingHealth + " to " + attackingPetRemainingHealth + ".");
			return cleanOutTechnicalText(getBattleById(battleId));
		} else {
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getAttackingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " missed!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", failing to deal damage to " + currentBattle.getAttackingPet().getName() + ".", battleFinished);
			System.out.println(currentBattle.getDefendingPet().getName() + " missed!");
			System.out.println(currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", failing to deal damage to " + currentBattle.getAttackingPet().getName() + ".");
			return cleanOutTechnicalText(getBattleById(battleId));
		}
	}

	public Battle attackingPetDefend(int battleId, int defendingId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = getBattleById(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		checkCurrentTurnPet(currentBattle, defendingId);
		checkAttackingPet(currentBattle, defendingId);
		double currentArmorModifier = currentBattle.getAttackingPetCurrentArmorModifier();
		if (currentBattle.getAttackingPet().getStrength() + randomNumber >= DEFEND_HURDLE + (currentBattle.getAttackingPet().getPetLevel() * HURDLE_MULTIPLIER)) {
			currentBattle.setAttackingPetCurrentArmorModifier(currentBattle.getAttackingPetCurrentArmorModifier() + MODIFIER_INCREMENT);
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getDefendingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " armored up!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their armor modifier from " + currentArmorModifier + " to " + (currentArmorModifier + MODIFIER_INCREMENT) + ".", battleFinished);
			System.out.println(currentBattle.getAttackingPet().getName() + " armored up!");
			System.out.println(currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their armor modifier from " + currentArmorModifier + " to " + (currentArmorModifier + MODIFIER_INCREMENT) + ".");
			return cleanOutTechnicalText(getBattleById(battleId));
		} else {
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getDefendingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " failed to armor up!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their armor modifier.", battleFinished);
			System.out.println(currentBattle.getAttackingPet().getName() + " failed to armor up!");
			System.out.println(currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their armor modifier.");
			return cleanOutTechnicalText(getBattleById(battleId));
		}
	}

	public Battle defendingPetDefend(int battleId, int defendingId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = getBattleById(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		checkCurrentTurnPet(currentBattle, defendingId);
		checkDefendingPet(currentBattle, defendingId);
		double currentArmorModifier = currentBattle.getDefendingPetCurrentArmorModifier();
		if (currentBattle.getDefendingPet().getStrength() + randomNumber >= DEFEND_HURDLE + (currentBattle.getDefendingPet().getPetLevel() * HURDLE_MULTIPLIER)) {
			currentBattle.setDefendingPetCurrentArmorModifier(currentBattle.getDefendingPetCurrentArmorModifier() + MODIFIER_INCREMENT);
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getAttackingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " armored up!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their armor modifier from " + currentArmorModifier + " to " + (currentArmorModifier + MODIFIER_INCREMENT) + ".", battleFinished);
			System.out.println(currentBattle.getDefendingPet().getName() + " armored up!");
			System.out.println(currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their armor modifier from " + currentArmorModifier + " to " + (currentArmorModifier + MODIFIER_INCREMENT) + ".");
			return cleanOutTechnicalText(getBattleById(battleId));
		} else {
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getAttackingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " failed to armor up!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their armor modifier.", battleFinished);
			System.out.println(currentBattle.getDefendingPet().getName() + " failed to armor up!");
			System.out.println(currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their armor modifier.");
			return cleanOutTechnicalText(getBattleById(battleId));
		}
	}

	public Battle attackingPetAim(int battleId, int aimingId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = getBattleById(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		checkCurrentTurnPet(currentBattle, aimingId);
		checkAttackingPet(currentBattle, aimingId);
		double currentAccuracyModifier = currentBattle.getAttackingPetCurrentAccuracyModifier();
		if (currentBattle.getAttackingPet().getIntelligence() + randomNumber >= AIM_HURDLE + (currentBattle.getAttackingPet().getPetLevel() * HURDLE_MULTIPLIER)) {
			currentBattle.setAttackingPetCurrentAccuracyModifier(currentBattle.getAttackingPetCurrentAccuracyModifier() + MODIFIER_INCREMENT);
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getDefendingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " took aim!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their accuracy modifier from " + currentAccuracyModifier + " to " + (currentAccuracyModifier + MODIFIER_INCREMENT) + ".", battleFinished);
			System.out.println(currentBattle.getAttackingPet().getName() + " took aim!");
			System.out.println(currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their accuracy modifier from " + currentAccuracyModifier + " to " + (currentAccuracyModifier + MODIFIER_INCREMENT) + ".");
			return cleanOutTechnicalText(getBattleById(battleId));
		} else {
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getDefendingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " failed to take aim!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their accuracy modifier.", battleFinished);
			System.out.println(currentBattle.getAttackingPet().getName() + " failed to take aim!");
			System.out.println(currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their accuracy modifier.");
			return cleanOutTechnicalText(getBattleById(battleId));
		}
	}

	public Battle defendingPetAim(int battleId, int aimingId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = getBattleById(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		checkCurrentTurnPet(currentBattle, aimingId);
		checkDefendingPet(currentBattle, aimingId);
		double currentAccuracyModifier = currentBattle.getDefendingPetCurrentAccuracyModifier();
		if (currentBattle.getDefendingPet().getIntelligence() + randomNumber >= AIM_HURDLE + (currentBattle.getDefendingPet().getPetLevel() * HURDLE_MULTIPLIER)) {
			currentBattle.setDefendingPetCurrentAccuracyModifier(currentBattle.getDefendingPetCurrentAccuracyModifier() + MODIFIER_INCREMENT);
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getAttackingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " took aim!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their accuracy modifier from " + currentAccuracyModifier + " to " + (currentAccuracyModifier + MODIFIER_INCREMENT) + ".", battleFinished);
			System.out.println(currentBattle.getDefendingPet().getName() + " took aim!");
			System.out.println(currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their accuracy modifier from " + currentAccuracyModifier + " to " + (currentAccuracyModifier + MODIFIER_INCREMENT) + ".");
			return cleanOutTechnicalText(getBattleById(battleId));
		} else {
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getAttackingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " failed to take aim!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their accuracy modifier.", battleFinished);
			System.out.println(currentBattle.getDefendingPet().getName() + " failed to take aim!");
			System.out.println(currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their accuracy modifier.");
			return cleanOutTechnicalText(getBattleById(battleId));
		}
	}

	public Battle attackingPetSharpen(int battleId, int sharpeningId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = getBattleById(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		checkCurrentTurnPet(currentBattle, sharpeningId);
		checkAttackingPet(currentBattle, sharpeningId);
		double currentAttackModifier = currentBattle.getAttackingPetCurrentAttackModifier();
		if (currentBattle.getAttackingPet().getAgility() + randomNumber >= SHARPEN_HURDLE + (currentBattle.getAttackingPet().getPetLevel() * HURDLE_MULTIPLIER)) {
			currentBattle.setAttackingPetCurrentAttackModifier(currentBattle.getAttackingPetCurrentAttackModifier() + MODIFIER_INCREMENT);
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getDefendingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " became more deadly!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their attack modifier from " + currentAttackModifier + " to " + (currentAttackModifier + MODIFIER_INCREMENT) + ".", battleFinished);
			System.out.println(currentBattle.getAttackingPet().getName() + " became more deadly!");
			System.out.println(currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their attack modifier from " + currentAttackModifier + " to " + (currentAttackModifier + MODIFIER_INCREMENT) + ".");
			return cleanOutTechnicalText(getBattleById(battleId));
		} else {
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getDefendingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getAttackingPet().getName() + " failed to become more deadly!", currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their attack modifier.", battleFinished);
			System.out.println(currentBattle.getAttackingPet().getName() + " failed to become more deadly!");
			System.out.println(currentBattle.getAttackingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their attack modifier.");
			return cleanOutTechnicalText(getBattleById(battleId));
		}
	}

	public Battle defendingPetSharpen(int battleId, int sharpeningId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = getBattleById(battleId);
		int currentTurnCount = currentBattle.getCurrentTurnCount();
		boolean battleFinished = false;
		int randomNumber = (int) (Math.random() * 100);
		checkCurrentTurnPet(currentBattle, sharpeningId);
		checkDefendingPet(currentBattle, sharpeningId);
		double currentAttackModifier = currentBattle.getDefendingPetCurrentAttackModifier();
		if (currentBattle.getDefendingPet().getAgility() + randomNumber >= SHARPEN_HURDLE + (currentBattle.getDefendingPet().getPetLevel() * HURDLE_MULTIPLIER)) {
			currentBattle.setDefendingPetCurrentAttackModifier(currentBattle.getDefendingPetCurrentAttackModifier() + MODIFIER_INCREMENT);
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getAttackingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " became more deadly!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their attack modifier from " + currentAttackModifier + " to " + (currentAttackModifier + MODIFIER_INCREMENT) + ".", battleFinished);
			System.out.println(currentBattle.getDefendingPet().getName() + " became more deadly!");
			System.out.println(currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", successfully increasing their attack modifier from " + currentAttackModifier + " to " + (currentAttackModifier + MODIFIER_INCREMENT) + ".");
			return cleanOutTechnicalText(getBattleById(battleId));
		} else {
			battleFinished = nextTurnAndSaveBattle(currentBattle, currentBattle.getAttackingPet(), battleFinished);
			battleLogRepository.saveNewBattleLog(currentBattle.getId(), currentTurnCount, currentBattle.getDefendingPet().getName() + " failed to become more deadly!", currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their attack modifier.", battleFinished);
			System.out.println(currentBattle.getDefendingPet().getName() + " failed to become more deadly!");
			System.out.println(currentBattle.getDefendingPet().getName() + " has rolled a " + randomNumber + ", unsuccessfully increasing their attack modifier.");
			return cleanOutTechnicalText(getBattleById(battleId));
		}
	}

	public Battle prematureEndBattle(int battleId, String battleType) {
		Battle endedBattle = getBattleById(battleId);
		endedBattle.setBattleFinished(true);
		if (battleType.equalsIgnoreCase("pve")) {
			if (endedBattle.getBattleType().toLowerCase() == battleType) {
				List<Pet> userPets = petRepository.findAllPetsByUserId(endedBattle.getAttackingUser().getId());
				for (Pet pet : userPets) {
					pet.setCurrentHealth(0);
					petRepository.save(pet);
				}
			}
		}
		battleRepository.save(endedBattle);
		return cleanOutTechnicalText(endedBattle);
	}

	Battle cleanOutTechnicalText(Battle currentBattle) {
		for (BattleLog battleLog : currentBattle.getBattleLogs()) {
			battleLog.setTurnTechnicalText(null);
		}
		return cleanOutPasswords(currentBattle);
	}

	public Battle cleanOutPasswords(Battle battle) {
		if (battle.getAttackingPet() != null) {
			battle.getAttackingPet().getOwner().setUserPassword(null);
			battle.getAttackingPet().getOwner().setSecretPassword(null);
		}
		if (battle.getDefendingPet() != null) {
			battle.getDefendingPet().getOwner().setUserPassword(null);
			battle.getDefendingPet().getOwner().setSecretPassword(null);
		}
		if (battle.getWinningPet() != null) {
			battle.getWinningPet().getOwner().setUserPassword(null);
			battle.getWinningPet().getOwner().setSecretPassword(null);
		}
		if (battle.getLosingPet() != null) {
			battle.getLosingPet().getOwner().setUserPassword(null);
			battle.getLosingPet().getOwner().setSecretPassword(null);
		}
		if (battle.getAttackingUser() != null) {
			battle.getAttackingUser().setUserPassword(null);
			battle.getAttackingUser().setSecretPassword(null);
		}
		if (battle.getDefendingUser() != null) {
			battle.getDefendingUser().setUserPassword(null);
			battle.getDefendingUser().setSecretPassword(null);
		}
		return battle;
	}

	public Battle getBattleById(int id) {
		Battle battle = cleanOutPasswords(battleRepository.findById(id).get());
		if (battle.getAttackingPet() == null) {
			battle.setAttackingPet(EMPTY_PET);
		}
		if (battle.getDefendingPet() == null) {
			battle.setDefendingPet(EMPTY_PET);
		}
		if (battle.getWinningPet() == null) {
			battle.setWinningPet(EMPTY_PET);
		}
		if (battle.getLosingPet() == null) {
			battle.setLosingPet(EMPTY_PET);
		}
		if (battle.getCurrentTurnPet() == null) {
			battle.setCurrentTurnPet(EMPTY_PET);
		}
		return battle;
	}

	public Battle cleanOutWinnerAndLoser(Battle battle) {
		battle.setWinningPet(null);
		battle.setLosingPet(null);
		return battle;
	}
}