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
import zone.fothu.pets.model.AutoBattle;
import zone.fothu.pets.repository.AutoBattleLogRepository;
import zone.fothu.pets.repository.AutoBattleRepository;
import zone.fothu.pets.service.AutoBattleService;

@RestController
@CrossOrigin
@RequestMapping(path = "/autoBattle")
public class BattleController {

    @Autowired
    AutoBattleRepository battleRepository;
    @Autowired
    AutoBattleLogRepository battleLogRepository;
    @Autowired
    AutoBattleService autoBattleService;

    @GetMapping("/all")
    public ResponseEntity<List<AutoBattle>> getAllBattles() {
        List<AutoBattle> battles = battleRepository.findAll();
        for (AutoBattle battle : battles) {
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
    public ResponseEntity<AutoBattle> getBattleById(@PathVariable int id) throws BattleNotFoundException {
        AutoBattle battle = battleRepository.findById(id);
        if (battle.getAttackingPet().getOwner() != null) {
            battle.getAttackingPet().getOwner().setUserPassword(null);
        }
        if (battle.getDefendingPet().getOwner() != null) {
            battle.getDefendingPet().getOwner().setUserPassword(null);
            ;
        }
        return ResponseEntity.ok(battle);
    }

    @PostMapping("/new/attackerId/{attackerId}/defenderId/{defenderId}")
    public ResponseEntity<AutoBattle> createNewBattle(@PathVariable int attackerId, @PathVariable int defenderId)
        throws BattleNotFoundException, PetNotFoundException {
        AutoBattle battleResult = autoBattleService.battle(attackerId, defenderId);
        if (battleResult.getAttackingPet().getOwner() != null) {
            battleResult.getAttackingPet().getOwner().setUserPassword(null);
        }
        if (battleResult.getDefendingPet().getOwner() != null) {
            battleResult.getDefendingPet().getOwner().setUserPassword(null);
        }
        return ResponseEntity.ok(battleResult);
    }
}
