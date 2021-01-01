package zone.fothu.pets.controller;

import java.io.Serializable;
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
import zone.fothu.pets.model.adventure.AutoBattle;
import zone.fothu.pets.model.adventure.AutoBattleDTO;
import zone.fothu.pets.repository.AutoBattleLogRepository;
import zone.fothu.pets.repository.AutoBattleRepository;
import zone.fothu.pets.service.AutoBattleService;

@RestController
@CrossOrigin
@RequestMapping(path = "/autoBattle")
public class AutoBattleController implements Serializable {

    private static final long serialVersionUID = -6467395004412840497L;

    @Autowired
    AutoBattleRepository autoBattleRepository;
    @Autowired
    AutoBattleLogRepository autoBattleLogRepository;
    @Autowired
    AutoBattleService autoBattleService;

    @GetMapping("/all")
    public ResponseEntity<List<AutoBattle>> getAllBattles() {
        List<AutoBattle> battles = autoBattleRepository.findAll();
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
        AutoBattle battle = autoBattleRepository.findById(id);
        if (battle.getAttackingPet().getOwner() != null) {
            battle.getAttackingPet().getOwner().setUserPassword(null);
        }
        if (battle.getDefendingPet().getOwner() != null) {
            battle.getDefendingPet().getOwner().setUserPassword(null);
            ;
        }
        return ResponseEntity.ok(battle);
    }

    @PostMapping("/new/pve/attackerId/{attackerId}/defenderId/{defenderId}")
    public ResponseEntity<AutoBattleDTO> createNewPVEBattle(@PathVariable int attackerId, @PathVariable int defenderId)
        throws BattleNotFoundException, PetNotFoundException {
        AutoBattleDTO battleResult = autoBattleService.battle(attackerId, defenderId, "pve");
        AutoBattle battle = battleResult.getBattle();
        if (battle.getAttackingPet().getOwner() != null) {
            battleResult.getBattle().getAttackingPet().getOwner().setUserPassword(null);
        }
        if (battle.getDefendingPet().getOwner() != null) {
            battleResult.getBattle().getDefendingPet().getOwner().setUserPassword(null);
        }
        return ResponseEntity.ok(battleResult);
    }

    @PostMapping("/new/pvp/attackerId/{attackerId}/defenderId/{defenderId}")
    public ResponseEntity<AutoBattleDTO> createNewPVPBattle(@PathVariable int attackerId, @PathVariable int defenderId)
        throws BattleNotFoundException, PetNotFoundException {
        AutoBattleDTO battleResult = autoBattleService.battle(attackerId, defenderId, "pvp");
        AutoBattle battle = battleResult.getBattle();
        if (battle.getAttackingPet().getOwner() != null) {
            battleResult.getBattle().getAttackingPet().getOwner().setUserPassword(null);
        }
        if (battle.getDefendingPet().getOwner() != null) {
            battleResult.getBattle().getDefendingPet().getOwner().setUserPassword(null);
        }
        return ResponseEntity.ok(battleResult);
    }
}
