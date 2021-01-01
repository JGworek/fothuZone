package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.exception.BattleNotFoundException;
import zone.fothu.pets.model.AutoBattle;

public interface AutoBattleRepository extends JpaRepository<AutoBattle, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * FROM pets.auto_battles")
    List<AutoBattle> findAll();

    @Query(nativeQuery = true, value = "SELECT * FROM pets.auto_battles WHERE id = ?1")
    AutoBattle findById(int id) throws BattleNotFoundException;

    @Query(nativeQuery = true, value = "SELECT MAX(id) FROM pets.auto_battles")
    int findLatestBattleID();

    @Query(nativeQuery = true, value = "SELECT * FROM pets.auto_battles WHERE attacking_pet = ?1 OR defending_pet = ?1")
    List<AutoBattle> findAllBattlesForOnePet(int id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO pets.auto_battles VALUES (DEFAULT, ?1, ?2, null)")
    void saveNewBattle(int attackingPet, int defendingPet);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE pets.auto_battles SET winning_pet_id = ?2, losing_pet_id = ?3 WHERE id = ?1")
    void setWinner(int id, int winningPetId, int losingPetId);
}
