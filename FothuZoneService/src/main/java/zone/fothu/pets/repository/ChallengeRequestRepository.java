package zone.fothu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zone.fothu.pets.model.adventure.ChallengeRequest;

public interface ChallengeRequestRepository extends JpaRepository<ChallengeRequest, Integer> {

}
