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

	private final BattleService battleService;
	private final RequestService requestService;
	private final ChallengeRequestRepository challengeRequestRepository;

	@Autowired
	public ChallengeRequestController(@Lazy BattleService battleService, @Lazy RequestService requestService, ChallengeRequestRepository challengeRequestRepository) {
		super();
		this.battleService = battleService;
		this.requestService = requestService;
		this.challengeRequestRepository = challengeRequestRepository;
	}

	@PostMapping("/new/challengerId/{attackerId}/opponentId/{defenderId}/numberOfPets/{numberOfPets}")
	ResponseEntity<ChallengeRequest> createNewChallengeRequest(@PathVariable long attackerId, @PathVariable long defenderId, @PathVariable int numberOfPets) {
		return ResponseEntity.ok(battleService.createNewChallengeRequest(attackerId, defenderId, numberOfPets));
	}

	@PutMapping("/accept/challengeRequestId/{challengeRequestId}")
	ResponseEntity<ChallengeRequest> acceptChallengeRequest(@PathVariable long challengeRequestId) {
		return ResponseEntity.ok(battleService.acceptChallengeRequest(challengeRequestId));
	}

	@PutMapping("/reject/challengeRequestId/{challengeRequestId}")
	ResponseEntity<ChallengeRequest> rejectChallengeRequest(@PathVariable long challengeRequestId) {
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
	ResponseEntity<List<ChallengeRequest>> getAllPendingChallengeRequestsForUser(@PathVariable long userId) {
		return ResponseEntity.ok(requestService.getAllChallengeRequestsForUser(userId));
	}

	@GetMapping("/id/{id}")
	ResponseEntity<ChallengeRequest> getChallengeRequestWithId(@PathVariable long id) {
		return ResponseEntity.ok(requestService.getChallengeRequestById(id));
	}

	@GetMapping("/battleId/{battleId}")
	ResponseEntity<ChallengeRequest> getChallengeRequestForBattle(@PathVariable long battleId) {
		return ResponseEntity.ok(requestService.cleanOutPasswords(challengeRequestRepository.getChallengeRequestWithBattleId(battleId)));
	}
}