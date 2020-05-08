package zone.fothu.pets.controller;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import zone.fothu.cyoa.exception.PageNotFoundException;
import zone.fothu.pets.exception.PetNotFoundException;
import zone.fothu.pets.exception.PetNotUpdatedException;
import zone.fothu.pets.model.Pet;
import zone.fothu.pets.repository.PetRepository;

@RestController
@CrossOrigin
@RequestMapping(path="/pets")
public class PetController {
	@Autowired
	PetRepository petRepository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Pet>> getAllPets() {
		try {
			return ResponseEntity.ok(petRepository.findAll());
		} catch (PageNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Pets not found", e);
		}
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Pet> getPetById(@PathVariable int id) throws PetNotFoundException {
		try {
			return ResponseEntity.ok(petRepository.findById(id));
		} catch (PageNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Pet not found", e);
		}
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<Pet> getUserByUsername(@PathVariable String name) throws PetNotFoundException {
		try {
			return ResponseEntity.ok(petRepository.findByPetName(name));
		} catch (PageNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Pet not found", e);
		}
	}
	
	@GetMapping("/userid/{id}")
	public ResponseEntity<List<Pet>> getPetByUserId(@PathVariable int id) throws PetNotFoundException {
		try {
			return ResponseEntity.ok(petRepository.findAllUsersPetsById(id));
		} catch (PageNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Pet not found", e);
		}
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<List<Pet>> getPetByUsername(@PathVariable String username) throws PetNotFoundException {
		try {
			return ResponseEntity.ok(petRepository.findAllUsersPetsByUsername(username));
		} catch (PageNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Pet not found", e);
		}
	}
	
	@PostMapping("/new")
	public Pet createPet(@RequestBody Pet newPet) {
		try {
			return petRepository.save(newPet);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Pet not created", e);
		}
	}

	@PatchMapping("/update")
	public Pet updatePet(@RequestBody Pet updatedPet) throws PetNotFoundException, PSQLException {
		boolean success = false;
		try {
			petRepository.updatePet(updatedPet.getId(), updatedPet.getName(), updatedPet.getImage(), updatedPet.getType(), updatedPet.getHunger(), updatedPet.getCurrentHealth(), updatedPet.getMaxHealth(), updatedPet.getStrength(), updatedPet.getDexterity(), updatedPet.getIntelligence(), updatedPet.getPetLevel());
			success = true;
		} catch (PetNotUpdatedException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Pet not updated", e);
		}
		if (success == true) {
			return petRepository.findById(updatedPet.getId());
		} else {
			return updatedPet;
		}
	}
	
	@PatchMapping("/giveto/{id}")
	public Pet givePet(@RequestBody Pet tradedPet, @PathVariable int id) throws PetNotFoundException, PSQLException{
		boolean success = false;
		try {
			petRepository.givePet(tradedPet.getId(), tradedPet.getName(), tradedPet.getImage(), tradedPet.getType(), tradedPet.getHunger(), tradedPet.getCurrentHealth(), tradedPet.getMaxHealth(), tradedPet.getStrength(), tradedPet.getDexterity(), tradedPet.getIntelligence(), tradedPet.getPetLevel(), id);
			success = true;
		} catch (PetNotUpdatedException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Pet not updated", e);
		}
		if (success == true) {
			return petRepository.findById(tradedPet.getId());
		} else {
			return tradedPet;
		}
	}
	
}
