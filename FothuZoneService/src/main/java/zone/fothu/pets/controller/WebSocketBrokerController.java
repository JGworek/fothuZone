package zone.fothu.pets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import zone.fothu.pets.service.BattleService;
import zone.fothu.pets.service.UserService;

@Controller
public class WebSocketBrokerController {

	@Autowired
	BattleService battleService;
	@Autowired
	UserService userService;

}
