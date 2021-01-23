package zone.fothu.pets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zone.fothu.pets.model.profile.SupportedColor;
import zone.fothu.pets.repository.SupportedColorRepository;

@RestController
@CrossOrigin
@RequestMapping(path = "/supportedColors")
public class SupportedColorController {

	@Autowired
	SupportedColorRepository supportedColorRespository;

	@GetMapping("/all")
	public ResponseEntity<List<SupportedColor>> getAllSupportedColors() {
		return ResponseEntity.ok(supportedColorRespository.getAll());
	}

}