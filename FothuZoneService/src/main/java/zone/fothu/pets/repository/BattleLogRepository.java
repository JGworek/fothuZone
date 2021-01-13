package zone.fothu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.adventure.BattleLog;

public interface BattleLogRepository extends JpaRepository<BattleLog, Integer> {

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO pets.battle_logs VALUES (DEFAULT, ?battleId, ?currentTurnCount, ?turnFlavorText, ?turnTechnicalText, ?battleEnded)")
	BattleLog saveNewBattleLog(int battleId, int currentTurnCount, String turnFlavorText, String turnTechnicalText, boolean battleEnded);

}
