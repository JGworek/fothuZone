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

import zone.fothu.pets.model.adventure.ChallengeRequest;
import zone.fothu.pets.repository.ChallengeRequestRepository;
import zone.fothu.pets.service.BattleService;
import zone.fothu.pets.service.RequestService;

@RestController
@CrossOrigin
@RequestMapping(path = "/challengeRequests")
public class ChallengeRequestController implements Serializable {

	private static final long serialVersionUID = -1231910342596482588L;

	@Autowired
	BattleService battleService;
	@Autowired
	RequestService requestService;
	@Autowired
	ChallengeRequestRepository challengeRequestRepository;

	@PostMapping("/new/challengerId/{attackerId}/opponentId/{defenderId}")
	ResponseEntity<ChallengeRequest> createNewChallengeRequest(@PathVariable int attackerId, @PathVariable int defenderId) {
		return ResponseEntity.ok(battleService.createNewChallengeRequest(attackerId, defenderId));
	}

	@PutMapping("/accept/challengeRequestId/{challengeRequestId}")
	ResponseEntity<ChallengeRequest> acceptChallengeRequest(@PathVariable int challengeRequestId) {
		return ResponseEntity.ok(battleService.acceptChallengeRequest(challengeRequestId));
	}

	@PutMapping("/reject/challengeRequestId/{challengeRequestId}")
	ResponseEntity<ChallengeRequest> rejectChallengeRequest(@PathVariable int challengeRequestId) {
		return ResponseEntity.ok(battleService.rejectChallengeRequest(challengeRequestId));
	}

	@GetMapping("/all")
	ResponseEntity<List<ChallengeRequest>> getAllChallengeRequests() {
		List<ChallengeRequest> challengeRequests = challengeRequestRepository.findAll();
		for (ChallengeRequest challengeRequest : challengeRequests) {
			challengeRequest = requestService.cleanOutPasswords(challengeRequest);
		}
		return ResponseEntity.ok(challengeRequests);
	}

	@GetMapping("/all/pending/userId/{userId}")
	ResponseEntity<List<ChallengeRequest>> getAllPendingChallengeRequestsForUser(@PathVariable int userId) {
		return ResponseEntity.ok(requestService.getAllChallengeRequestsForUser(userId));
	}

	@GetMapping("/id/{id}")
	ResponseEntity<ChallengeRequest> getChallengeRequestWithId(@PathVariable int id) {
		return ResponseEntity.ok(requestService.getChallengeRequestById(id));
	}

	@GetMapping("/battleId/{battleId}")
	ResponseEntity<ChallengeRequest> getChallengeRequestForBattle(@PathVariable int battleId) {
		return ResponseEntity.ok(requestService.cleanOutPasswords(challengeRequestRepository.getChallengeRequestWithBattleId(battleId)));
	}
}
