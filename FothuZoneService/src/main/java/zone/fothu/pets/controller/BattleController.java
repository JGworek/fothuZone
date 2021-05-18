package zone.fothu.pets.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zone.fothu.pets.exception.BattleNotFoundException;
import zone.fothu.pets.model.adventure.Battle;
import zone.fothu.pets.repository.BattleRepository;
import zone.fothu.pets.service.BattleService;
import zone.fothu.pets.service.RequestService;

@RestController
@CrossOrigin
@RequestMapping(path = "/battles")
public class BattleController implements Serializable {

	private static final long serialVersionUID = 7455066211780881774L;

	private final BattleService battleService;
	private final RequestService requestService;
	private final WebSocketBrokerController webSocketBrokerController;
	private final BattleRepository battleRepository;

	@Autowired
	public BattleController(@Lazy BattleService battleService, @Lazy RequestService requestService, WebSocketBrokerController webSocketBrokerController, BattleRepository battleRepository) {
		super();
		this.battleService = battleService;
		this.requestService = requestService;
		this.webSocketBrokerController = webSocketBrokerController;
		this.battleRepository = battleRepository;
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Battle> getBattleById(@PathVariable long id) throws BattleNotFoundException {
		Battle battle = battleService.getBattleById(id);
		return ResponseEntity.ok(battle);
	}

	@GetMapping("/all/pvp/current/userId/{userId}")
	ResponseEntity<List<Battle>> getAllPendingChallengeRequestsForUser(@PathVariable long userId) {
		return ResponseEntity.ok(requestService.getAllCurrentPVPBattlesForUser(userId));
	}

//	@PutMapping("/pve/prematureEnd/battleId/{battleId}")
//	ResponseEntity<Battle> prematureEndPveBattle(@PathVariable long battleId) {
//		return ResponseEntity.ok(battleService.prematureEndBattle(battleId, "pve"));
//	}

	@PostMapping("/new/userId/{userId}/defendingPetId/{defendingPetId}/pve")
	ResponseEntity<Battle> createNewPVEBattleWithDefendingPet(@PathVariable long userId, @PathVariable long defendingPetId) {
		return ResponseEntity.ok(battleService.createNewPVEBattleWithDefendingPet(userId, defendingPetId));
	}

	// /battles/pvp/battleId/{battleId}/attackingPetId/{attackingPetId}?requestedUserId={requestingUserId}
//	@PutMapping("/pve/battleId/{battleId}/attackingPetId/{attackingPetId}/")
//	ResponseEntity<Battle> setPVEAttackingPet(@PathVariable long battleId, @PathVariable long attackingPetId, @DestinationVariable long requestedUserId) {
//		try {
//			return ResponseEntity.ok(battleService.updateBattleWithAttackingPet(battleId, attackingPetId));
//		} catch (Exception e) {
//			webSocketBrokerController.sendErrorMessage(requestedUserId, "Failed to set attacking pet!");
//		}
//		return null;
//	}

	// /battles/pvp/battleId/{battleId}/attackingPetId/{attackingPetId}?requestedUserId={requestingUserId}
//	@PutMapping("/pvp/battleId/{battleId}/attackingPetId/{attackingPetId}/")
//	ResponseEntity<Battle> setPVPAttackingPet(@PathVariable long battleId, @PathVariable long attackingPetId, @DestinationVariable long requestedUserId) {
//		try {
//			return ResponseEntity.ok(battleService.updateBattleWithAttackingPet(battleId, attackingPetId));
//		} catch (Exception e) {
//			webSocketBrokerController.sendErrorMessage(requestedUserId, "Failed to set attacking pet!");
//		}
//		return null;
//	}

	// /battles/pvp/battleId{battleId}/defendingPetId/{defendingPetId}?requestedUserId={requestingUserId}
//	@PutMapping("/pvp/battleId/{battleId}/defendingPetId/{defendingPetId}")
//	ResponseEntity<Battle> setPVPDefendingPet(@PathVariable long battleId, @PathVariable long defendingPetId, @DestinationVariable long requestedUserId) {
//		try {
//			return ResponseEntity.ok(battleService.updateBattleWithDefendingPet(battleId, defendingPetId));
//		} catch (Exception e) {
//			webSocketBrokerController.sendErrorMessage(requestedUserId, "Failed to set defending pet!");
//		}
//		return null;
//	}
}