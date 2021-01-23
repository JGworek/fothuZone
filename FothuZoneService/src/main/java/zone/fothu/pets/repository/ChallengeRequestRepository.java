package zone.fothu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.adventure.ChallengeRequest;

public interface ChallengeRequestRepository extends JpaRepository<ChallengeRequest, Integer> {

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO pets.challenge_requests VALUES (DEFAULT, false, false, ?attackerId, ?defenderId, null, DEFAULT)")
	ChallengeRequest createNewChallengeRequest(int attackerId, int defenderId);

}
