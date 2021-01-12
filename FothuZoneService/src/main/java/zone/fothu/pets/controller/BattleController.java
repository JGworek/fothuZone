package zone.fothu.pets.controller;

import java.io.Serializable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = "/battles")
public class BattleController implements Serializable {

    private static final long serialVersionUID = 7455066211780881774L;

}
