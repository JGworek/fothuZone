package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.adventure.ChallengeRequestDTO;

public interface ChallengeRequestDTORepository extends JpaRepository<ChallengeRequestDTO, Integer> {

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO pets.challenge_requests VALUES (DEFAULT, false, false, ?attackerId, ?defenderId, null, DEFAULT)")
	ChallengeRequestDTO createNewChallengeRequest(int attackerId, int defenderId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.challenge_requests SET accepted_status = TRUE WHERE id = ?challengeRequestId")
	ChallengeRequestDTO acceptChallengeRequest(int challengeRequestId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.challenge_requests SET rejected_status = TRUE WHERE id = ?challengeRequestId")
	ChallengeRequestDTO rejectChallengeRequest(int challengeRequestId);

	@Query(nativeQuery = true, value = "SELECT * FROM pets.challenge_requests")
	List<ChallengeRequestDTO> getAllChallengeRequests();

	@Query(nativeQuery = true, value = "SELECT * FROM pets.challenge_requests where defender_id = ?userId AND accepted_status = FALSE AND rejected_status = FALSE")
	List<ChallengeRequestDTO> getAllPendingChallengeRequestsForUser(int userId);

}
