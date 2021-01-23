package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.exception.BattleNotFoundException;
import zone.fothu.pets.model.adventure.AutoBattle;

public interface AutoBattleRepository extends JpaRepository<AutoBattle, Integer> {

	@Override
	@Query(nativeQuery = true, value = "SELECT * FROM pets.battles")
	List<AutoBattle> findAll();

	@Query(nativeQuery = true, value = "SELECT * FROM pets.battles WHERE id = ?id")
	AutoBattle findById(int id) throws BattleNotFoundException;

	@Query(nativeQuery = true, value = "SELECT MAX(id) FROM pets.battles")
	int findLatestBattleID();

	@Query(nativeQuery = true, value = "SELECT * FROM pets.battles WHERE attacking_pet = ?petId OR defending_pet = ?petId")
	List<AutoBattle> findAllBattlesForOnePet(int petId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO pets.battles VALUES (DEFAULT, ?attackingPetId, ?defendingPetId, null)")
	void saveNewBattle(int attackingPetId, int defendingPetId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.battles SET winning_pet_id = ?winningPetId, losing_pet_id = ?losingPetId WHERE id = ?battleId")
	void setWinner(int battleId, int winningPetId, int losingPetId);
}
