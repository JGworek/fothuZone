package zone.fothu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.adventure.Dungeon;

public interface DungeonRepository extends JpaRepository<Dungeon, Long> {

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.dungeon_maps WHERE id = ?1")
	Dungeon findMapById(long mapId);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.dungeon_maps WHERE name = ?1")
	Dungeon findMapByName(String name);

}
