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
import zone.fothu.pets.model.BattleDTO;
import zone.fothu.pets.repository.BattleLogRepository;
import zone.fothu.pets.repository.BattleRepository;
import zone.fothu.pets.service.AutoBattleService;
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
    AutoBattleService autoBattleService;

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

    @PostMapping("/newAutoBattle/pve/attackerId/{attackerId}/defenderId/{defenderId}")
    public ResponseEntity<BattleDTO> createNewPVEBattle(@PathVariable int attackerId, @PathVariable int defenderId)
        throws BattleNotFoundException, PetNotFoundException {
        BattleDTO battleResult = autoBattleService.battle(attackerId, defenderId, "pve");
        Battle battle = battleResult.getBattle();
        if (battle.getAttackingPet().getOwner() != null) {
            battleResult.getBattle().getAttackingPet().getOwner().setUserPassword(null);
        }
        if (battle.getDefendingPet().getOwner() != null) {
            battleResult.getBattle().getDefendingPet().getOwner().setUserPassword(null);
        }
        return ResponseEntity.ok(battleResult);
    }
    
    @PostMapping("/newAutoBattle/pvp/attackerId/{attackerId}/defenderId/{defenderId}")
    public ResponseEntity<BattleDTO> createNewPVPBattle(@PathVariable int attackerId, @PathVariable int defenderId)
        throws BattleNotFoundException, PetNotFoundException {
        BattleDTO battleResult = autoBattleService.battle(attackerId, defenderId, "pvp");
        Battle battle = battleResult.getBattle();
        if (battle.getAttackingPet().getOwner() != null) {
            battleResult.getBattle().getAttackingPet().getOwner().setUserPassword(null);
        }
        if (battle.getDefendingPet().getOwner() != null) {
            battleResult.getBattle().getDefendingPet().getOwner().setUserPassword(null);
        }
        return ResponseEntity.ok(battleResult);
    }
}
