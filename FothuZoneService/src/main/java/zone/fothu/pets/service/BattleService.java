package zone.fothu.pets.service;

import java.io.Serializable;
import org.springframework.stereotype.Service;
import zone.fothu.pets.model.Battle;

@Service
public class BattleService implements Serializable {

   private static final long serialVersionUID = -7951721923780422911L;

   private final int accuracyHurdle = 75;
   private final int defendHurdle = 50;
   private final int aimHurdle = 50;
   private final int sharpenHurdle = 50;
   private final double attackModifier = 1.5;
   private final double startingArmorModifier = 1.5;
   private final double startingAccuracyModifier = 10;
   
   //make sure battle has flavor_text for the front end, and technical_text to store in the DB for analysis
    
   public Battle createNewBattle(String battleType) {
       //set pve or pvp based on battleType
       //if pvp, set both pet health values to full, if pve, set the attacker health to the current value
       //get attacking pet from DB and set attacking_pet to their id
       //get defending pet from DB and set defending_pet to their id
       //set attack power for attacking and defending pet based on pet type stat
       //set armor value for attacking and defending pet based on str/int
       //set accuracy value for attacking and defending pet based on int/agi
       //set speed value for attacking and defending pet based on agi/str
       //set default armor, accuracy, and attack modifiers for attacking and defending pet
       //set first turn based on speed value, then on agi on a tie, then on a coin flip
       //save new battle to DB
       return newBattle;
    }
    
   public Battle attack(int battleId, int attackingId) {
       //pet will do damage if they surpass the accuracy hurdle
       //on success: damage is (attack power*attack modifier) - (defense value*defense modifier)
       //if pve, the current health of the attacking pet will be updated upon damage
       //change the currentTurn to the petId that does not match attackingId
       return currentBattle;
   }
   
   public Battle defend(int battleId, int defendingId) {
       //pet will add 0.5 to armor modifier if their last 2 digits of their str+a random number from 1-100 is over the defend hurdle
       //change the currentTurn to the petId that does not match defendingId
       return currentBattle;
   }
   
   //stat*random number from 1-100 get over hurdle of level*a number
   
   public Battle aim(int battleId, int aimingId) {
       //pet will add 1 to accuracy modifier if their last 2 digits of their int+a random number from 1-100 is over the aim hurdle
       //change the currentTurn to the petId that does not match aimingId
       return currentBattle;
   }
   
   public Battle sharpen(int battleId, int sharpeningId) {
       //pet will add 0.5 to attack modifier if their last 2 digits of their agi+a random number from 1-100 is over the sharpen hurdle
       //change the currentTurn to the petId that does not match sharpeningId
       return currentBattle
   }
   
   public Battle endBattle(int battleId) {
       //set battle_finished to be true
       //battle will check if pve or pvp, and give exp to winner if pve attacker (defender is npc in pve)
       return endedBattle;
   }
   
   public void prematureEndBattle(int battleId) {
       //if the battle is not over when ngOnDestroy is run in pve, set the attacker to have lost, their health to 0, they get no xp, and battle_finished to be true
   }
}