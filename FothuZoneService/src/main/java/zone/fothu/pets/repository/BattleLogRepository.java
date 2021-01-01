package zone.fothu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zone.fothu.pets.model.adventure.BattleLog;

public interface BattleLogRepository extends JpaRepository<BattleLog, Integer> {

}
