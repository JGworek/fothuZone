package zone.fothu.pets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.repository.BattleDTORepository;
import zone.fothu.pets.service.BattleService;
import zone.fothu.pets.service.RequestService;
import zone.fothu.pets.service.UserService;

@Controller
public class WebSocketBrokerController {

	@Autowired
	BattleService battleService;
	@Autowired
	UserService userService;
	@Autowired
	BattleDTORepository battleDTORepsitory;
	@Autowired
	RequestService requestService;

	private final SimpMessagingTemplate template;

	@Autowired
	public WebSocketBrokerController(SimpMessagingTemplate template) {
		this.template = template;
	}

	public void updateChallengeSubscription(int defenderId) throws MessagingException {
		template.convertAndSend("/challengeSubscription/" + defenderId, requestService.getAllChallengeRequestsForUser(defenderId));
	}

	public void updateCurrentBattleSubscription(int attackerId, int defenderId) throws MessagingException {
		template.convertAndSend("/currentBattlesSubscription/" + attackerId, requestService.getAllCurrentPVPBattlesForUser(attackerId));
		template.convertAndSend("/currentBattlesSubscription/" + defenderId, requestService.getAllCurrentPVPBattlesForUser(defenderId));
	}

	public void updateUserSubscription(int userId) throws MessagingException, UserNotFoundException {
		template.convertAndSend("/userSubscription/" + userId, userService.getUserWithId(userId));
	}

	public void sendErrorMessage(int userId, String errorMessage) {
		template.convertAndSend("/errorMessageSubscription/" + userId, errorMessage);
	}
}