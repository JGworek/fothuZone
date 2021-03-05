package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.adventure.BattleDTO;

public interface BattleDTORepository extends JpaRepository<BattleDTO, Integer> {

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.battles WHERE (attacking_user_id = ?1 OR defending_user_id = ?1) AND battle_finished = false AND LOWER(battle_type) = 'pvp'")
	List<BattleDTO> getAllCurrentPVPBattlesForUser(int userId);
}
