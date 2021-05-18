package zone.fothu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zone.fothu.pets.model.adventure.DefendingBattlePet;

public interface DefendingBattlePetRepository extends JpaRepository<DefendingBattlePet, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM pets.defending_battle_pets WHERE pet_id = ?1")
	DefendingBattlePet getBattlePetByPetId(long defendingPetId);

	@Query(nativeQuery = true, value = "SELECT * FROM pets.defending_battle_pets WHERE battle_id =?1 AND pet_id = ?2")
	DefendingBattlePet getBattlePetWithBattleIdAndPetId(long battleId, long id);

}
