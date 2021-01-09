package zone.fothu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zone.fothu.pets.model.adventure.ChallengeRequestDTO;

public interface ChallengeRequestDTORepository extends JpaRepository<ChallengeRequestDTO, Integer> {

}
