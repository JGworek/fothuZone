package zone.fothu.pets.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zone.fothu.pets.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE id = ?1")
	Pet findById(int id);
	
	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets")
	List<Pet> findAll();
	
	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE name = ?1")
	Pet findByPetName(String petName);
	
	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE user_id = ?1")
	List<Pet> findAllUsersPetsById(int user_id);
	
	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE user_id = (SELECT id FROM pets.users WHERE username = ?1)")
	List<Pet> findAllUsersPetsByUsername(String username);

}
