package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.profile.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE id = ?id")
	Pet findById(int id);

	@Override
	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets ORDER BY id ASC")
	List<Pet> findAll();

	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE name = ?petName")
	Pet findByPetName(String petName);

	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE user_id = ?userId ORDER BY id ASC")
	List<Pet> findAllUsersPetsById(int userid);

	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE user_id = (SELECT id FROM pets.users WHERE username = ?userName) ORDER BY id ASC")
	List<Pet> findAllUsersPetsByUsername(String username);

	@Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE user_id = ?userId ORDER BY id ASC")
	List<Pet> findAllPetsByUserId(int userId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET name = ?name, hunger = ?hunger, current_health = ?currentHealth, max_health = ?maxHealth, strength = ?strength, agility = ?agility, intelligence = ?intelligence, pet_level = ?petLevel current_xp = ?currentXP WHERE id = ?id")
	void updatePet(int id, String name, int hunger, int currentHealth, int maxHealth, int strength, int agility, int intelligence, int petLevel, int currentXP);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET strength = ?strength, agility = ?agility, intelligence = ?intelligence WHERE id = ?id")
	void setPetStats(int id, int strength, int agility, int intelligence);

//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = "UPDATE pets.pets SET name = ?2, image = ?3, stat_type = ?4, hunger = ?5, current_health = ?6, max_health = ?7, strength = ?8, agility = ?9, intelligence = ?10, pet_level = ?11, current_xp = ?12, user_id = ?13  WHERE id = ?1")
//    void givePet(int id, String name, String image, String type, int hunger, int currentHealth, int maxHealth,
//        int strength, int dexterity, int intelligence, int petLevel, int currentXP, int userId)
//        throws PetNotUpdatedException, PSQLException;

	@Query(nativeQuery = true, value = "SELECT xp_to_next_level FROM pets.xp_chart WHERE pet_level = ?currentLevel")
	int getXPToNextLevel(int currentLevel);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET current_health = ?currentHealth WHERE id = ?id")
	void setPetHealth(int currentHealth, int id);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET current_health = max_health")
	void restoreAllPetsHealth();

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET current_health = max_health WHERE id = ?petId")
	void restoreOnePetsHealth(int petId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET current_health = max_health WHERE user_id = ?userId")
	void restoreAllUsersPetsHealth(int userId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET current_xp = ?currentXP, pet_level = ?petLevel WHERE id = ?petId")
	void updatePetXPAndLevel(int petId, int currentXP, int petLevel);
}
