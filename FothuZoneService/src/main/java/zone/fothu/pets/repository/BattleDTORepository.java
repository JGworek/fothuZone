package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zone.fothu.pets.model.adventure.BattleDTO;

public interface BattleDTORepository extends JpaRepository<BattleDTO, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM pets.battles WHERE (attacker_id = ?1 OR defender_id = ?1) AND battle_finished = false AND LOWER(battle_type) = 'pvp'")
	List<BattleDTO> getAllCurrentPVPBattlesForUser(int userId);
}
