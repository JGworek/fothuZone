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

import zone.fothu.pets.model.adventure.ChallengeRequestDTO;
import zone.fothu.pets.repository.ChallengeRequestDTORepository;
import zone.fothu.pets.service.BattleService;

@RestController
@CrossOrigin
@RequestMapping(path = "/challengeRequests")
public class ChallengeRequestController implements Serializable {

	private static final long serialVersionUID = -1231910342596482588L;

	@Autowired
	BattleService battleService;
	@Autowired
	ChallengeRequestDTORepository challengeRequestDTORepository;

	@PostMapping("/new/challengerId/{attackerId}/opponentId/{defenderId}")
	ResponseEntity<ChallengeRequestDTO> createNewChallengeRequest(@PathVariable int attackerId, @PathVariable int defenderId) {
		return ResponseEntity.ok(battleService.createNewChallengeRequest(attackerId, defenderId));
	}

	@PutMapping("/accept/challengeRequestId/{challengeRequestId}")
	ResponseEntity<ChallengeRequestDTO> acceptChallengeRequest(@PathVariable int challengeRequestId) {
		return ResponseEntity.ok(battleService.acceptChallengeRequest(challengeRequestId));
	}

	@PutMapping("/reject/challengeRequestId/{challengeRequestId}")
	ResponseEntity<ChallengeRequestDTO> rejectChallengeRequest(@PathVariable int challengeRequestId) {
		return ResponseEntity.ok(battleService.rejectChallengeRequest(challengeRequestId));
	}

	@GetMapping("/all")
	ResponseEntity<List<ChallengeRequestDTO>> getAllChallengeRequests() {
		return ResponseEntity.ok(challengeRequestDTORepository.getAllChallengeRequests());
	}

	@GetMapping("/all/pending/userId/{userId}")
	ResponseEntity<List<ChallengeRequestDTO>> getAllPendingChallengeRequestsForUser(@PathVariable int userId) {
		return ResponseEntity.ok(challengeRequestDTORepository.getAllPendingChallengeRequestsForUser(userId));
	}

	@GetMapping("/id/{id}")
	ResponseEntity<ChallengeRequestDTO> getChallengeRequestWithId(@PathVariable int id) {
		return ResponseEntity.ok(challengeRequestDTORepository.getOne(id));
	}

	@GetMapping("/battleId/{battleId}")
	ResponseEntity<ChallengeRequestDTO> getChallengeRequestForBattle(@PathVariable int battleId) {
		return ResponseEntity.ok(challengeRequestDTORepository.getChallengeRequestWithBattleId(battleId));
	}
}
