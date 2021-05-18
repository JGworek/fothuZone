package zone.fothu.pets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.service.BattleService;
import zone.fothu.pets.service.RequestService;
import zone.fothu.pets.service.UserService;

@Controller
public class WebSocketBrokerController {

	private final BattleService battleService;
	private final UserService userService;
	private final RequestService requestService;
	private final SimpMessagingTemplate template;

	@Autowired
	public WebSocketBrokerController(@Lazy BattleService battleService, @Lazy UserService userService, @Lazy RequestService requestService, SimpMessagingTemplate template) {
		super();
		this.battleService = battleService;
		this.userService = userService;
		this.requestService = requestService;
		this.template = template;
	}

	public void updateChallengeSubscription(long defenderId) throws MessagingException {
		template.convertAndSend("/challengeSubscription/" + defenderId, requestService.getAllChallengeRequestsForUser(defenderId));
	}

	public void updateCurrentBattleSubscription(long attackerId, long defenderId) throws MessagingException {
		template.convertAndSend("/currentBattlesSubscription/" + attackerId, requestService.getAllCurrentPVPBattlesForUser(attackerId));
		template.convertAndSend("/currentBattlesSubscription/" + defenderId, requestService.getAllCurrentPVPBattlesForUser(defenderId));
	}

	public void updateUserSubscription(long userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/userSubscription/" + userId, userService.getUserWithId(userId));
	}

	public void sendErrorMessage(long userId, String errorMessage) {
		template.convertAndSend("/errorMessageSubscription/" + userId, errorMessage);
	}
}