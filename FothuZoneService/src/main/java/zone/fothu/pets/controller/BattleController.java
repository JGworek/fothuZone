package zone.fothu.pets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zone.fothu.pets.exception.BattleNotFoundException;
import zone.fothu.pets.exception.PetNotFoundException;
import zone.fothu.pets.model.Battle;
import zone.fothu.pets.repository.BattleLogRepository;
import zone.fothu.pets.repository.BattleRepository;
import zone.fothu.pets.service.BattleService;

@RestController
@CrossOrigin
@RequestMapping(path = "/battle")
public class BattleController {

    @Autowired
    BattleRepository battleRepository;
    @Autowired
    BattleLogRepository battleLogRepository;
    @Autowired
    BattleService battleService;

    @GetMapping("/all")
    public ResponseEntity<List<Battle>> getAllBattles() {
        List<Battle> battles = battleRepository.findAll();
        for (Battle battle : battles) {
            if (battle.getAttackingPet().getOwner() != null) {
                battle.getAttackingPet().getOwner().setUserPassword(null);
            }
            if (battle.getDefendingPet().getOwner() != null) {
                battle.getDefendingPet().getOwner().setUserPassword(null);
            }
        }
        return ResponseEntity.ok(battles);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Battle> getBattleById(@PathVariable int id) throws BattleNotFoundException {
        Battle battle = battleRepository.findById(id);
        if (battle.getAttackingPet().getOwner() != null) {
            battle.getAttackingPet().getOwner().setUserPassword(null);
        }
        if (battle.getDefendingPet().getOwner() != null) {
            battle.getDefendingPet().getOwner().setUserPassword(null);
            ;
        }
        return ResponseEntity.ok(battle);
    }

    @PostMapping("/newBattle/attackerId/{attackerId}/defenderId/{defenderId}")
    public ResponseEntity<Battle> createNewBattle(@PathVariable int attackerId, @PathVariable int defenderId)
        throws BattleNotFoundException, PetNotFoundException {
        Battle battle = battleService.battle(attackerId, defenderId);
        if (battle.getAttackingPet().getOwner() != null) {
            battle.getAttackingPet().getOwner().setUserPassword(null);
        }
        if (battle.getDefendingPet().getOwner() != null) {
            battle.getDefendingPet().getOwner().setUserPassword(null);
            ;
        }
        return ResponseEntity.ok(battle);
    }

}
