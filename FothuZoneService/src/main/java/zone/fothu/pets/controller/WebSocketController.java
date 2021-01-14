package zone.fothu.pets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import zone.fothu.pets.service.BattleService;

@Controller
public class WebSocketController {

	@Autowired
	BattleService battleService;

	private final SimpMessagingTemplate template;

	@Autowired
	public WebSocketController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingPet/petId/{petId}/attack")
	public void attackWithAttackingPet(@DestinationVariable int battleId, @DestinationVariable int petId) {
		try {
			template.convertAndSend("/battle/battleId/{battleId}", battleService.attackingPetAttack(battleId, petId));
		} catch (Exception e) {
			// hey look they did something wrong
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingPet/petId/{petId}/attack")
	public void attackWithDefendingPet(@DestinationVariable int battleId, @DestinationVariable int petId) {
		try {
			template.convertAndSend("/battle/battleId/{battleId}", battleService.defendingPetAttack(battleId, petId));
		} catch (Exception e) {
			// hey look they did something wrong
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingPet/petId/{petId}/defend")
	public void defendWithAttackingPet(@DestinationVariable int battleId, @DestinationVariable int petId) {
		try {
			template.convertAndSend("/battle/battleId/{battleId}", battleService.attackingPetDefend(battleId, petId));
		} catch (Exception e) {
			// hey look they did something wrong
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingPet/petId/{petId}/defend")
	public void defendWithDefendingPet(@DestinationVariable int battleId, @DestinationVariable int petId) {
		try {
			template.convertAndSend("/battle/battleId/{battleId}", battleService.defendingPetDefend(battleId, petId));
		} catch (Exception e) {
			// hey look they did something wrong
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingPet/petId/{petId}/aim")
	public void aimWithAttackingPet(@DestinationVariable int battleId, @DestinationVariable int petId) {
		try {
			template.convertAndSend("/battle/battleId/{battleId}", battleService.attackingPetAim(battleId, petId));
		} catch (Exception e) {
			// hey look they did something wrong
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingPet/petId/{petId}/aim")
	public void aimWithDefendingPet(@DestinationVariable int battleId, @DestinationVariable int petId) {
		try {
			template.convertAndSend("/battle/battleId/{battleId}", battleService.defendingPetAim(battleId, petId));
		} catch (Exception e) {
			// hey look they did something wrong
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingPet/petId/{petId}/sharpen")
	public void sharpenWithAttackingPet(@DestinationVariable int battleId, @DestinationVariable int petId) {
		try {
			template.convertAndSend("/battle/battleId/{battleId}", battleService.attackingPetSharpen(battleId, petId));
		} catch (Exception e) {
			// hey look they did something wrong
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingPet/petId/{petId}/sharpen")
	public void sharpenWithDefendingPet(@DestinationVariable int battleId, @DestinationVariable int petId) {
		try {
			template.convertAndSend("/battle/battleId/{battleId}", battleService.defendingPetSharpen(battleId, petId));
		} catch (Exception e) {
			// hey look they did something wrong
		}
	}

}
