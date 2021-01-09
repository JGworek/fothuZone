package zone.fothu.pets.service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zone.fothu.pets.model.adventure.Battle;
import zone.fothu.pets.model.adventure.ChallengeRequestDTO;
import zone.fothu.pets.model.profile.Pet;
import zone.fothu.pets.repository.BattleLogRepository;
import zone.fothu.pets.repository.BattleRepository;
import zone.fothu.pets.repository.ChallengeRequestDTORepository;

//@Service
public class BattleService implements Serializable {

    private static final long serialVersionUID = -7951721923780422911L;

    @Autowired
    BattleRepository battleRepository;
    @Autowired
    BattleLogRepository battleLogRepository;
    @Autowired
    ChallengeRequestDTORepository challengeRequestDTORepository;
    
    private final int accuracyHurdle = 75;
    private final int defendHurdle = 50;
    private final int aimHurdle = 50;
    private final int sharpenHurdle = 50;
    private final double startingAttackModifier = 1.5;
    private final double startingArmorModifier = 1.5;
    private final double startingAccuracyModifier = 10;
    private final int hurdleMuliplier = 3;

    // make sure battle has flavor_text for the front end, and technical_text to
    // store in the DB for analysis

    // ROUND TO THE 4TH DECIMAL PLACE TO HANDLE REPEATING DECIMALS
    // ROUND DAMAGE VALUE AND DEFENCE VALUES DOWN (DID NOT MEET THRESHOLD FOR THE
    // NEXT VALUE)

    public ChallengeRequestDTO createNewChallengeRequest(int attackerId, int defenderId) {
        // create new challenge request in the DB
        return newChallengeRequest;
    }

    public ChallengeRequestDTO acceptChallengeRequest(int challengeRequestId) {
        // ChallengeRequest acceptedChallengeRequest =
        // challengeRepository.getChallengeRequestById(challengeRequestId).setAcceptedStatus(true);
        // challengeRepository.update(acceptedChallengeRequest);
        // createNewBattleWithNoPets(String battleType)

        return acceptedChallengeRequest;
    }

    public ChallengeRequestDTO rejectChallengeRequest(int challengeRequestId) {
        // ChallengeRequest rejectedChallengeRequest =
        // challengeRepository.getChallengeRequestById(challengeRequestId).setRejectedStatus(true);
        // challengeRepository.update(rejectedChallengeRequest);
        return rejectedChallengeRequest;
    }

    public Battle createNewBattleWithBothPets(String battleType, int attackingPetId, int defendingPetId) {
        // set pve or pvp based on battleType
        // if pvp, set both pet health values to full, if pve, set the attacker health
        // to the current value
        // get attacking pet from DB and set attacking_pet to their id
        // get defending pet from DB and set defending_pet to their id
        // set attack power for attacking and defending pet based on pet type stat
        // set armor value for attacking and defending pet based on str/int
        // set accuracy value for attacking and defending pet based on int/agi
        // set speed value for attacking and defending pet based on agi/str
        // set default armor, accuracy, and attack modifiers for attacking and defending
        // pet
        // set first turn based on speed value, then on agi on a tie, then on a coin
        // flip
        // save new battle to DB
        return newBattle;
    }

    public Battle createNewBattleWithNoPets(String battleType) {

        return newBattle;
    }

    public Battle updateBattleWithAttackingPet(int BattleId, int attackingPetId) {
        return updatedBattle;
    }

    public Battle updateBattleWithDefendingPet(int BattleId, int defendingPetId) {

        return updatedBattle;
    }

    public Pet getActingPet(Battle currentBattle, int actingPetId) {
        Set<Pet> battlePets = new HashSet<Pet>();
        battlePets.add(currentBattle.getAttackingPet());
        battlePets.add(currentBattle.getDefendingPet());
        for (Pet pet : battlePets) {
            if (pet.getId() == actingPetId) {
                return pet;
            }
        }
        return null;
    }
    
    public Pet getOtherPet(Battle currentBattle, int actingPetId) {
        Set<Pet> battlePets = new HashSet<Pet>();
        battlePets.add(currentBattle.getAttackingPet());
        battlePets.add(currentBattle.getDefendingPet());
        for (Pet pet : battlePets) {
            if (pet.getId() != actingPetId) {
                return pet;
            }
        }
        return null;
    }

    public Battle attack(int battleId, int attackingId) {
        // pet will do damage if they surpass the accuracy hurdle
        // on success: damage is (attack power*attack modifier) - (defense value*defense
        // modifier)
        // if pve, the current health of the attacking pet will be updated upon damage
        // change the currentTurn to the petId that does not match attackingId
        return currentBattle;
    }

    public Battle defend(int battleId, int defendingId) {
        // pet will add 0.5 to armor modifier if their last 2 digits of their str+a
        // random number from 1-100 is over the defend hurdle
        // change the currentTurn to the petId that does not match defendingId
        return currentBattle;
    }

    // stat*random number from 1-100+level get over hurdle of level*hurdlemultplier+hurdle number
    

    public Battle aim(int battleId, int aimingId) {
        // pet will add 1 to accuracy modifier if their last 2 digits of their int+a
        // random number from 1-100 is over the aim hurdle
        // change the currentTurn to the petId that does not match aimingId
        return currentBattle;
    }

    public Battle sharpen(int battleId, int sharpeningId) {
        // pet will add 0.5 to attack modifier if their last 2 digits of their agi+a
        // random number from 1-100 is over the sharpen hurdle
        // change the currentTurn to the petId that does not match sharpeningId
        return currentBattle;
    }

    public Battle endBattle(int battleId) {
        // set battle_finished to be true
        // battle will check if pve or pvp, and give exp to winner if pve attacker
        // (defender is npc in pve)
        return endedBattle;
    }

    public void prematureEndBattle(int battleId) {
        // if the battle is not over when ngOnDestroy is run in pve, set the attacker to
        // have lost, their health to 0, they get no xp, and battle_finished to be true
    }
}