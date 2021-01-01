package zone.fothu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zone.fothu.pets.model.adventure.Battle;

public interface BattleRepository extends JpaRepository<Battle, Integer> {

}
