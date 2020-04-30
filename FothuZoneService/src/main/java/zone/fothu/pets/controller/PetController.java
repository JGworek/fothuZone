package zone.fothu.pets.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import zone.fothu.cyoa.exception.PageNotFoundException;
import zone.fothu.pets.model.Pet;
import zone.fothu.pets.repository.PetRepository;

@RestController
@RequestMapping(path="/pets")
public class PetController {
	@Autowired
	PetRepository petRepository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Pet>> getAllPets() {
		try {
			//logger.info("All pages retrieved");
			return ResponseEntity.ok(petRepository.findAll());
		} catch (PageNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pets not found", e);
		}
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Pet> getPetById(@PathVariable int id) {
		try {
			//logger.info("Page #"+id+" retrieved");
			return ResponseEntity.ok(petRepository.findById(id));
		} catch (PageNotFoundException e) {
			//logger.info("Page #"+id+" not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found", e);
		}
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<Pet> getUserByUsername(@PathVariable String name) {
		try {
			//logger.info("Page #"+id+" retrieved");
			return ResponseEntity.ok(petRepository.findByPetName(name));
		} catch (PageNotFoundException e) {
			//logger.info("Page #"+id+" not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found", e);
		}
	}
	
	@GetMapping("/userid/{id}")
	public ResponseEntity<List<Pet>> getPetByUserId(@PathVariable int id) {
		try {
			//logger.info("Page #"+id+" retrieved");
			return ResponseEntity.ok(petRepository.findAllUsersPetsById(id));
		} catch (PageNotFoundException e) {
			//logger.info("Page #"+id+" not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found", e);
		}
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<List<Pet>> getPetByUsername(@PathVariable String username) {
		try {
			//logger.info("Page #"+id+" retrieved");
			return ResponseEntity.ok(petRepository.findAllUsersPetsByUsername(username));
		} catch (PageNotFoundException e) {
			//logger.info("Page #"+id+" not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found", e);
		}
	}
	
}
