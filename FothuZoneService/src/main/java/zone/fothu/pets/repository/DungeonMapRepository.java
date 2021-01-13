package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zone.fothu.pets.model.adventure.DungeonMap;

public interface DungeonMapRepository extends JpaRepository<DungeonMap, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM pets.dungeon_maps WHERE id = ?1")
	DungeonMap findMapById(int imageId);

	@Query(nativeQuery = true, value = "SELECT * FROM pets.dungeon_maps WHERE name = ?1 ORDER BY id ASC")
	List<DungeonMap> findMapsByName(String name);

}
