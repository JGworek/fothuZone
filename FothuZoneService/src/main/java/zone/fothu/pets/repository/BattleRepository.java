package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.adventure.Battle;

public interface BattleRepository extends JpaRepository<Battle, Integer> {

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO pets.battles VALUES (DEFAULT,?1,null,null,null,null,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,null,false,0,0,DEFAULT,?2,?3)")
	void createNewBattle(String battleType, int attackingUserId, int defendingUserId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO pets.battles VALUES (DEFAULT,?1,?2d,?3,null,null,?4,?5,?6,?6,?7,?7,?8,?8,?9,?10,?11,?12,?13,?14,?15,?16,FALSE,?17,?18,DEFAULT,?19,?20)")
	void createNewBattleWithBothPets(String battleType, int attackingPetId, int defendingPetId, int attackingPetCurrentHealth, int defendingPetCurrentHealth, double startingAttackModifier, double startingArmorModifier, double startingAccuracyModifier, double attackingPetBaseAttackPower, double defendingPetBaseAttackPower, double attackingPetBaseArmor, double defendingPetBaseArmor, double attackingPetBaseSpeed, double defendingPetBaseSpeed, double attackingPetBaseAccuracy,
			double defendingPetBaseAccuracy, int turnCount, int firstTurnPetId, int attackingUserId, int defendingUserId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.battles SET attacking_pet_id = ?2, attacking_pet_current_health = ?3 WHERE id = ?1")
	void setAttackingPet(int battleId, int attackingPetId, int attackingPetMaxHp);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.battles SET defending_pet_id = ?2, defending_pet_current_health = ?3 WHERE id = ?1")
	void setDefendingPet(int battleId, int defendingPetId, int defendingPetMaxHp);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT MAX(id) FROM pets.battles")
	Integer getMostRecentBattleId();

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO pets.battles VALUES (DEFAULT,'pve',null,?3,null,null,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,null,false,0,0,DEFAULT,?1,?2)")
	void createNewPVEBattleWithDefendingPet(int attackingUserId, int defendingUserId, int defendingPetId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.battles SET attacking_pet_current_health = ?2, defending_pet_current_health = ?3, attacking_pet_current_attack_modifier = ?4, defending_pet_current_attack_modifier = ?4, attacking_pet_current_armor_modifier = ?5, defending_pet_current_armor_modifier = ?5, attacking_pet_current_accuracy_modifier = ?6, defending_pet_current_accuracy_modifier = ?6, attacking_pet_base_attack_power = ?7, defending_pet_base_attack_power = ?8, attacking_pet_base_armor = ?9, defending_pet_base_armor = ?10, attacking_pet_base_speed = ?11, defending_pet_base_speed = ?12, attacking_pet_base_accuracy = ?13, defending_pet_base_accuracy = ?14, current_turn_count = ?15, current_turn_pet_id = ?16 WHERE id = ?1")
	void addBattleStatsForBattle(int battleId, int attackingPetCurrentHealth, int defendingPetCurrentHealth, double startingAttackModifier, double startingArmorModifer, double startingAccuracyModifier, double attackingPetBaseAttackPower, double defendingPetBaseAttackPower, double attackingPetBaseArmor, double defendingPetBaseArmor, double attackingPetBaseSpeed, double defendingPetBaseSpeed, double attackingPetBaseAccuracy, double defendingPetBaseAccuracy, int turnNumber, int firstTurnPetId);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.battles WHERE (attacking_user_id = ?1 OR defending_user_id = ?1) AND battle_finished = false AND LOWER(battle_type) = 'pvp'")
	List<Battle> getAllCurrentPVPBattlesForUser(int userId);

}
