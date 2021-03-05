package zone.fothu.pets.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zone.fothu.pets.exception.BattleNotFoundException;
import zone.fothu.pets.model.adventure.Battle;
import zone.fothu.pets.repository.BattleDTORepository;
import zone.fothu.pets.repository.BattleRepository;
import zone.fothu.pets.service.BattleService;
import zone.fothu.pets.service.RequestService;

@RestController
@CrossOrigin
@RequestMapping(path = "/battles")
public class BattleController implements Serializable {

	private static final long serialVersionUID = 7455066211780881774L;

	@Autowired
	BattleService battleService;
	@Autowired
	RequestService requestService;
	@Autowired
	BattleDTORepository battleDTORepository;
	@Autowired
	WebSocketBrokerController webSocketBrokerController;
	@Autowired
	BattleRepository battleRepository;

	@GetMapping("/id/{id}")
	public ResponseEntity<Battle> getBattleById(@PathVariable int id) throws BattleNotFoundException {
		Battle battle = battleService.getBattleById(id);
		return ResponseEntity.ok(battle);
	}

	@GetMapping("/all/pvp/current/userId/{userId}")
	ResponseEntity<List<Battle>> getAllPendingChallengeRequestsForUser(@PathVariable int userId) {
		return ResponseEntity.ok(requestService.getAllCurrentPVPBattlesForUser(userId));
	}

	@PutMapping("/pve/prematureEnd/battleId/{battleId}")
	ResponseEntity<Battle> prematureEndPveBattle(@PathVariable int battleId) {
		return ResponseEntity.ok(battleService.prematureEndBattle(battleId, "pve"));
	}

	@PostMapping("/pve/createNewBattle/attackingPetId/{attackingPetId}/defendingPetId/{defendingPetId}")
	ResponseEntity<Battle> createNewPveBattle(@PathVariable int attackingPetId, @PathVariable int defendingPetId) {
		return ResponseEntity.ok(battleService.createNewBattleWithBothPets("pve", attackingPetId, defendingPetId));
	}

	@PostMapping("/pve/new/userId/{userId}/defendingPetId/{defendingPetId}")
	ResponseEntity<Battle> createNewPVEBattleWithDefendingPet(@PathVariable int userId, @PathVariable int defendingPetId) {
		return ResponseEntity.ok(battleService.createNewPVEBattleWithDefendingPet(userId, defendingPetId));
	}

	// /battles/pvp/battleId{battleId}/attackingPetId/{attackingPetId}?requestedUserId={requestingUserId}
	@PutMapping("/pve/battleId/{battleId}/attackingPetId/{attackingPetId}/")
	ResponseEntity<Battle> setPVEAttackingPet(@PathVariable int battleId, @PathVariable int attackingPetId, @DestinationVariable int requestedUserId) {
		try {
			return ResponseEntity.ok(battleService.updateBattleWithAttackingPet(battleId, attackingPetId));
		} catch (Exception e) {
			webSocketBrokerController.sendErrorMessage(requestedUserId, "Failed to set attacking pet!");
		}
		return null;
	}

	// /battles/pvp/battleId{battleId}/attackingPetId/{attackingPetId}?requestedUserId={requestingUserId}
	@PutMapping("/pvp/battleId/{battleId}/attackingPetId/{attackingPetId}/")
	ResponseEntity<Battle> setPVPAttackingPet(@PathVariable int battleId, @PathVariable int attackingPetId, @DestinationVariable int requestedUserId) {
		try {
			return ResponseEntity.ok(battleService.updateBattleWithAttackingPet(battleId, attackingPetId));
		} catch (Exception e) {
			webSocketBrokerController.sendErrorMessage(requestedUserId, "Failed to set attacking pet!");
		}
		return null;
	}

	// /battles/pvp/battleId{battleId}/defendingPetId/{defendingPetId}?requestedUserId={requestingUserId}
	@PutMapping("/pvp/battleId/{battleId}/defendingPetId/{defendingPetId}")
	ResponseEntity<Battle> setPVPDefendingPet(@PathVariable int battleId, @PathVariable int defendingPetId, @DestinationVariable int requestedUserId) {
		try {
			return ResponseEntity.ok(battleService.updateBattleWithDefendingPet(battleId, defendingPetId));
		} catch (Exception e) {
			webSocketBrokerController.sendErrorMessage(requestedUserId, "Failed to set defending pet!");
		}
		return null;
	}
}
