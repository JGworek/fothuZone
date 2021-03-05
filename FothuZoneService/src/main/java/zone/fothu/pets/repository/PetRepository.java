package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.profile.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE name = ?1")
	Pet findByPetName(String petName);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE user_id = ?1 ORDER BY id ASC")
	List<Pet> findAllUsersPetsById(int userid);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE user_id = (SELECT id FROM pets.users WHERE username = ?1) ORDER BY id ASC")
	List<Pet> findAllUsersPetsByUsername(String username);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE user_id = ?1 ORDER BY id ASC")
	List<Pet> findAllPetsByUserId(int userId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET name = ?2, hunger = ?3, current_health = ?4, max_health = ?5, strength = ?6, agility = ?7, intelligence = ?8, pet_level = ?9, current_xp = ?10 WHERE id = ?1")
	void updatePet(int id, String name, int hunger, int currentHealth, int maxHealth, int strength, int agility, int intelligence, int petLevel, int currentXP);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET strength = ?2, agility = ?3, intelligence = ?4 WHERE id = ?1")
	void setPetStats(int id, int strength, int agility, int intelligence);

//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = "UPDATE pets.pets SET name = ?2, image = ?3, stat_type = ?4, hunger = ?5, current_health = ?6, max_health = ?7, strength = ?8, agility = ?9, intelligence = ?10, pet_level = ?11, current_xp = ?12, user_id = ?13  WHERE id = ?1")
//    void givePet(int id, String name, String image, String type, int hunger, int currentHealth, int maxHealth,
//        int strength, int dexterity, int intelligence, int petLevel, int currentXP, int userId)
//        throws PetNotUpdatedException, PSQLException;

	@Transactional
	@Query(nativeQuery = true, value = "SELECT xp_to_next_level FROM pets.xp_chart WHERE pet_level = ?1 + 1")
	int getXPToNextLevel(int currentLevel);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET current_health = ?1 WHERE id = ?2")
	void setPetHealth(int currentHealth, int id);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET current_health = max_health")
	void restoreAllPetsHealth();

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET current_health = max_health WHERE id = ?1")
	void restoreOnePetsHealth(int petId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET current_health = max_health WHERE user_id = ?1")
	void restoreAllUsersPetsHealth(int userId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET current_xp = ?2, pet_level = ?3 WHERE id = ?1")
	void updatePetXPAndLevel(int petId, int currentXP, int petLevel);
}
