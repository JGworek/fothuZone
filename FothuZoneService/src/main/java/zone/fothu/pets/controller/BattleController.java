package zone.fothu.pets.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zone.fothu.pets.exception.BattleNotFoundException;
import zone.fothu.pets.model.adventure.Battle;
import zone.fothu.pets.model.adventure.BattleDTO;
import zone.fothu.pets.repository.BattleDTORepository;
import zone.fothu.pets.service.BattleService;

@RestController
@CrossOrigin
@RequestMapping(path = "/battles")
public class BattleController implements Serializable {

	private static final long serialVersionUID = 7455066211780881774L;

	@Autowired
	BattleService battleService;

	@Autowired
	BattleDTORepository battleDTORepository;

	@GetMapping("/id/{id}")
	public ResponseEntity<Battle> getBattleById(@PathVariable int id) throws BattleNotFoundException {
		Battle battle = battleService.getBattleById(id);

		return ResponseEntity.ok(battle);
	}

	@GetMapping("/all/pvp/userId/{userId}")
	ResponseEntity<List<BattleDTO>> getAllPendingChallengeRequestsForUser(@PathVariable int userId) {
		return ResponseEntity.ok(battleDTORepository.getAllCurrentPVPBattlesForUser(userId));
	}

	@PutMapping("/pve/prematureEnd/battleId/{battleId}")
	ResponseEntity<Battle> prematureEndPveBattle(@PathVariable int battleId) {
		return ResponseEntity.ok(battleService.prematureEndBattle(battleId, "pve"));
	}

	@PostMapping("/pve/createNewBattle/attackingPetId/{attackingPetId}/defendingPetId/{defendingPetId}")
	ResponseEntity<Battle> createNewPveBattle(@PathVariable int attackingPetId, @PathVariable int defendingPetId) {
		return ResponseEntity.ok(battleService.createNewBattleWithBothPets("pve", attackingPetId, defendingPetId));
	}

	@PutMapping("/pvp/battleId/{battleId}/attackingPetId/{attackingPetId}/")
	ResponseEntity<Battle> setAttackingPet(@PathVariable int battleId, @PathVariable int attackingPetId) {
		return ResponseEntity.ok(battleService.updateBattleWithAttackingPet(battleId, attackingPetId));
	}

	@PutMapping("/pvp/battleId/{battleId}/defendingPetId/{defendingPetId}")
	ResponseEntity<Battle> setDefendingPet(@PathVariable int battleId, @PathVariable int defendingPetId) {
		return ResponseEntity.ok(battleService.updateBattleWithDefendingPet(battleId, defendingPetId));
	}
}
