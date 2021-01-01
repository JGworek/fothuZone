package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.exception.BattleNotFoundException;
import zone.fothu.pets.model.AutoBattleLog;

public interface AutoBattleLogRepository extends JpaRepository<AutoBattleLog, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM pets.auto_battle_logs WHERE battle_id = ?1")
    List<AutoBattleLog> findByBattleId(int id) throws BattleNotFoundException;

    @Query(nativeQuery = true, value = "SELECT MAX(id) FROM pets.auto_battle_logs")
    int findLatestBattleLogID();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE pets.auto_battle_logs SET battle_finished = true WHERE id = ?1")
    void updateLastBattleStepInTimeout(int id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO pets.auto_battle_logs VALUES (DEFAULT, ?1, ?2, ?3, ?4, ?5)")
    void saveNewBattleLog(int currentBattleID, int turnNumber, String turnText, String turnResult,
        boolean battleFinished);
}
