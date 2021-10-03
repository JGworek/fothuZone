package zone.fothu.pets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.exception.WrongBattlePetException;
import zone.fothu.pets.service.BattleService;
import zone.fothu.pets.service.UserService;

@Controller
public class WebSocketController {

	private final BattleService battleService;
	private final UserService userService;
	private final SimpMessagingTemplate template;

	@Autowired
	public WebSocketController(@Lazy BattleService battleService, @Lazy UserService userService, SimpMessagingTemplate template) {
		super();
		this.battleService = battleService;
		this.userService = userService;
		this.template = template;
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingUser/userId/{userId}/attack")
	public void attackWithAttackingPet(@DestinationVariable long battleId, @DestinationVariable long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.attackingPetAttack(battleId, userId));
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingUser/userId/{userId}/attack")
	public void attackWithDefendingPet(@DestinationVariable long battleId, @DestinationVariable long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.defendingPetAttack(battleId, userId));
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingUser/userId/{userId}/defend")
	public void defendWithAttackingPet(@DestinationVariable long battleId, @DestinationVariable long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.attackingPetDefend(battleId, userId));
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingUser/userId/{userId}/defend")
	public void defendWithDefendingPet(@DestinationVariable long battleId, @DestinationVariable long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.defendingPetDefend(battleId, userId));
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingUser/userId/{userId}/aim")
	public void aimWithAttackingPet(@DestinationVariable long battleId, @DestinationVariable long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.attackingPetAim(battleId, userId));
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingUser/userId/{userId}/aim")
	public void aimWithDefendingPet(@DestinationVariable long battleId, @DestinationVariable long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.defendingPetAim(battleId, userId));
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingUser/userId/{userId}/sharpen")
	public void sharpenWithAttackingPet(@DestinationVariable long battleId, @DestinationVariable long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.attackingPetSharpen(battleId, userId));
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingUser/userId/{userId}/sharpen")
	public void sharpenWithDefendingPet(@DestinationVariable long battleId, @DestinationVariable long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.defendingPetSharpen(battleId, userId));
	}

	@MessageMapping("/battles/battleId/{battleId}/attackingUser/userId/{userId}/evade")
	public void evadeWithAttackingPet(@DestinationVariable long battleId, @DestinationVariable long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.attackingPetEvade(battleId, userId));
	}

	@MessageMapping("/battles/battleId/{battleId}/defendingUser/userId/{userId}/evade")
	public void evadeWithDefendingPet(@DestinationVariable long battleId, @DestinationVariable long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.defendingPetEvade(battleId, userId));
	}

	@MessageMapping("/battles/battleId/{battleId}/setStartingAttackingPet/{attackingPetId}")
	public void setStartingAttackingPet(@DestinationVariable long battleId, @DestinationVariable long attackingPetId, @Payload String userId) throws WrongBattlePetException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.setStartingAttackingBattlePet(battleId, attackingPetId));
	}

	@MessageMapping("/battles/battleId/{battleId}/setStartingDefendingPet/{defendingPetId}")
	public void setStartingDefendingPet(@DestinationVariable long battleId, @DestinationVariable long defendingPetId, @Payload String userId) throws WrongBattlePetException {
		System.out.println("2");
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.setStartingDefendingBattlePet(battleId, defendingPetId));
	}

	@MessageMapping("/battles/battleId/{battleId}/setAttackingPet/{attackingPetId}")
	public void setAttackingPet(@DestinationVariable long battleId, @DestinationVariable long attackingPetId, @Payload String userId) throws WrongBattlePetException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.setAttackingBattlePet(battleId, attackingPetId));
	}

	@MessageMapping("/battles/battleId/{battleId}/setDefendingPet/{defendingPetId}")
	public void setDefendingPet(@DestinationVariable long battleId, @DestinationVariable long defendingPetId, @Payload String userId) throws WrongBattlePetException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.setDefendingBattlePet(battleId, defendingPetId));
	}

	@MessageMapping("/battles/battleId/{battleId}/swapAttackingPet/{attackingPetId}")
	public void swapAttackingPet(@DestinationVariable long battleId, @DestinationVariable long attackingPetId, @Payload String userId) throws MessagingException, WrongBattlePetException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.swapAttackingPet(battleId, attackingPetId));
	}

	@MessageMapping("/battles/battleId/{battleId}/swapDefendingPet/{defendingPetId}")
	public void swapDefendingPet(@DestinationVariable long battleId, @DestinationVariable long defendingPetId, @Payload String userId) throws MessagingException, WrongBattlePetException {
		template.convertAndSend("/battleSubscription/battleId/" + battleId, battleService.swapDefendingPet(battleId, defendingPetId));
	}

	@MessageMapping("/battles/battleId/{battleId}/replaceDeadAttackingPet/{deadAttackingPetId}/newAttackingPet/{newAttackingPetId}")
	public void replaceDeadAttackingPet(@DestinationVariable long battleId, @DestinationVariable long deadAttackingPetId, @DestinationVariable long newAttackingPetId, @Payload String userId) throws MessagingException, WrongBattlePetException {
		template.convertAndSend("/battleSubscription/battleId" + battleId, battleService.replaceDeadAttackingPet(battleId, deadAttackingPetId, newAttackingPetId));
	}

	@MessageMapping("/battles/battleId/{battleId}/replaceDeadAttackingPet/{deadAttackingPetId}/newDefendingPet/{newDefendingPetId}")
	public void replaceDeadDefendingPet(@DestinationVariable long battleId, @DestinationVariable long deadDefendingPetId, @DestinationVariable long newDefendingPetId, @Payload String userId) throws MessagingException, WrongBattlePetException {
		template.convertAndSend("/battleSubscription/battleId" + battleId, battleService.replaceDeadDefendingPet(battleId, deadDefendingPetId, newDefendingPetId));
	}

	public void updateLoggedInUser(long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/userSubscription/" + userId, userService.getUserWithId(userId));
	}
}