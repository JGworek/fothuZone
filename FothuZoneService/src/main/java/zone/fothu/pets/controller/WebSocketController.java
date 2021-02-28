package zone.fothu.pets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import zone.fothu.pets.service.BattleService;
import zone.fothu.pets.service.UserService;

@Controller
public class WebSocketController {

	@Autowired
	BattleService battleService;
	@Autowired
	UserService userService;

	private final SimpMessagingTemplate template;

	@Autowired
	public WebSocketController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingPet/petId/{petId}/attack")
	public void attackWithAttackingPet(@DestinationVariable int battleId, @DestinationVariable int petId, @Payload String userId) {
		try {
			template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.attackingPetAttack(battleId, petId));
		} catch (Exception e) {
			template.convertAndSend("/errorMessageSubscription/" + userId, "Unable to attack with that pet");
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingPet/petId/{petId}/attack")
	public void attackWithDefendingPet(@DestinationVariable int battleId, @DestinationVariable int petId, @Payload String userId) {
		try {
			template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.defendingPetAttack(battleId, petId));
		} catch (Exception e) {
			template.convertAndSend("/errorMessageSubscription/" + userId, "Unable to attack with that pet");
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingPet/petId/{petId}/defend")
	public void defendWithAttackingPet(@DestinationVariable int battleId, @DestinationVariable int petId, @Payload String userId) {
		try {
			template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.attackingPetDefend(battleId, petId));
		} catch (Exception e) {
			template.convertAndSend("/errorMessageSubscription/" + userId, "Unable to defend with that pet");
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingPet/petId/{petId}/defend")
	public void defendWithDefendingPet(@DestinationVariable int battleId, @DestinationVariable int petId, @Payload String userId) {
		try {
			template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.defendingPetDefend(battleId, petId));
		} catch (Exception e) {
			template.convertAndSend("/errorMessageSubscription/" + userId, "Unable to defend with that pet");
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingPet/petId/{petId}/aim")
	public void aimWithAttackingPet(@DestinationVariable int battleId, @DestinationVariable int petId, @Payload String userId) {
		try {
			template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.attackingPetAim(battleId, petId));
		} catch (Exception e) {
			template.convertAndSend("/errorMessageSubscription/" + userId, "Unable to aim with that pet");
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingPet/petId/{petId}/aim")
	public void aimWithDefendingPet(@DestinationVariable int battleId, @DestinationVariable int petId, @Payload String userId) {
		try {
			template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.defendingPetAim(battleId, petId));
		} catch (Exception e) {
			template.convertAndSend("/errorMessageSubscription/" + userId, "Unable to aim with that pet");
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingPet/petId/{petId}/sharpen")
	public void sharpenWithAttackingPet(@DestinationVariable int battleId, @DestinationVariable int petId, @Payload String userId) {
		try {
			template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.attackingPetSharpen(battleId, petId));
		} catch (Exception e) {
			template.convertAndSend("/errorMessageSubscription/" + userId, "Unable to sharpen with that pet");
		}
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingPet/petId/{petId}/sharpen")
	public void sharpenWithDefendingPet(@DestinationVariable int battleId, @DestinationVariable int petId, @Payload String userId) {
		try {
			template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.defendingPetSharpen(battleId, petId));
		} catch (Exception e) {
			template.convertAndSend("/errorMessageSubscription/" + userId, "Unable to sharpen with that pet");
		}
	}

	@MessageMapping("/battles/pvp/battleId/{battleId}/setAttackingPetId/{attackingPetId}/")
	public void setAttackingPet(@DestinationVariable int battleId, @DestinationVariable int attackingPetId, @Payload String userId) {
		try {
			template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.updateBattleWithAttackingPet(battleId, attackingPetId));
		} catch (Exception e) {
			System.out.println("hey look they probably did something wrong");
		}
	}

	@MessageMapping("/battles/pvp/battleId/{battleId}/setDefendingPetId/{defendingPetId}/")
	public void setDefendingPet(@DestinationVariable int battleId, @DestinationVariable int defendingPetId, @Payload String userId) {
		try {
			template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.updateBattleWithDefendingPet(battleId, defendingPetId));
		} catch (Exception e) {
			System.out.println("hey look they probably did something wrong");
		}
	}

	public void updateLoggedInUser(int userId) {
		try {
			template.convertAndSend("/userSubscription/" + userId, userService.getUserWithId(userId));
		} catch (Exception e) {
			System.out.println("hey look they probably did something wrong");
		}
	}
}