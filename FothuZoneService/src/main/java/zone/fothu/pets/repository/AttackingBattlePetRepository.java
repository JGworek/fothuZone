package zone.fothu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zone.fothu.pets.model.adventure.AttackingBattlePet;

public interface AttackingBattlePetRepository extends JpaRepository<AttackingBattlePet, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM pets.attacking_battle_pets WHERE pet_id = ?1")
	AttackingBattlePet getBattlePetByPetId(long attackingPetId);

	@Query(nativeQuery = true, value = "SELECT * FROM pets.attacking_battle_pets WHERE battle_id =?1 AND pet_id = ?2")
	AttackingBattlePet getBattlePetWithBattleIdAndPetId(long battleId, long id);

}
