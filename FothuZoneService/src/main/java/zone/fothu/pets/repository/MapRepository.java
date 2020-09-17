package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zone.fothu.pets.model.Map;

public interface MapRepository extends JpaRepository<Map, Integer> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM pets.maps WHERE id = ?1")
    Map findMapById(int imageId);
    
    @Query(nativeQuery = true, value = "SELECT * FROM pets.maps WHERE name = ?1 ORDER BY id ASC")
    List<Map> findMapsByName(String name);

}
