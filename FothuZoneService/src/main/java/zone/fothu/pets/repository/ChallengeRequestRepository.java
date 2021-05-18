package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.adventure.ChallengeRequest;

public interface ChallengeRequestRepository extends JpaRepository<ChallengeRequest, Long> {

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.challenge_requests where defending_user_id = ?1 AND accepted_status = FALSE AND rejected_status = FALSE ORDER BY created_on ASC")
	List<ChallengeRequest> getAllPendingChallengeRequestsForUser(long userId);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT MAX(id) FROM pets.challenge_requests")
	Long getMostRecentChallengeRequestId();

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.challenge_requests where resulting_battle_id = ?1")
	ChallengeRequest getChallengeRequestWithBattleId(long battleId);

}
