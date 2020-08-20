package zone.fothu.pets.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.exception.PetNotFoundException;
import zone.fothu.pets.exception.PetNotUpdatedException;
import zone.fothu.pets.model.Pet;
import zone.fothu.pets.model.XPValue;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE id = ?1")
    Pet findById(int id) throws PetNotFoundException;

    @Query(nativeQuery = true, value = "SELECT * FROM pets.pets ORDER BY id ASC")
    List<Pet> findAll();

    @Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE name = ?1")
    Pet findByPetName(String petName) throws PetNotFoundException;

    @Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE user_id = ?1 ORDER BY id ASC")
    List<Pet> findAllUsersPetsById(int user_id) throws PetNotFoundException;

    @Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE user_id = (SELECT id FROM pets.users WHERE username = ?1) ORDER BY id ASC")
    List<Pet> findAllUsersPetsByUsername(String username) throws PetNotFoundException;

    @Query(nativeQuery = true, value = "SELECT * FROM pets.pets WHERE user_id = ?1 ORDER BY id ASC")
    List<Pet> findAllPetsByUserId(int userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE pets.pets SET name = ?2, stat_type = ?3, hunger = ?4, current_health = ?5, max_health = ?6, strength = ?7, agility = ?8, intelligence = ?9, pet_level = ?10 current_xp = ?11 WHERE id = ?1")
    void updatePet(int id, String name, String type, int hunger, int currentHealth, int maxHealth,
        int strength, int dexterity, int intelligence, int petLevel, int currentXP)
        throws PetNotUpdatedException, PSQLException;

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

    @Query(nativeQuery = true, value = "SELECT xp_to_next_level FROM pets.xp_chart WHERE pet_level = ?1")
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
