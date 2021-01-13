package zone.fothu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.adventure.Battle;

public interface BattleRepository extends JpaRepository<Battle, Integer> {

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO pets.battles VALUES (DEFAULT,?battleType,null,null,null,null,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,null,false,0,0,DEFAULT)")
	Battle createNewBattle(String battleType);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO pets.battles VALUES (DEFAULT,?battleType,?attackingPetId,?defendingPetId,null,null,?attackingPetCurrentHealth,?defendingPetCurrentHealth,?startingAttackModifier,?startingAttackModifier,?startingArmorModifier,?startingArmorModifier,?startingAccuracyModifier,?startingAccuracyModifier,?attackingPetBaseAttackPower,?defendingPetBaseAttackPower,?attackingPetBaseArmor,?defendingPetBaseArmor,?attackingPetBaseSpeed,?defendingPetBaseSpeed,?turnCount,?firstTurnPetId,FALSE,?attackingPetBaseAccuracy,?defendingPetBaseAccuracy,DEFAULT)")
	Battle createNewBattleWithBothPets(String battleType, int attackingPetId, int defendingPetId, int attackingPetCurrentHealth, int defendingPetCurrentHealth, double startingAttackModifier, double startingArmorModifier, double startingAccuracyModifier, double attackingPetBaseAttackPower, double defendingPetBaseAttackPower, double attackingPetBaseArmor, double defendingPetBaseArmor, double attackingPetBaseSpeed, double defendingPetBaseSpeed, double attackingPetBaseAccuracy,
			double defendingPetBaseAccuracy, int turnCount, int firstTurnPetId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.battles SET attacking_pet_id = ?attackingPetId WHERE id = ?battleId")
	Battle setAttackingPet(int battleId, int attackingPetId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.battles SET defending_pet_id = ?defendingPetId WHERE id = ?battleId")
	Battle setDefendingPet(int battleId, int defendingPetId);

}
