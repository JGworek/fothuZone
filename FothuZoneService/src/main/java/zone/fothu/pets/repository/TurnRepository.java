package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.adventure.Turn;

public interface TurnRepository extends JpaRepository<Turn, Long> {

	@Query(nativeQuery = true, value = "SELECT MAX(id) FROM pets.turns WHERE battle_id = ?1")
	Long getLastTurnIdForBattle(long battleId);

	@Query(nativeQuery = true, value = "SELECT * FROM pets.turns WHERE battle_id = ?1 ORDER BY id DESC LIMIT 1")
	Turn getLastTurnForBattle(long battleId);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.turns WHERE battle_id = ?1 ORDER BY id DESC")
	List<Turn> getReverseOrderTurnsForBattle(long id);
}
