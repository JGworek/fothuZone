//package zone.fothu.pets.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import zone.fothu.pets.controller.WebSocketBrokerController;
//import zone.fothu.pets.model.adventure.Battle;
//import zone.fothu.pets.model.adventure.Turn;
//import zone.fothu.pets.model.profile.Pet;
//import zone.fothu.pets.repository.AttackingBattlePetRepository;
//import zone.fothu.pets.repository.BattleRepository;
//import zone.fothu.pets.repository.ChallengeRequestRepository;
//import zone.fothu.pets.repository.DefendingBattlePetRepository;
//import zone.fothu.pets.repository.PetRepository;
//import zone.fothu.pets.repository.TurnRepository;
//import zone.fothu.pets.repository.UserRepository;
//
//@Service
//public class NewBattleService {
//
//	private final BattleRepository battleRepository;
//	private final TurnRepository turnRepository;
//	private final ChallengeRequestRepository challengeRequestRepository;
//	private final UserRepository userRepository;
//	private final PetRepository petRepository;
//	private final AttackingBattlePetRepository attackingBattlePetRepository;
//	private final DefendingBattlePetRepository defendingBattlePetRepository;
//	private final WebSocketBrokerController webSocketBrokerController;
//
//	@Autowired
//	public Battle NewBattleService(BattleRepository battleRepository, TurnRepository turnRepository, ChallengeRequestRepository challengeRequestRepository, UserRepository userRepository, PetRepository petRepository, AttackingBattlePetRepository attackingBattlePetRepository, DefendingBattlePetRepository defendingBattlePetRepository, WebSocketBrokerController webSocketBrokerController) {
//		super();
//		this.battleRepository = battleRepository;
//		this.turnRepository = turnRepository;
//		this.challengeRequestRepository = challengeRequestRepository;
//		this.userRepository = userRepository;
//		this.petRepository = petRepository;
//		this.attackingBattlePetRepository = attackingBattlePetRepository;
//		this.defendingBattlePetRepository = defendingBattlePetRepository;
//		this.webSocketBrokerController = webSocketBrokerController;
//	}
//
//	private final double STARTING_ATTACK_MODIFIER = 1;
//	private final double STARTING_ARMOR_MODIFIER = 1;
//	private final double STARTING_ACCURACY_MODIFIER = 1;
//	private final double STARTING_EVASION_MODIFIER = 1;
//	private final double MODIFIER_INCREMENT = 0.5;
//	private final int MAX_TURN_COUNT = 100;
//	private final int XP_HEALTH_MODIFIER = 2;
//	private final int BST_DEFEND_PENALTY = 2;
//
//	public Battle doATurn(long actingPetId, long respondingPetId, long swappingPetId, long battleId, String actionType) {
//		Battle currentBattle = battleRepository.findById(battleId).get();
//		Turn lastTurn = turnRepository.getLastTurnForBattle(battleId);
//		Pet actingPet = getPetFromBattle(actingPetId, lastTurn);
//		Pet respondingPet = getPetFromBattle(respondingPetId, lastTurn);
//		// checkIfCorrectPetTurn()
//		String actionStarterLabel = getActionStarterLabel(actingPetId, lastTurn);
//		Turn newTurn;
//		int actingRoll = (int) (Math.random() * getPetBST(actingPet));
//		boolean success = false;
//		switch (actionType) {
//		case "attack":
//			success = checkActionCriteria(actingPet, respondingPet, currentBattle, lastTurn, actionType, actionStarterLabel, actingRoll);
//			newTurn = attack(actingPet, respondingPet, currentBattle, lastTurn, success, actionStarterLabel, actingRoll);
//			currentBattle = battleRepository.findById(battleId).get();
//			break;
//		case "defend":
//
//			break;
//		case "aim":
//
//			break;
//		case "sharpen":
//
//			break;
//		case "evade":
//
//			break;
//		case "swap":
//
//			break;
//		}
//		return currentBattle;
//	}
//
//	public Turn attack(Pet actingPet, Pet respondingPet, Battle currentBattle, Turn lastTurn, boolean success, String actionStarterLabel) {
//		if (success == true) {
//			boolean battleFinished = checkIfBattleEnded(currentBattle, lastTurn);
//			String flavorText = lastTurn.getAttackingPet().getName() + " became harder to hit!";
//			String technicalText = lastTurn.getAttackingPet().getName() + " has rolled a " + attackingPetRoll + ", successfully increasing their evasion modifier from " + currentEvasionModifier + " to " + (currentEvasionModifier + MODIFIER_INCREMENT) + ".";
//
//		} else {
//
//		}
//	}
//
//	public boolean checkActionCriteria(Pet actingPet, Pet respondingPet, Battle currentBattle, Turn lastTurn, String actionType, String actionStarterLabel, int actingRoll) {
//		switch (actionType) {
//		case "attack":
//			switch (actionStarterLabel) {
//			case "attacker":
//				if ((actingPet.getIntelligence() * lastTurn.getAttackingPetAccuracyModifier()) + (actingRoll) > ((getPetBST(respondingPet) / BST_DEFEND_PENALTY) + (respondingPet.getAgility() * lastTurn.getDefendingPetEvasionModifier()))) {
//					return true;
//				} else {
//					return false;
//				}
//			case "defender":
//				if ((actingPet.getIntelligence() * lastTurn.getDefendingPetAccuracyModifier()) + (actingRoll) > ((getPetBST(respondingPet) / BST_DEFEND_PENALTY) + (respondingPet.getAgility() * lastTurn.getAttackingPetEvasionModifier()))) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		case "defend":
//			if ((actingPet.getStrength() + actingRoll) > (getPetBST(respondingPet) - respondingPet.getIntelligence())) {
//				return true;
//			} else {
//				return false;
//			}
//		case "aim":
//			if ((actingPet.getIntelligence() + actingRoll) > (getPetBST(respondingPet) - respondingPet.getAgility())) {
//				return true;
//			} else {
//				return false;
//			}
//		case "sharpen":
//			if ((getBaseStatPower(actingPet) + actingRoll) > (getPetBST(respondingPet) - getBaseStatPower(respondingPet))) {
//				return true;
//			} else {
//				return false;
//			}
//		case "evade":
//			if ((actingPet.getAgility() + actingRoll) > (getPetBST(respondingPet) - respondingPet.getStrength())) {
//				return true;
//			} else {
//				return false;
//			}
//		default:
//			return false;
//		}
//	}
//
//	public int getPetBST(Pet pet) {
//		return pet.getAgility() + pet.getIntelligence() + pet.getStrength();
//	}
//
//	public Pet getPetFromBattle(long petId, Turn lastTurn) {
//		if (lastTurn.getAttackingPet().getId() == petId) {
//			return lastTurn.getAttackingPet();
//		} else {
//			return lastTurn.getDefendingPet();
//		}
//	}
//
//	public String getActionStarterLabel(long actingPetId, Turn lastTurn) {
//		if (lastTurn.getAttackingPet().getId() == actingPetId) {
//			return "attacker";
//		} else {
//			return "defender";
//		}
//	}
//
//	int getBaseStatPower(Pet currentPet) {
//		if (currentPet.getType().equalsIgnoreCase("strength")) {
//			return currentPet.getStrength();
//		} else if (currentPet.getType().equalsIgnoreCase("agility")) {
//			return currentPet.getAgility();
//		} else {
//			return currentPet.getIntelligence();
//		}
//	}
//}
