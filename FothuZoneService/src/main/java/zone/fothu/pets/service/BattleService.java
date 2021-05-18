package zone.fothu.pets.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import zone.fothu.pets.controller.WebSocketBrokerController;
import zone.fothu.pets.exception.DeadBattlePetException;
import zone.fothu.pets.exception.NotAttackingUserException;
import zone.fothu.pets.exception.NotDefendingUserException;
import zone.fothu.pets.exception.PetAlreadySelectedException;
import zone.fothu.pets.exception.PetNotDeadException;
import zone.fothu.pets.exception.TooManyPetsSelectedForBattleException;
import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.exception.WrongBattlePetException;
import zone.fothu.pets.exception.WrongTurnException;
import zone.fothu.pets.model.adventure.AttackingBattlePet;
import zone.fothu.pets.model.adventure.Battle;
import zone.fothu.pets.model.adventure.ChallengeRequest;
import zone.fothu.pets.model.adventure.DefendingBattlePet;
import zone.fothu.pets.model.adventure.Turn;
import zone.fothu.pets.model.profile.Pet;
import zone.fothu.pets.model.profile.User;
import zone.fothu.pets.repository.AttackingBattlePetRepository;
import zone.fothu.pets.repository.BattleRepository;
import zone.fothu.pets.repository.ChallengeRequestRepository;
import zone.fothu.pets.repository.DefendingBattlePetRepository;
import zone.fothu.pets.repository.PetRepository;
import zone.fothu.pets.repository.TurnRepository;
import zone.fothu.pets.repository.UserRepository;
import zone.fothu.utility.BeanUtil;

@Service
public class BattleService implements Serializable {

	private static final long serialVersionUID = -7951721923780422911L;

	private final BattleRepository battleRepository;
	private final TurnRepository turnRepository;
	private final ChallengeRequestRepository challengeRequestRepository;
	private final UserRepository userRepository;
	private final PetRepository petRepository;
	private final AttackingBattlePetRepository attackingBattlePetRepository;
	private final DefendingBattlePetRepository defendingBattlePetRepository;
	private final WebSocketBrokerController webSocketBrokerController;

	@Autowired
	public BattleService(BattleRepository battleRepository, TurnRepository turnRepository, ChallengeRequestRepository challengeRequestRepository, UserRepository userRepository, PetRepository petRepository, AttackingBattlePetRepository attackingBattlePetRepository, DefendingBattlePetRepository defendingBattlePetRepository, WebSocketBrokerController webSocketBrokerController) {
		super();
		this.battleRepository = battleRepository;
		this.turnRepository = turnRepository;
		this.challengeRequestRepository = challengeRequestRepository;
		this.userRepository = userRepository;
		this.petRepository = petRepository;
		this.attackingBattlePetRepository = attackingBattlePetRepository;
		this.defendingBattlePetRepository = defendingBattlePetRepository;
		this.webSocketBrokerController = webSocketBrokerController;
	}

	private final double STARTING_ATTACK_MODIFIER = 1;
	private final double STARTING_ARMOR_MODIFIER = 1;
	private final double STARTING_ACCURACY_MODIFIER = 1;
	private final double STARTING_EVASION_MODIFIER = 1;
	private final double MODIFIER_INCREMENT = 0.5;
	private final int MAX_TURN_COUNT = 100;
	private final int XP_HEALTH_MODIFIER = 2;
	private final int BST_DEFEND_PENALTY = 2;

	// ROUND TO THE 4TH DECIMAL PLACE TO HANDLE REPEATING DECIMALS
	// ROUND ALL VALUES DOWN (DID NOT MEET THRESHOLD FOR THE NEXT VALUE)

	public ChallengeRequest createNewChallengeRequest(long attackerId, long defenderId, int numberOfPets) {
		challengeRequestRepository.save(BeanUtil.getBean(ChallengeRequest.class).setId(0).setAttackingUser(userRepository.findById(attackerId).get()).setDefendingUser(userRepository.findById(defenderId).get()).setMaxNumberOfAttackingPets(numberOfPets).setMaxNumberOfDefendingPets(numberOfPets).setCreatedOn(LocalDateTime.now()));
		webSocketBrokerController.updateChallengeSubscription(defenderId);

		return getChallengeRequestById(challengeRequestRepository.getMostRecentChallengeRequestId());
	}

	public ChallengeRequest acceptChallengeRequest(long challengeRequestId) {
		ChallengeRequest acceptedChallengeRequest = challengeRequestRepository.findById(challengeRequestId).get();
		Battle newBattle = battleRepository.save(BeanUtil.getBean(Battle.class).setId(0).setBattleType("pvp").setAttackingUser(acceptedChallengeRequest.getAttackingUser()).setDefendingUser(acceptedChallengeRequest.getDefendingUser()).setMaxNumberOfAttackingPets(acceptedChallengeRequest.getMaxNumberOfAttackingPets()).setMaxNumberOfDefendingPets(acceptedChallengeRequest.getMaxNumberOfDefendingPets()).setCreatedOn(LocalDateTime.now()));
		turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(newBattle).setTurnNumber(1).setBattleFinished(false).setCreatedOn(LocalDateTime.now()));
		challengeRequestRepository.save(acceptedChallengeRequest.setAcceptedStatus(true).setResultingBattle(newBattle));
		webSocketBrokerController.updateChallengeSubscription(acceptedChallengeRequest.getDefendingUser().getId());
		webSocketBrokerController.updateCurrentBattleSubscription(newBattle.getAttackingUser().getId(), newBattle.getDefendingUser().getId());
		return getChallengeRequestById(challengeRequestId);
	}

	public ChallengeRequest rejectChallengeRequest(long challengeRequestId) {
		ChallengeRequest rejectedChallengeRequest = challengeRequestRepository.findById(challengeRequestId).get();
		challengeRequestRepository.save(rejectedChallengeRequest.setRejectedStatus(true));
		webSocketBrokerController.updateChallengeSubscription(rejectedChallengeRequest.getDefendingUser().getId());
		return getChallengeRequestById(challengeRequestId);
	}

	public Battle createNewPVEBattleWithDefendingPet(long attackingUserId, long defendingPetId) {
		Pet defendingPet = petRepository.findById(defendingPetId).get();
		User attackingUser = userRepository.findById(attackingUserId).get();
		Battle newPVEBattle = battleRepository.save(BeanUtil.getBean(Battle.class).setId(0).setBattleType("pve").setAttackingUser(userRepository.findById(attackingUserId).get()).setDefendingUser(defendingPet.getOwner()).setMaxNumberOfAttackingPets(getNumberOfLivingPetsForUser(attackingUser)).setMaxNumberOfDefendingPets(1).setCreatedOn(LocalDateTime.now()));
		turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(newPVEBattle).setTurnNumber(1).setDefendingPet(defendingPet).setDefendingPetCurrentHealth(defendingPet.getMaxHealth()).setDefendingPetAttackModifier(STARTING_ATTACK_MODIFIER).setDefendingPetArmorModifier(STARTING_ARMOR_MODIFIER).setDefendingPetAccuracyModifier(STARTING_ACCURACY_MODIFIER).setDefendingPetEvasionModifier(STARTING_EVASION_MODIFIER).setCreatedOn(LocalDateTime.now()));
		return getBattleById(newPVEBattle.getId());
	}

	public int getNumberOfLivingPetsForUser(User user) {
		int numberOfLivingPets = 0;
		for (Pet pet : user.getPets()) {
			if (pet.getCurrentHealth() > 0) {
				++numberOfLivingPets;
			}
		}
		return numberOfLivingPets;
	}

	public Battle setStartingAttackingBattlePet(long battleId, long attackingPetId) throws WrongBattlePetException {
		AttackingBattlePet newBattlePet;
		Battle currentBattle = battleRepository.findById(battleId).get();
		Pet attackingPet = petRepository.findById(attackingPetId).get();
		Turn firstTurn = turnRepository.getLastTurnForBattle(battleId);
		if (isPetAlreadySelected(attackingPet, currentBattle)) {
			throw new PetAlreadySelectedException("That attacking pet has already been selected!");
		}
		if (currentBattle.getAttackingUser().getId() != attackingPet.getOwner().getId()) {
			throw new WrongBattlePetException("That attacking user cannot set that pet for battle!");
		}
		if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
			if (petRepository.findById(attackingPetId).get().getCurrentHealth() <= 0) {
				throw new DeadBattlePetException("That pet is dead and can't fight!");
			}
		}
		if (currentBattle.getAttackingBattlePets().size() >= currentBattle.getMaxNumberOfAttackingPets()) {
			throw new TooManyPetsSelectedForBattleException("That would be too many pets on that side!");
		}
		if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
			newBattlePet = BeanUtil.getBean(AttackingBattlePet.class).setId(0).setBattle(currentBattle).setPet(attackingPet).setCurrentHealth(petRepository.findById(attackingPetId).get().getCurrentHealth()).setAliveStatus(true);

		} else {
			newBattlePet = BeanUtil.getBean(AttackingBattlePet.class).setId(0).setBattle(currentBattle).setPet(attackingPet).setCurrentHealth(petRepository.findById(attackingPetId).get().getMaxHealth()).setAliveStatus(true);
		}
		attackingBattlePetRepository.save(newBattlePet);
		if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
			firstTurn = firstTurn.setAttackingPet(attackingPet).setAttackingPetAttackModifier(STARTING_ATTACK_MODIFIER).setAttackingPetArmorModifier(STARTING_ARMOR_MODIFIER).setAttackingPetAccuracyModifier(STARTING_ACCURACY_MODIFIER).setAttackingPetEvasionModifier(STARTING_EVASION_MODIFIER).setAttackingPetCurrentHealth(attackingPet.getCurrentHealth());
		} else {
			firstTurn = firstTurn.setAttackingPet(attackingPet).setAttackingPetAttackModifier(STARTING_ATTACK_MODIFIER).setAttackingPetArmorModifier(STARTING_ARMOR_MODIFIER).setAttackingPetAccuracyModifier(STARTING_ACCURACY_MODIFIER).setAttackingPetEvasionModifier(STARTING_EVASION_MODIFIER).setAttackingPetCurrentHealth(attackingPet.getMaxHealth());
		}
		turnRepository.save(firstTurn);
		Battle battle = battleRepository.findById(battleId).get();
		firstTurn = turnRepository.getLastTurnForBattle(battleId);
		if (firstTurn.getDefendingPet() != null & firstTurn.getAttackingPet() != null) {
			Pet firstPet = getFirstTurnPet(battle.getAttackingBattlePets().get(0).getPet(), battle.getDefendingBattlePets().get(0).getPet(), battle);
			battle = battleRepository.save(battle.setNextTurnUser(firstPet.getOwner()));
		}
		return getBattleById(battleId);
	}

	public Battle setStartingDefendingBattlePet(long battleId, long defendingPetId) throws WrongBattlePetException {
		DefendingBattlePet newBattlePet;
		Battle currentBattle = battleRepository.findById(battleId).get();
		Pet defendingPet = petRepository.findById(defendingPetId).get();
		Turn firstTurn = turnRepository.getLastTurnForBattle(battleId);
		if (isPetAlreadySelected(defendingPet, currentBattle)) {
			throw new PetAlreadySelectedException("That defending pet has already been selected!");
		}
		if (currentBattle.getDefendingUser().getId() != defendingPet.getOwner().getId()) {
			throw new WrongBattlePetException("That defending user cannot set that pet for battle!");
		}
		if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
			if (petRepository.findById(defendingPetId).get().getCurrentHealth() <= 0) {
				throw new DeadBattlePetException("That pet is dead and can't fight!");
			}
		}
		if (currentBattle.getDefendingBattlePets().size() >= currentBattle.getMaxNumberOfDefendingPets()) {
			throw new TooManyPetsSelectedForBattleException("That would be too many pets on that side!");
		}
		if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
			newBattlePet = BeanUtil.getBean(DefendingBattlePet.class).setId(0).setBattle(currentBattle).setPet(defendingPet).setCurrentHealth(petRepository.findById(defendingPetId).get().getCurrentHealth()).setAliveStatus(true);
		} else {
			newBattlePet = BeanUtil.getBean(DefendingBattlePet.class).setId(0).setBattle(currentBattle).setPet(defendingPet).setCurrentHealth(petRepository.findById(defendingPetId).get().getMaxHealth()).setAliveStatus(true);
		}
		defendingBattlePetRepository.save(newBattlePet);
		if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
			firstTurn = firstTurn.setDefendingPet(defendingPet).setDefendingPetAttackModifier(STARTING_ATTACK_MODIFIER).setDefendingPetArmorModifier(STARTING_ARMOR_MODIFIER).setDefendingPetAccuracyModifier(STARTING_ACCURACY_MODIFIER).setDefendingPetEvasionModifier(STARTING_EVASION_MODIFIER).setDefendingPetCurrentHealth(defendingPet.getCurrentHealth());
		} else {
			firstTurn = firstTurn.setDefendingPet(defendingPet).setDefendingPetAttackModifier(STARTING_ATTACK_MODIFIER).setDefendingPetArmorModifier(STARTING_ARMOR_MODIFIER).setDefendingPetAccuracyModifier(STARTING_ACCURACY_MODIFIER).setDefendingPetEvasionModifier(STARTING_EVASION_MODIFIER).setDefendingPetCurrentHealth(defendingPet.getMaxHealth());
		}
		turnRepository.save(firstTurn);
		Battle battle = battleRepository.findById(battleId).get();
		firstTurn = turnRepository.getLastTurnForBattle(battleId);
		if (firstTurn.getAttackingPet() != null & firstTurn.getDefendingPet() != null) {
			Pet firstPet = getFirstTurnPet(battle.getAttackingBattlePets().get(0).getPet(), battle.getDefendingBattlePets().get(0).getPet(), battle);
			battle = battleRepository.save(battle.setNextTurnUser(firstPet.getOwner()));
		}
		return getBattleById(battleId);
	}

	public Battle setAttackingBattlePet(long battleId, long attackingPetId) throws WrongBattlePetException {
		AttackingBattlePet newBattlePet;
		Battle currentBattle = battleRepository.findById(battleId).get();
		Pet attackingPet = petRepository.findById(attackingPetId).get();
		if (isPetAlreadySelected(attackingPet, currentBattle)) {
			throw new PetAlreadySelectedException("That attacking pet has already been selected!");
		}
		if (currentBattle.getAttackingUser().getId() != attackingPet.getId()) {
			throw new WrongBattlePetException("That attacking user cannot set that pet for battle!");
		}
		if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
			if (petRepository.findById(attackingPetId).get().getCurrentHealth() <= 0) {
				throw new DeadBattlePetException("That pet is dead and can't fight!");
			}
		}
		if (currentBattle.getAttackingBattlePets().size() >= currentBattle.getMaxNumberOfAttackingPets()) {
			throw new TooManyPetsSelectedForBattleException("That would be too many pets on that side!");
		}
		if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
			newBattlePet = BeanUtil.getBean(AttackingBattlePet.class).setId(0).setBattle(currentBattle).setPet(attackingPet).setCurrentHealth(petRepository.findById(attackingPetId).get().getCurrentHealth()).setAliveStatus(true);
		} else {
			newBattlePet = BeanUtil.getBean(AttackingBattlePet.class).setId(0).setBattle(currentBattle).setPet(attackingPet).setCurrentHealth(petRepository.findById(attackingPetId).get().getMaxHealth()).setAliveStatus(true);
		}
		attackingBattlePetRepository.save(newBattlePet);
		return getBattleById(battleId);
	}

	public Battle setDefendingBattlePet(long battleId, long defendingPetId) throws WrongBattlePetException {
		DefendingBattlePet newBattlePet;
		Battle currentBattle = battleRepository.findById(battleId).get();
		Pet defendingPet = petRepository.findById(defendingPetId).get();
		if (isPetAlreadySelected(defendingPet, currentBattle)) {
			throw new PetAlreadySelectedException("That defending pet has already been selected!");
		}
		if (currentBattle.getDefendingUser().getId() != defendingPet.getId()) {
			throw new WrongBattlePetException("That defending user cannot set that pet for battle!");
		}
		if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
			if (petRepository.findById(defendingPetId).get().getCurrentHealth() <= 0) {
				throw new DeadBattlePetException("That pet is dead and can't fight!");
			}
		}
		if (currentBattle.getDefendingBattlePets().size() >= currentBattle.getMaxNumberOfDefendingPets()) {
			throw new TooManyPetsSelectedForBattleException("That would be too many pets on that side!");
		}
		if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
			newBattlePet = BeanUtil.getBean(DefendingBattlePet.class).setId(0).setBattle(currentBattle).setPet(defendingPet).setCurrentHealth(petRepository.findById(defendingPetId).get().getCurrentHealth()).setAliveStatus(true);
		} else {
			newBattlePet = BeanUtil.getBean(DefendingBattlePet.class).setId(0).setBattle(currentBattle).setPet(defendingPet).setCurrentHealth(petRepository.findById(defendingPetId).get().getMaxHealth()).setAliveStatus(true);
		}
		defendingBattlePetRepository.save(newBattlePet);
		return getBattleById(battleId);
	}

	boolean isPetAlreadySelected(Pet pet, Battle currentBattle) {
		for (AttackingBattlePet battlePet : currentBattle.getAttackingBattlePets()) {
			if (battlePet.getPet().getId() == pet.getId()) {
				return true;
			}
		}
		for (DefendingBattlePet battlePet : currentBattle.getDefendingBattlePets()) {
			if (battlePet.getPet().getId() == pet.getId()) {
				return true;
			}
		}
		return false;
	}

	public Battle replaceDeadAttackingPet(long battleId, long deadAttackingPetId, long newAttackingPetId) throws WrongBattlePetException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Pet deadAttackingPet = petRepository.findById(deadAttackingPetId).get();
		Pet newAttackingPet = petRepository.findById(newAttackingPetId).get();
		AttackingBattlePet newAttackingBattlePet = attackingBattlePetRepository.getBattlePetByPetId(newAttackingPetId);
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		if (lastTurn.getAttackingPetCurrentHealth() > 0) {
			throw new PetNotDeadException(deadAttackingPet.getName() + " is not dead!");
		}
		if (!checkIfPetIsInBattleAndOnRightSideAndNotDead(currentBattle, newAttackingPet, currentBattle.getAttackingUser())) {
			throw new WrongBattlePetException(newAttackingPet.getName() + " cannot be set as the new attacking pet!");
		}
		int nextTurnNumber = lastTurn.getTurnNumber() + 1;
		turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(nextTurnNumber).setAttackingPet(newAttackingPet).setAttackingPetCurrentHealth(newAttackingBattlePet.getCurrentHealth()).setAttackingPetAttackModifier(STARTING_ATTACK_MODIFIER).setAttackingPetArmorModifier(STARTING_ARMOR_MODIFIER).setAttackingPetAccuracyModifier(STARTING_ACCURACY_MODIFIER).setAttackingPetEvasionModifier(STARTING_EVASION_MODIFIER).setDefendingPet(lastTurn.getDefendingPet())
				.setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(newAttackingPet.getOwner().getUsername() + " switched to " + newAttackingPet.getName())
				.setTurnTechnicalText(newAttackingPet.getOwner().getUsername() + " switched to " + newAttackingPet.getName()).setAttackerReplacedDeadPet(true).setDefenderReplacedDeadPet(false).setCreatedOn(LocalDateTime.now()));
		return getBattleById(battleId);
	}

	public Battle replaceDeadDefendingPet(long battleId, long deadDefendingPetId, long newDefendingPetId) throws WrongBattlePetException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Pet deadDefendingPet = petRepository.findById(deadDefendingPetId).get();
		Pet newDefendingPet = petRepository.findById(newDefendingPetId).get();
		DefendingBattlePet newDefendingBattlePet = defendingBattlePetRepository.getBattlePetByPetId(newDefendingPetId);
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		if (lastTurn.getDefendingPetCurrentHealth() > 0) {
			throw new PetNotDeadException(deadDefendingPet.getName() + " is not dead!");
		}
		if (!checkIfPetIsInBattleAndOnRightSideAndNotDead(currentBattle, newDefendingPet, currentBattle.getDefendingUser())) {
			throw new WrongBattlePetException(newDefendingPet.getName() + " cannot be set as the new attacking pet!");
		}
		int nextTurnNumber = lastTurn.getTurnNumber() + 1;
		turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(nextTurnNumber).setDefendingPet(newDefendingPet).setDefendingPetCurrentHealth(newDefendingBattlePet.getCurrentHealth()).setDefendingPetAttackModifier(STARTING_ATTACK_MODIFIER).setDefendingPetArmorModifier(STARTING_ARMOR_MODIFIER).setDefendingPetAccuracyModifier(STARTING_ACCURACY_MODIFIER).setDefendingPetEvasionModifier(STARTING_EVASION_MODIFIER).setAttackingPet(lastTurn.getAttackingPet())
				.setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier()).setTurnFlavorText(newDefendingPet.getOwner().getUsername() + " switched to " + newDefendingPet.getName())
				.setTurnTechnicalText(newDefendingPet.getOwner().getUsername() + " switched to " + newDefendingPet.getName()).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(true).setCreatedOn(LocalDateTime.now()));
		return getBattleById(battleId);

	}

	public Battle swapAttackingPet(long battleId, long attackingPetId) throws WrongBattlePetException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Pet attackingPet = petRepository.findById(attackingPetId).get();
		AttackingBattlePet newAttackingBattlePet = attackingBattlePetRepository.getBattlePetByPetId(attackingPetId);
		if (currentBattle.getAttackingUser().getId() != attackingPet.getOwner().getId()) {
			throw new WrongBattlePetException("That attacking user cannot set that pet for battle!");
		}
		if (checkForDeadPet(currentBattle, attackingPet)) {
			throw new DeadBattlePetException("That attacking pet is dead!");
		}
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		int nextTurnNumber = lastTurn.getTurnNumber() + 1;
		Turn nextTurn = turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(nextTurnNumber).setAttackingPet(attackingPet).setAttackingPetCurrentHealth(newAttackingBattlePet.getCurrentHealth()).setAttackingPetAttackModifier(STARTING_ATTACK_MODIFIER).setAttackingPetArmorModifier(STARTING_ARMOR_MODIFIER).setAttackingPetAccuracyModifier(STARTING_ACCURACY_MODIFIER).setAttackingPetEvasionModifier(STARTING_EVASION_MODIFIER)
				.setDefendingPet(lastTurn.getDefendingPet()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier())
				.setTurnFlavorText(attackingPet.getOwner().getUsername() + " switched to " + attackingPet.getName()).setTurnTechnicalText(attackingPet.getOwner().getUsername() + " switched to " + attackingPet.getName()).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setCreatedOn(LocalDateTime.now()));
		return checkIfOverMaxTurnCount(currentBattle, nextTurn);
	}

	public Battle swapDefendingPet(long battleId, long defendingPetId) throws WrongBattlePetException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Pet defendingPet = petRepository.findById(defendingPetId).get();
		DefendingBattlePet newDefendingBattlePet = defendingBattlePetRepository.getBattlePetByPetId(defendingPetId);
		if (currentBattle.getDefendingUser().getId() != petRepository.findById(defendingPetId).get().getOwner().getId()) {
			throw new WrongBattlePetException("That defending user cannot set that pet for battle!");
		}
		if (checkForDeadPet(currentBattle, defendingPet)) {
			throw new DeadBattlePetException("That defending pet is dead!");
		}
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		int nextTurnNumber = lastTurn.getTurnNumber() + 1;
		Turn nextTurn = turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(nextTurnNumber).setDefendingPet(defendingPet).setDefendingPetCurrentHealth(newDefendingBattlePet.getCurrentHealth()).setDefendingPetAttackModifier(STARTING_ATTACK_MODIFIER).setDefendingPetArmorModifier(STARTING_ARMOR_MODIFIER).setDefendingPetAccuracyModifier(STARTING_ACCURACY_MODIFIER).setDefendingPetEvasionModifier(STARTING_EVASION_MODIFIER)
				.setAttackingPet(lastTurn.getAttackingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
				.setTurnFlavorText(defendingPet.getOwner().getUsername() + " switched to " + defendingPet.getName()).setTurnTechnicalText(defendingPet.getOwner().getUsername() + " switched to " + defendingPet.getName()).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setCreatedOn(LocalDateTime.now()));
		return checkIfOverMaxTurnCount(currentBattle, nextTurn);
	}

	public boolean checkForDeadPet(Battle battle, Pet pet) {
		for (Turn turn : battle.getTurns()) {
			if (turn.getAttackingPet().getId() == pet.getId() && turn.getAttackingPetCurrentHealth() == 0) {
				return true;
			}
			if (turn.getDefendingPet().getId() == pet.getId() && turn.getDefendingPetCurrentHealth() == 0) {
				return true;
			}
		}
		return false;
	}

	public int checkBattleTypeForHealth(Battle battle, Pet pet) {
		if (battle.getBattleType().equalsIgnoreCase("pve")) {
			return petRepository.findById(pet.getId()).get().getCurrentHealth();
		} else {
			return petRepository.findById(pet.getId()).get().getMaxHealth();
		}
	}

	public boolean checkForBothPetsSet(Turn startingTurn) {
		if (startingTurn.getAttackingPet() != null && startingTurn.getDefendingPet() != null) {
			return true;
		} else {
			return false;
		}
	}

	public Battle checkIfOverMaxTurnCount(Battle battle, Turn turn) {
		if (turn.getTurnNumber() > MAX_TURN_COUNT) {
			turnRepository.save(turn.setBattleFinished(true));
			battleRepository.save(battle.setBattleFinished(true).setWinningUser(battle.getDefendingUser()).setLosingUser(battle.getAttackingUser()));
		}
		return getBattleById(battle.getId());
	}

	int getBaseStatPower(Pet currentPet) {
		if (currentPet.getType().equalsIgnoreCase("strength")) {
			return currentPet.getStrength();
		} else if (currentPet.getType().equalsIgnoreCase("agility")) {
			return currentPet.getAgility();
		} else {
			return currentPet.getIntelligence();
		}
	}

	double getSpeed(Pet currentPet) {
		return BigDecimal.valueOf(((double) currentPet.getAgility() + (double) currentPet.getIntelligence()) / (double) currentPet.getStrength()).setScale(4, RoundingMode.DOWN).doubleValue();
	}

	Pet getFirstTurnPet(Pet attackingPet, Pet defendingPet, Battle battle) {
		Turn firstTurn = turnRepository.getLastTurnForBattle(battle.getId());
		if (getSpeed(attackingPet) == getSpeed(defendingPet)) {
			if (attackingPet.getAgility() == defendingPet.getAgility()) {
				List<Pet> bothPets = new ArrayList<Pet>();
				bothPets.add(attackingPet);
				bothPets.add(defendingPet);
				Random random = new Random();
				Pet firstTurnPet = bothPets.get(random.nextInt(bothPets.size() - 1));
				if (firstTurnPet.getOwner().getId() == battle.getAttackingUser().getId()) {
					turnRepository.save(firstTurn.setAttackerReplacedDeadPet(true));
				} else {
					turnRepository.save(firstTurn.setDefenderReplacedDeadPet(true));
				}
				return firstTurnPet;
			} else if (attackingPet.getAgility() > defendingPet.getAgility()) {
				turnRepository.save(firstTurn.setAttackerReplacedDeadPet(true));
				return attackingPet;
			} else {
				turnRepository.save(firstTurn.setDefenderReplacedDeadPet(true));
				return defendingPet;
			}
		} else if (getSpeed(attackingPet) > getSpeed(defendingPet)) {
			turnRepository.save(firstTurn.setAttackerReplacedDeadPet(true));
			return attackingPet;
		} else {
			turnRepository.save(firstTurn.setDefenderReplacedDeadPet(true));
			return defendingPet;
		}
	}

	public void checkCurrentTurnUser(Battle currentBattle, long userId) {
		if (currentBattle.getNextTurnUser().getId() != userId) {
			throw new WrongTurnException("It's not " + userRepository.findById(userId).get().getUsername() + "'s turn!");
		}
	}

	public void checkAttackingUser(Battle currentBattle, long userId) {
		if (currentBattle.getAttackingUser().getId() != userId) {
			throw new NotAttackingUserException(userRepository.findById(userId).get().getUsername() + " is not the attacking user in this battle!");
		}
	}

	public void checkDefendingUser(Battle currentBattle, long userId) {
		if (currentBattle.getDefendingUser().getId() != userId) {
			throw new NotDefendingUserException(userRepository.findById(userId).get().getUsername() + " is not the defending user in this battle!");
		}
	}

	public boolean checkIfPetIsInBattleAndOnRightSideAndNotDead(Battle battle, Pet pet, User user) {
		List<Pet> petsInBattle = new ArrayList<Pet>();
		for (AttackingBattlePet battlePet : battle.getAttackingBattlePets()) {
			petsInBattle.add(battlePet.getPet());
		}
		for (DefendingBattlePet battlePet : battle.getDefendingBattlePets()) {
			petsInBattle.add(battlePet.getPet());
		}
		if (!petsInBattle.contains(pet)) {
			return false;
		}
		if (battle.getAttackingUser().getId() == user.getId()) {
			for (AttackingBattlePet attackingPet : battle.getAttackingBattlePets()) {
				if (attackingPet.getPet().getOwner().getId() != user.getId()) {
					return false;
				}
				if (attackingPet.getPet().getId() == pet.getId()) {
					if (attackingPet.getCurrentHealth() == 0) {
						return false;
					}
				}
			}
		} else {
			for (DefendingBattlePet defendingPet : battle.getDefendingBattlePets()) {
				if (defendingPet.getPet().getOwner().getId() != user.getId()) {
					return false;
				}
				if (defendingPet.getPet().getId() == pet.getId()) {
					if (defendingPet.getCurrentHealth() == 0) {
						return false;
					}
				}
			}
		}
		return true;
	}

	int getRemainingHealthForAttackingBattlePets(List<AttackingBattlePet> attackingBattlePets) {
		int remainingHealth = 0;
		for (AttackingBattlePet attackingBattlePet : attackingBattlePets) {
			remainingHealth += attackingBattlePet.getCurrentHealth();
		}
		return remainingHealth;
	}

	int getRemainingHealthForDefendingBattlePets(List<DefendingBattlePet> defendingBattlePets) {
		int remainingHealth = 0;
		for (DefendingBattlePet defendingBattlePet : defendingBattlePets) {
			remainingHealth += defendingBattlePet.getCurrentHealth();
		}
		return remainingHealth;
	}

	private boolean checkIfBattleEnded(Battle currentBattle, Turn lastTurn) {
		if ((lastTurn.getTurnNumber()) > MAX_TURN_COUNT + 1) {
			currentBattle.setBattleFinished(true);
			if (currentBattle.getBattleType().equalsIgnoreCase("pve")) {
				currentBattle.setWinningUser(currentBattle.getDefendingUser());
				currentBattle.setLosingUser(currentBattle.getAttackingUser());
				return true;
			} else {
				if (getRemainingHealthForAttackingBattlePets(currentBattle.getAttackingBattlePets()) > getRemainingHealthForDefendingBattlePets(currentBattle.getDefendingBattlePets())) {
					currentBattle.setWinningUser(currentBattle.getAttackingUser());
					currentBattle.setLosingUser(currentBattle.getDefendingUser());
					return true;
				} else {
					currentBattle.setWinningUser(currentBattle.getDefendingUser());
					currentBattle.setLosingUser(currentBattle.getAttackingUser());
					return true;
				}
			}
		}
		if (getRemainingHealthForDefendingBattlePets(currentBattle.getDefendingBattlePets()) == 0) {
			currentBattle.setWinningUser(currentBattle.getAttackingUser());
			currentBattle.setLosingUser(currentBattle.getDefendingUser());
			return true;
		} else if (getRemainingHealthForAttackingBattlePets(currentBattle.getAttackingBattlePets()) == 0) {
			currentBattle.setWinningUser(currentBattle.getDefendingUser());
			currentBattle.setLosingUser(currentBattle.getAttackingUser());
			return true;
		}
		return false;
	}

	public void giveParticipatingWinningPetsXP(long winningUserId, long losingUserId, Battle finishedBattle) throws MessagingException, UserNotFoundException {
		Set<Pet> winningPets = new HashSet<Pet>();
		List<Pet> deadPets = new ArrayList<Pet>();
		Set<Pet> losingPets = new HashSet<Pet>();
		for (Turn turn : finishedBattle.getTurns()) {
			if (turn.getAttackingPet().getOwner().getId() == winningUserId) {
				if (turn.getAttackingPetCurrentHealth() == 0) {
					deadPets.add(turn.getAttackingPet());
				}
				if (turn.getDefendingPetCurrentHealth() == 0) {
					deadPets.add(turn.getDefendingPet());
				}
				winningPets.add(turn.getAttackingPet());
				losingPets.add(turn.getDefendingPet());
			} else {
				if (turn.getAttackingPetCurrentHealth() == 0) {
					deadPets.add(turn.getAttackingPet());
				}
				if (turn.getDefendingPetCurrentHealth() == 0) {
					deadPets.add(turn.getDefendingPet());
				}
				winningPets.add(turn.getDefendingPet());
				losingPets.add(turn.getAttackingPet());
			}
		}
		for (Pet pet : winningPets) {
			if (checkForDeadPets(pet, deadPets)) {
				winningPets.remove(pet);
			}
		}
		int totalLosingPetLife = 0;
		for (Pet losingPet : losingPets) {
			totalLosingPetLife += losingPet.getMaxHealth();
		}
		for (Pet winningPet : winningPets) {
			giveWinningPetXP(winningPet.getId(), totalLosingPetLife / winningPets.size());
		}
	}

	public boolean checkForDeadPets(Pet pet, List<Pet> deadPets) {
		for (Pet deadPet : deadPets) {
			if (deadPet.getId() == pet.getId()) {
				return true;
			}
		}
		return false;
	}

	public void giveWinningPetXP(long winningPetId, int totalLosingPetLife) throws MessagingException, UserNotFoundException {
		Pet winningPet = petRepository.findById(winningPetId).get();
		if (winningPet.getPetLevel() < 100) {
			int winningXP = (totalLosingPetLife * XP_HEALTH_MODIFIER);
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

	public int getRandomNumberMinusEnemyStat(int enemyStat) {
		int randomModifiedRoll = (int) (Math.random() * (100 - enemyStat));
		return randomModifiedRoll;

	}

	public Battle attackingPetAttack(long battleId, long attackingUserId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		boolean battleFinished = false;
		int attackingPetBST = lastTurn.getAttackingPet().getStrength() + lastTurn.getAttackingPet().getAgility() + lastTurn.getAttackingPet().getIntelligence();
		int defendingPetBST = lastTurn.getDefendingPet().getStrength() + lastTurn.getDefendingPet().getAgility() + lastTurn.getDefendingPet().getIntelligence();
		int attackingPetRoll = (int) (Math.random() * attackingPetBST);
		checkCurrentTurnUser(currentBattle, attackingUserId);
		checkAttackingUser(currentBattle, attackingUserId);
		if ((lastTurn.getAttackingPet().getIntelligence() * lastTurn.getAttackingPetAccuracyModifier()) + (attackingPetRoll) > ((defendingPetBST / BST_DEFEND_PENALTY) + (lastTurn.getDefendingPet().getAgility() * lastTurn.getDefendingPetEvasionModifier()))) {
			int defendingPetStartingHealth = lastTurn.getDefendingPetCurrentHealth();
			int defendingPetRemainingHealth = (int) ((defendingPetStartingHealth) - ((getBaseStatPower(lastTurn.getAttackingPet()) * lastTurn.getAttackingPetAttackModifier()) - (lastTurn.getDefendingPet().getStrength() * lastTurn.getDefendingPetArmorModifier())));
			int totalDealtDamage = defendingPetStartingHealth - defendingPetRemainingHealth;
			if (totalDealtDamage < 1) {
				totalDealtDamage = 1;
				defendingPetRemainingHealth = defendingPetStartingHealth - 1;
			}
			DefendingBattlePet defendingBattlePet = defendingBattlePetRepository.getBattlePetWithBattleIdAndPetId(battleId, lastTurn.getDefendingPet().getId());
			if (defendingPetRemainingHealth <= 0) {
				defendingPetRemainingHealth = 0;
				defendingBattlePet.setAliveStatus(false);
			}
			defendingBattlePetRepository.save(defendingBattlePet.setCurrentHealth(defendingPetRemainingHealth));
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			if (getRemainingHealthForDefendingBattlePets(currentBattle.getDefendingBattlePets()) <= 0) {
				battleFinished = true;
			}
			String flavorText = lastTurn.getAttackingPet().getName() + " attacked and dealt " + totalDealtDamage + " damage!";
			String technicalText = lastTurn.getAttackingPet().getName() + " has rolled a " + attackingPetRoll + ", successfully dealing " + totalDealtDamage + ", reducing " + lastTurn.getDefendingPet().getName() + "'s health from " + defendingPetStartingHealth + " to " + defendingPetRemainingHealth + ".";
			if (lastTurn.isAttackerReplacedDeadPet()) {
				turnRepository.save(lastTurn.setDefendingPetCurrentHealth(defendingPetRemainingHealth).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(lastTurn.getTurnNumber() + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(defendingPetRemainingHealth).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getDefendingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		} else {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getAttackingPet().getName() + " missed!";
			String technicalText = lastTurn.getAttackingPet().getName() + " has rolled a " + attackingPetRoll + ", failing to deal damage to " + lastTurn.getDefendingPet().getName() + ".";
			if (lastTurn.isAttackerReplacedDeadPet()) {
				turnRepository.save(lastTurn.setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(lastTurn.getTurnNumber() + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getDefendingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		}
	}

	public Battle defendingPetAttack(long battleId, long defendingUserId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		boolean battleFinished = false;
		int defendingPetBST = lastTurn.getDefendingPet().getStrength() + lastTurn.getDefendingPet().getAgility() + lastTurn.getDefendingPet().getIntelligence();
		int attackingPetBST = lastTurn.getAttackingPet().getStrength() + lastTurn.getAttackingPet().getAgility() + lastTurn.getAttackingPet().getIntelligence();
		int attackingPetRoll = (int) (Math.random() * defendingPetBST);
		checkCurrentTurnUser(currentBattle, defendingUserId);
		checkDefendingUser(currentBattle, defendingUserId);
		if ((lastTurn.getDefendingPet().getIntelligence() * lastTurn.getDefendingPetAccuracyModifier()) + (attackingPetRoll) > ((attackingPetBST / BST_DEFEND_PENALTY) + (lastTurn.getAttackingPet().getAgility() * lastTurn.getAttackingPetEvasionModifier()))) {
			int attackingPetStartingHealth = lastTurn.getAttackingPetCurrentHealth();
			int attackingPetRemainingHealth = (int) ((attackingPetStartingHealth) - ((getBaseStatPower(lastTurn.getDefendingPet()) * lastTurn.getDefendingPetAttackModifier()) - (lastTurn.getAttackingPet().getStrength() * lastTurn.getAttackingPetArmorModifier())));
			int totalDealtDamage = attackingPetStartingHealth - attackingPetRemainingHealth;
			if (totalDealtDamage < 1) {
				totalDealtDamage = 1;
				attackingPetRemainingHealth = attackingPetStartingHealth - 1;
			}
			AttackingBattlePet attackingBattlePet = attackingBattlePetRepository.getBattlePetWithBattleIdAndPetId(battleId, lastTurn.getAttackingPet().getId());
			if (attackingPetRemainingHealth <= 0) {
				attackingPetRemainingHealth = 0;
				attackingBattlePet.setAliveStatus(false);
			}
			attackingBattlePetRepository.save(attackingBattlePet.setCurrentHealth(attackingPetRemainingHealth));
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			if (getRemainingHealthForAttackingBattlePets(currentBattle.getAttackingBattlePets()) <= 0) {
				battleFinished = true;
			}
			String flavorText = lastTurn.getDefendingPet().getName() + " attacked and dealt " + totalDealtDamage + " damage!";
			String technicalText = lastTurn.getDefendingPet().getName() + " has rolled a " + attackingPetRoll + ", successfully dealing " + totalDealtDamage + ", reducing " + lastTurn.getAttackingPet().getName() + "'s health from " + attackingPetStartingHealth + " to " + attackingPetRemainingHealth + ".";
			if (lastTurn.isDefenderReplacedDeadPet()) {
				turnRepository.save(lastTurn.setAttackingPetCurrentHealth(attackingPetRemainingHealth).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(lastTurn.getTurnNumber() + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(attackingPetRemainingHealth).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getAttackingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		} else {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getDefendingPet().getName() + " missed!";
			String technicalText = lastTurn.getDefendingPet().getName() + " has rolled a " + attackingPetRoll + ", failing to deal damage to " + lastTurn.getAttackingPet().getName() + ".";
			if (lastTurn.isDefenderReplacedDeadPet()) {
				turnRepository.save(lastTurn.setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(lastTurn.getTurnNumber() + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getAttackingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		}
	}

	public Battle attackingPetDefend(long battleId, long defendingUserId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		checkCurrentTurnUser(currentBattle, defendingUserId);
		checkAttackingUser(currentBattle, defendingUserId);
		int currentTurnCount = lastTurn.getTurnNumber();
		boolean battleFinished = false;
		int attackingPetBST = lastTurn.getAttackingPet().getStrength() + lastTurn.getAttackingPet().getAgility() + lastTurn.getAttackingPet().getIntelligence();
		int defendingPetBST = lastTurn.getDefendingPet().getStrength() + lastTurn.getDefendingPet().getAgility() + lastTurn.getDefendingPet().getIntelligence();
		int attackingPetRoll = (int) (Math.random() * attackingPetBST);
		double currentArmorModifier = lastTurn.getAttackingPetArmorModifier();
		if ((lastTurn.getAttackingPet().getStrength() + attackingPetRoll) > (defendingPetBST - lastTurn.getDefendingPet().getIntelligence())) {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getAttackingPet().getName() + " armored up!";
			String technicalText = lastTurn.getAttackingPet().getName() + " has rolled a " + attackingPetRoll + ", successfully increasing their armor modifier from " + currentArmorModifier + " to " + (currentArmorModifier + MODIFIER_INCREMENT) + ".";
			if (lastTurn.isAttackerReplacedDeadPet()) {
				turnRepository.save(lastTurn.setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier() + MODIFIER_INCREMENT).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier() + MODIFIER_INCREMENT).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getDefendingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		} else {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getAttackingPet().getName() + " failed to armor up!";
			String technicalText = lastTurn.getAttackingPet().getName() + " has rolled a " + attackingPetRoll + ", failing to increase their armor modifier.";
			if (lastTurn.isAttackerReplacedDeadPet()) {
				turnRepository.save(lastTurn.setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getDefendingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		}
	}

	public Battle defendingPetDefend(long battleId, long defendingUserId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		checkCurrentTurnUser(currentBattle, defendingUserId);
		checkDefendingUser(currentBattle, defendingUserId);
		int currentTurnCount = lastTurn.getTurnNumber();
		boolean battleFinished = false;
		int defendingPetBST = lastTurn.getDefendingPet().getStrength() + lastTurn.getDefendingPet().getAgility() + lastTurn.getDefendingPet().getIntelligence();
		int attackingPetBST = lastTurn.getAttackingPet().getStrength() + lastTurn.getAttackingPet().getAgility() + lastTurn.getAttackingPet().getIntelligence();
		int defendingPetRoll = (int) (Math.random() * defendingPetBST);
		double currentArmorModifier = lastTurn.getDefendingPetArmorModifier();
		if ((lastTurn.getDefendingPet().getStrength() + defendingPetRoll) > (attackingPetBST - lastTurn.getAttackingPet().getIntelligence())) {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getDefendingPet().getName() + " armored up!";
			String technicalText = lastTurn.getDefendingPet().getName() + " has rolled a " + defendingPetRoll + ", successfully increasing their armor modifier from " + currentArmorModifier + " to " + (currentArmorModifier + MODIFIER_INCREMENT) + ".";
			if (lastTurn.isDefenderReplacedDeadPet()) {
				turnRepository.save(lastTurn.setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier() + MODIFIER_INCREMENT).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getAttackingPetArmorModifier() + MODIFIER_INCREMENT).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getAttackingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		} else {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getDefendingPet().getName() + " failed to armor up!";
			String technicalText = lastTurn.getDefendingPet().getName() + " has rolled a " + defendingPetRoll + ", failing to increase their armor modifier.";
			if (lastTurn.isDefenderReplacedDeadPet()) {
				turnRepository.save(lastTurn.setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getAttackingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		}
	}

	public Battle attackingPetAim(long battleId, long aimingUserId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		checkCurrentTurnUser(currentBattle, aimingUserId);
		checkAttackingUser(currentBattle, aimingUserId);
		int currentTurnCount = lastTurn.getTurnNumber();
		boolean battleFinished = false;
		int attackingPetBST = lastTurn.getAttackingPet().getStrength() + lastTurn.getAttackingPet().getAgility() + lastTurn.getAttackingPet().getIntelligence();
		int defendingPetBST = lastTurn.getDefendingPet().getStrength() + lastTurn.getDefendingPet().getAgility() + lastTurn.getDefendingPet().getIntelligence();
		int attackingPetRoll = (int) (Math.random() * attackingPetBST);
		double currentAccuracyModifier = lastTurn.getAttackingPetAccuracyModifier();
		if ((lastTurn.getAttackingPet().getIntelligence() + attackingPetRoll) > (defendingPetBST - lastTurn.getDefendingPet().getAgility())) {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getAttackingPet().getName() + " took aim!";
			String technicalText = lastTurn.getAttackingPet().getName() + " has rolled a " + attackingPetRoll + ", successfully increasing their accuracy modifier from " + currentAccuracyModifier + " to " + (currentAccuracyModifier + MODIFIER_INCREMENT) + ".";
			if (lastTurn.isAttackerReplacedDeadPet()) {
				turnRepository.save(lastTurn.setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier() + MODIFIER_INCREMENT).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier() + MODIFIER_INCREMENT).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));

			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getDefendingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		} else {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getAttackingPet().getName() + " failed to take aim!";
			String technicalText = lastTurn.getAttackingPet().getName() + " has rolled a " + attackingPetRoll + ", failing to increase their accuracy modifier.";
			if (lastTurn.isAttackerReplacedDeadPet()) {
				turnRepository.save(lastTurn.setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getDefendingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		}
	}

	public Battle defendingPetAim(long battleId, long aimingUserId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		checkCurrentTurnUser(currentBattle, aimingUserId);
		checkDefendingUser(currentBattle, aimingUserId);
		int currentTurnCount = lastTurn.getTurnNumber();
		boolean battleFinished = false;
		int defendingPetBST = lastTurn.getDefendingPet().getStrength() + lastTurn.getDefendingPet().getAgility() + lastTurn.getDefendingPet().getIntelligence();
		int attackingPetBST = lastTurn.getAttackingPet().getStrength() + lastTurn.getAttackingPet().getAgility() + lastTurn.getAttackingPet().getIntelligence();
		int defendingPetRoll = (int) (Math.random() * defendingPetBST);
		double currentAccuracyModifier = lastTurn.getDefendingPetAccuracyModifier();
		if ((lastTurn.getDefendingPet().getIntelligence() + defendingPetRoll) > (attackingPetBST - lastTurn.getAttackingPet().getAgility())) {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getDefendingPet().getName() + " took aim!";
			String technicalText = lastTurn.getDefendingPet().getName() + " has rolled a " + defendingPetRoll + ", successfully increasing their accuracy modifier from " + currentAccuracyModifier + " to " + (currentAccuracyModifier + MODIFIER_INCREMENT) + ".";
			if (lastTurn.isDefenderReplacedDeadPet()) {
				turnRepository.save(lastTurn.setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier() + MODIFIER_INCREMENT).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier() + MODIFIER_INCREMENT).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getAttackingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		} else {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getDefendingPet().getName() + " failed to take aim!";
			String technicalText = lastTurn.getDefendingPet().getName() + " has rolled a " + defendingPetRoll + ", failing to increase their acuracy modifier.";
			if (lastTurn.isDefenderReplacedDeadPet()) {
				turnRepository.save(lastTurn.setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getAttackingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		}
	}

	public Battle attackingPetSharpen(long battleId, long sharpeningUserId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		checkCurrentTurnUser(currentBattle, sharpeningUserId);
		checkAttackingUser(currentBattle, sharpeningUserId);
		int currentTurnCount = lastTurn.getTurnNumber();
		boolean battleFinished = false;
		int attackingPetBST = lastTurn.getAttackingPet().getStrength() + lastTurn.getAttackingPet().getAgility() + lastTurn.getAttackingPet().getIntelligence();
		int defendingPetBST = lastTurn.getDefendingPet().getStrength() + lastTurn.getDefendingPet().getAgility() + lastTurn.getDefendingPet().getIntelligence();
		int attackingPetRoll = (int) (Math.random() * attackingPetBST);
		double currentAttackModifier = lastTurn.getAttackingPetAttackModifier();
		if ((getBaseStatPower(lastTurn.getAttackingPet()) + attackingPetRoll) > (defendingPetBST - getBaseStatPower(lastTurn.getDefendingPet()))) {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getAttackingPet().getName() + " became more deadly!";
			String technicalText = lastTurn.getAttackingPet().getName() + " has rolled a " + attackingPetRoll + ", successfully increasing their attack modifier from " + currentAttackModifier + " to " + (currentAttackModifier + MODIFIER_INCREMENT) + ".";
			if (lastTurn.isAttackerReplacedDeadPet()) {
				turnRepository.save(lastTurn.setAttackingPetArmorModifier(lastTurn.getAttackingPetAttackModifier() + MODIFIER_INCREMENT).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier() + MODIFIER_INCREMENT)
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getDefendingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		} else {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getAttackingPet().getName() + " failed to become more deadly!";
			String technicalText = lastTurn.getAttackingPet().getName() + " has rolled a " + attackingPetRoll + ", failing to increase their attack modifier.";
			if (lastTurn.isAttackerReplacedDeadPet()) {
				turnRepository.save(lastTurn.setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getDefendingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		}
	}

	public Battle defendingPetSharpen(long battleId, long sharpeningUserId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		checkCurrentTurnUser(currentBattle, sharpeningUserId);
		checkDefendingUser(currentBattle, sharpeningUserId);
		int currentTurnCount = lastTurn.getTurnNumber();
		boolean battleFinished = false;
		int defendingPetBST = lastTurn.getDefendingPet().getStrength() + lastTurn.getDefendingPet().getAgility() + lastTurn.getDefendingPet().getIntelligence();
		int attackingPetBST = lastTurn.getAttackingPet().getStrength() + lastTurn.getAttackingPet().getAgility() + lastTurn.getAttackingPet().getIntelligence();
		int defendingPetRoll = (int) (Math.random() * defendingPetBST);
		double currentAttackModifier = lastTurn.getDefendingPetAttackModifier();
		if ((getBaseStatPower(lastTurn.getDefendingPet()) + defendingPetRoll) > (attackingPetBST - getBaseStatPower(lastTurn.getAttackingPet()))) {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getDefendingPet().getName() + " became more deadly!";
			String technicalText = lastTurn.getDefendingPet().getName() + " has rolled a " + defendingPetRoll + ", successfully increasing their attack modifier from " + currentAttackModifier + " to " + (currentAttackModifier + MODIFIER_INCREMENT) + ".";
			if (lastTurn.isDefenderReplacedDeadPet()) {
				turnRepository.save(lastTurn.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier() + MODIFIER_INCREMENT).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier() + MODIFIER_INCREMENT).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getAttackingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		} else {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getDefendingPet().getName() + " failed to become more deadly!";
			String technicalText = lastTurn.getDefendingPet().getName() + " has rolled a " + defendingPetRoll + ", failing to increase their attack modifier.";
			if (lastTurn.isDefenderReplacedDeadPet()) {
				turnRepository.save(lastTurn.setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getAttackingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		}
	}

	public Battle attackingPetEvade(long battleId, long evadingUserId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		checkCurrentTurnUser(currentBattle, evadingUserId);
		checkAttackingUser(currentBattle, evadingUserId);
		int currentTurnCount = lastTurn.getTurnNumber();
		boolean battleFinished = false;
		int attackingPetBST = lastTurn.getAttackingPet().getStrength() + lastTurn.getAttackingPet().getAgility() + lastTurn.getAttackingPet().getIntelligence();
		int defendingPetBST = lastTurn.getDefendingPet().getStrength() + lastTurn.getDefendingPet().getAgility() + lastTurn.getDefendingPet().getIntelligence();
		int attackingPetRoll = (int) (Math.random() * attackingPetBST);
		double currentEvasionModifier = lastTurn.getAttackingPetEvasionModifier();
		if ((lastTurn.getAttackingPet().getAgility() + attackingPetRoll) > (defendingPetBST - lastTurn.getDefendingPet().getStrength())) {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getAttackingPet().getName() + " became harder to hit!";
			String technicalText = lastTurn.getAttackingPet().getName() + " has rolled a " + attackingPetRoll + ", successfully increasing their evasion modifier from " + currentEvasionModifier + " to " + (currentEvasionModifier + MODIFIER_INCREMENT) + ".";
			if (lastTurn.isAttackerReplacedDeadPet()) {
				turnRepository.save(lastTurn.setAttackingPetArmorModifier(lastTurn.getAttackingPetEvasionModifier() + MODIFIER_INCREMENT).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier() + MODIFIER_INCREMENT)
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getDefendingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		} else {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getAttackingPet().getName() + " failed to become harder to hit!";
			String technicalText = lastTurn.getAttackingPet().getName() + " has rolled a " + attackingPetRoll + ", failing to increase their evasion modifier.";
			if (lastTurn.isAttackerReplacedDeadPet()) {
				turnRepository.save(lastTurn.setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getDefendingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		}
	}

	public Battle defendingPetEvade(long battleId, long evadingUserId) throws MessagingException, UserNotFoundException {
		Battle currentBattle = battleRepository.findById(battleId).get();
		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
		checkCurrentTurnUser(currentBattle, evadingUserId);
		checkDefendingUser(currentBattle, evadingUserId);
		int currentTurnCount = lastTurn.getTurnNumber();
		boolean battleFinished = false;
		int defendingPetBST = lastTurn.getDefendingPet().getStrength() + lastTurn.getDefendingPet().getAgility() + lastTurn.getDefendingPet().getIntelligence();
		int attackingPetBST = lastTurn.getAttackingPet().getStrength() + lastTurn.getAttackingPet().getAgility() + lastTurn.getAttackingPet().getIntelligence();
		int defendingPetRoll = (int) (Math.random() * defendingPetBST);
		double currentEvasionModifier = lastTurn.getDefendingPetEvasionModifier();
		if ((lastTurn.getDefendingPet().getAgility() + defendingPetRoll) > (attackingPetBST - lastTurn.getAttackingPet().getStrength())) {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getDefendingPet().getName() + " became harder to hit!";
			String technicalText = lastTurn.getDefendingPet().getName() + " has rolled a " + defendingPetRoll + ", successfully increasing their evasion modifier from " + currentEvasionModifier + " to " + (currentEvasionModifier + MODIFIER_INCREMENT) + ".";
			if (lastTurn.isDefenderReplacedDeadPet()) {
				turnRepository.save(lastTurn.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier() + MODIFIER_INCREMENT).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier() + MODIFIER_INCREMENT).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getAttackingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		} else {
			battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
			String flavorText = lastTurn.getDefendingPet().getName() + " failed to become harder to hit!";
			String technicalText = lastTurn.getDefendingPet().getName() + " has rolled a " + defendingPetRoll + ", failing to increase their evasion modifier.";
			if (lastTurn.isDefenderReplacedDeadPet()) {
				turnRepository.save(lastTurn.setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished));
			} else {
				turnRepository.save(BeanUtil.getBean(Turn.class).setId(0).setBattle(currentBattle).setTurnNumber(currentTurnCount + 1).setAttackingPet(lastTurn.getAttackingPet()).setDefendingPet(lastTurn.getDefendingPet()).setAttackingPetCurrentHealth(lastTurn.getAttackingPetCurrentHealth()).setDefendingPetCurrentHealth(lastTurn.getDefendingPetCurrentHealth()).setAttackingPetAttackModifier(lastTurn.getAttackingPetAttackModifier())
						.setDefendingPetAttackModifier(lastTurn.getDefendingPetAttackModifier()).setAttackingPetArmorModifier(lastTurn.getAttackingPetArmorModifier()).setDefendingPetArmorModifier(lastTurn.getDefendingPetArmorModifier()).setAttackingPetAccuracyModifier(lastTurn.getAttackingPetAccuracyModifier()).setDefendingPetAccuracyModifier(lastTurn.getDefendingPetAccuracyModifier()).setAttackingPetEvasionModifier(lastTurn.getAttackingPetEvasionModifier())
						.setDefendingPetEvasionModifier(lastTurn.getDefendingPetEvasionModifier()).setTurnFlavorText(flavorText).setTurnTechnicalText(technicalText).setAttackerReplacedDeadPet(false).setDefenderReplacedDeadPet(false).setBattleFinished(battleFinished).setCreatedOn(LocalDateTime.now()));
			}
			battleRepository.save(currentBattle.setNextTurnUser(currentBattle.getAttackingUser()).setBattleFinished(battleFinished));
			System.out.println(flavorText);
			System.out.println(technicalText);
			return getBattleById(battleId);
		}
	}

	public Battle prematureEndBattle(long battleId, String battleType) {
		Battle endedBattle = battleRepository.findById(battleId).get();
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
		if (currentBattle.getTurns() != null) {
			for (Turn turn : currentBattle.getTurns()) {
				turn.setTurnTechnicalText(null);
				if (turn.getTurnFlavorText() == null) {
					turn.setTurnFlavorText("");
				}
			}
		}
		return cleanOutPasswords(currentBattle);
	}

	public Battle cleanOutPasswords(Battle battle) {
		if (battle.getTurns() != null) {
			for (Turn turn : battle.getTurns()) {
				if (turn.getAttackingPet() != null) {
					turn.getAttackingPet().getOwner().setUserPassword(null).setSecretPassword(null);
				}
				if (turn.getDefendingPet() != null) {
					turn.getDefendingPet().getOwner().setUserPassword(null).setSecretPassword(null);
				}
			}
		}
		if (battle.getAttackingBattlePets() != null) {
			if (battle.getAttackingBattlePets().size() != 0) {
				for (AttackingBattlePet battlePet : battle.getAttackingBattlePets()) {
					battlePet.getPet().getOwner().setUserPassword(null).setSecretPassword(null);
				}
			}
		}
		if (battle.getDefendingBattlePets() != null) {
			if (battle.getDefendingBattlePets().size() != 0) {
				for (DefendingBattlePet battlePet : battle.getDefendingBattlePets()) {
					battlePet.getPet().getOwner().setUserPassword(null).setSecretPassword(null);
				}
			}
		}
		if (battle.getAttackingUser() != null) {
			battle.getAttackingUser().setUserPassword(null).setSecretPassword(null);
		}
		if (battle.getDefendingUser() != null) {
			battle.getDefendingUser().setUserPassword(null).setSecretPassword(null);
		}
		if (battle.getWinningUser() != null) {
			battle.getWinningUser().setUserPassword(null).setSecretPassword(null);
		}
		return battle;
	}

	public Battle getBattleById(long id) {
		Battle battle = battleRepository.findById(id).get();
		if (battle.getWinningUser() == null) {
			battle.setWinningUser(BeanUtil.getBean(User.class).setId(0));
		}
		if (battle.getLosingUser() == null) {
			battle.setLosingUser(BeanUtil.getBean(User.class).setId(0));
		}
		if (battle.getNextTurnUser() == null) {
			battle.setNextTurnUser(BeanUtil.getBean(User.class).setId(0));
		}
		return cleanOutTechnicalText(battle);
	}

	public ChallengeRequest getChallengeRequestById(long id) {
		ChallengeRequest challengeRequest = challengeRequestRepository.findById(id).get();
		if (challengeRequest.getResultingBattle() != null) {
			challengeRequest.setResultingBattle(getBattleById(challengeRequest.getResultingBattle().getId()));
		}
		if (challengeRequest.getAttackingUser() != null) {
			challengeRequest.getAttackingUser().setUserPassword(null).setSecretPassword(null);
		}
		if (challengeRequest.getDefendingUser() != null) {
			challengeRequest.getDefendingUser().setUserPassword(null).setSecretPassword(null);
		}
		return challengeRequest;
	}
}