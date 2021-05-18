package zone.fothu.pets.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zone.fothu.pets.model.adventure.Battle;
import zone.fothu.pets.model.adventure.ChallengeRequest;
import zone.fothu.pets.model.adventure.Turn;
import zone.fothu.pets.repository.BattleRepository;
import zone.fothu.pets.repository.ChallengeRequestRepository;
import zone.fothu.pets.repository.TurnRepository;

@Service
public class RequestService implements Serializable {

	private static final long serialVersionUID = 1183483219260851540L;

	private final ChallengeRequestRepository challengeRequestRepository;
	private final BattleRepository battleRepository;
	private final TurnRepository turnRepository;
	private ChallengeRequest challengeRequestBean;
	private Turn turnBean;

	@Autowired
	public RequestService(ChallengeRequestRepository challengeRequestRepository, BattleRepository battleRepository, TurnRepository turnRepository, ChallengeRequest challengeRequestBean, Turn turnBean) {
		super();
		this.challengeRequestRepository = challengeRequestRepository;
		this.battleRepository = battleRepository;
		this.turnRepository = turnRepository;
		this.challengeRequestBean = challengeRequestBean;
		this.turnBean = turnBean;
	}

	public List<ChallengeRequest> getAllChallengeRequestsForUser(long userId) {
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

	public List<Battle> getAllCurrentPVPBattlesForUser(long userId) {
		boolean anyExpired = false;
		List<Battle> currentBattles = battleRepository.getAllCurrentPVPBattlesForUser(userId);
		for (Battle battle : currentBattles) {
			if (battle.getCreatedOn().plusDays(14).isBefore(LocalDateTime.now())) {
				anyExpired = true;
				Battle currentBattle = battleRepository.findById(battle.getId()).get();
				Turn lastTurn = turnRepository.getLastTurnForBattle(battle.getId());
				if (currentBattle.getNextTurnUser().getId() == currentBattle.getAttackingUser().getId()) {
					turnRepository.save(lastTurn.setBattleFinished(true));
					battleRepository.save(currentBattle.setWinningUser(currentBattle.getDefendingUser()).setLosingUser(currentBattle.getAttackingUser()).setBattleFinished(true));
				} else {
					turnRepository.save(lastTurn.setBattleFinished(true));
					battleRepository.save(currentBattle.setWinningUser(currentBattle.getAttackingUser()).setLosingUser(currentBattle.getDefendingUser()).setBattleFinished(true));
				}
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

	public ChallengeRequest getChallengeRequestById(long id) {
		ChallengeRequest challengeRequest = cleanOutPasswords(challengeRequestRepository.findById(id).get());
		return challengeRequest;
	}
}
