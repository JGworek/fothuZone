package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zone.fothu.pets.model.adventure.DungeonMap;

public interface DungeonMapRepository extends JpaRepository<DungeonMap, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM pets.dungeon_maps WHERE id = ?imageId")
	DungeonMap findMapById(int mapId);

	@Query(nativeQuery = true, value = "SELECT * FROM pets.dungeon_maps WHERE name = ?name ORDER BY id ASC")
	List<DungeonMap> findMapsByName(String name);

}
