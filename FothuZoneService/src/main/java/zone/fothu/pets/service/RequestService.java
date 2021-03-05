package zone.fothu.pets.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zone.fothu.pets.model.adventure.Battle;
import zone.fothu.pets.model.adventure.ChallengeRequest;
import zone.fothu.pets.repository.BattleLogRepository;
import zone.fothu.pets.repository.BattleRepository;
import zone.fothu.pets.repository.ChallengeRequestRepository;

@Service
public class RequestService implements Serializable {

	private static final long serialVersionUID = 1183483219260851540L;

	@Autowired
	ChallengeRequestRepository challengeRequestRepository;
	@Autowired
	BattleRepository battleRepository;
	@Autowired
	BattleLogRepository battleLogRepository;

	public List<ChallengeRequest> getAllChallengeRequestsForUser(int userId) {
		boolean anyExpired = false;
		List<ChallengeRequest> challengeRequests = challengeRequestRepository.getAllPendingChallengeRequestsForUser(userId);
		for (ChallengeRequest challengeRequest : challengeRequests) {

			if (challengeRequest.getCreatedOn().plusDays(7).isBefore(LocalDateTime.now())) {
				anyExpired = true;
				challengeRequest.setRejectedStatus(true);
				challengeRequestRepository.save(challengeRequest);
			}
		}
		if (anyExpired = true) {
			challengeRequests = challengeRequestRepository.getAllPendingChallengeRequestsForUser(userId);
		}
		return challengeRequests;
	}

	public List<Battle> getAllCurrentPVPBattlesForUser(int userId) {
		boolean anyExpired = false;
		List<Battle> currentBattles = battleRepository.getAllCurrentPVPBattlesForUser(userId);
		for (Battle battle : currentBattles) {
			if (battle.getCreatedOn().plusDays(14).isBefore(LocalDateTime.now())) {
				anyExpired = true;
				Battle currentBattle = battleRepository.findById(battle.getId()).get();
				currentBattle.setBattleFinished(true);
				currentBattle.setLosingPet(currentBattle.getCurrentTurnPet());
				if (currentBattle.getCurrentTurnPet().getId() == currentBattle.getAttackingPet().getId()) {
					currentBattle.setWinningPet(currentBattle.getDefendingPet());
					battleLogRepository.saveNewBattleLog(currentBattle.getId(), battleLogRepository.findById(battleLogRepository.getLastBattleLogIdForBattle(currentBattle.getId())).get().getTurnNumber() + 1, currentBattle.getDefendingPet().getName() + " wins due to timeout!", currentBattle.getDefendingPet().getName() + " wins since " + currentBattle.getAttackingPet().getName() + ", failed to due anything before the battle timed out 14 days after creation.", true);

				} else {
					currentBattle.setWinningPet(currentBattle.getAttackingPet());
					battleLogRepository.saveNewBattleLog(currentBattle.getId(), battleLogRepository.findById(battleLogRepository.getLastBattleLogIdForBattle(currentBattle.getId())).get().getTurnNumber() + 1, currentBattle.getAttackingPet().getName() + " wins due to timeout!", currentBattle.getAttackingPet().getName() + " wins since " + currentBattle.getDefendingPet().getName() + ", failed to due anything before the battle timed out 14 days after creation.", true);
				}
				battleRepository.save(currentBattle);
			}
		}
		if (anyExpired = true) {
			currentBattles = battleRepository.getAllCurrentPVPBattlesForUser(userId);
		}
		return currentBattles;
	}

	public ChallengeRequest cleanOutPasswords(ChallengeRequest challengeRequest) {
		if (challengeRequest.getAttackingUser() != null) {
			challengeRequest.getAttackingUser().setUserPassword(null);
			challengeRequest.getAttackingUser().setSecretPassword(null);
		}
		if (challengeRequest.getDefendingUser() != null) {
			challengeRequest.getDefendingUser().setUserPassword(null);
			challengeRequest.getDefendingUser().setSecretPassword(null);
		}
		return challengeRequest;
	}

	public ChallengeRequest getChallengeRequestById(int id) {
		ChallengeRequest challengeRequest = cleanOutPasswords(challengeRequestRepository.findById(id).get());
		return challengeRequest;
	}
}
