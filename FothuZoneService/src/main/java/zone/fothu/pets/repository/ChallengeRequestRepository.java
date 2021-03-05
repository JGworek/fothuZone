package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.adventure.ChallengeRequest;

public interface ChallengeRequestRepository extends JpaRepository<ChallengeRequest, Integer> {

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO pets.challenge_requests VALUES (DEFAULT, false, false, ?1, ?2, null, DEFAULT)")
	void createNewChallengeRequest(int attackerId, int defenderId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.challenge_requests SET accepted_status = TRUE WHERE id = ?1")
	void acceptChallengeRequest(int challengeRequestId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.challenge_requests SET rejected_status = TRUE WHERE id = ?1")
	void rejectChallengeRequest(int challengeRequestId);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.challenge_requests where defender_id = ?1 AND accepted_status = FALSE AND rejected_status = FALSE")
	List<ChallengeRequest> getAllPendingChallengeRequestsForUser(int userId);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT MAX(id) FROM pets.challenge_requests")
	int getMostRecentChallengeRequestId();

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.challenge_requests where battle_id = ?1")
	ChallengeRequest getChallengeRequestWithBattleId(int battleId);

}
