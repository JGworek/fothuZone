package zone.fothu.pets.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import zone.fothu.pets.model.profile.Pet;
import zone.fothu.pets.repository.ImageRepository;
import zone.fothu.pets.repository.PetRepository;
import zone.fothu.pets.repository.UserRepository;
import zone.fothu.pets.service.PetService;

@RestController
@CrossOrigin
@RequestMapping(path = "/pets")
public class PetController implements Serializable {

	private static final long serialVersionUID = 458928494736944980L;

	private final PetRepository petRepository;
	private final UserRepository userRepository;
	private final ImageRepository imageRepository;
	private final WebSocketBrokerController webSocketBrokerController;
	private final PetService petService;

	@Autowired
	public PetController(PetRepository petRepository, UserRepository userRepository, ImageRepository imageRepository, WebSocketBrokerController webSocketBrokerController, @Lazy PetService petService) {
		super();
		this.petRepository = petRepository;
		this.userRepository = userRepository;
		this.imageRepository = imageRepository;
		this.webSocketBrokerController = webSocketBrokerController;
		this.petService = petService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<Pet>> getAllPets() {
		List<Pet> pets = petRepository.findAll();
		for (Pet pet : pets) {
			if (pet.getOwner() != null) {
				pet.getOwner().setUserPassword(null);
			}
		}
		return ResponseEntity.ok(pets);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Pet> getPetById(@PathVariable long id) {
		Pet pet = petRepository.findById(id).get();
		if (pet.getOwner() != null) {
			pet.getOwner().setUserPassword(null);
		}
		return ResponseEntity.ok(pet);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<Pet> getPetByName(@PathVariable String name) {
		Pet pet = petRepository.findByPetName(name);
		if (pet.getOwner() != null) {
			pet.getOwner().setUserPassword(null);
		}
		return ResponseEntity.ok(pet);
	}

	@GetMapping("/userid/{id}")
	public ResponseEntity<List<Pet>> getPetByUserId(@PathVariable long id) {
		List<Pet> pets = petRepository.findAllUsersPetsById(id);
		for (Pet pet : pets) {
			if (pet.getOwner() != null) {
				pet.getOwner().setUserPassword(null);
			}
		}
		return ResponseEntity.ok(pets);
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<List<Pet>> getPetByUsername(@PathVariable String username) {
		List<Pet> pets = petRepository.findAllUsersPetsByUsername(username);
		for (Pet pet : pets) {
			if (pet.getOwner() != null) {
				pet.getOwner().setUserPassword(null);
			}
		}
		return ResponseEntity.ok(pets);
	}

	@PostMapping("/new")
	public ResponseEntity<Pet> createPet(@RequestBody Pet newPet) {
		Pet pet = petRepository.save(newPet);
		if (pet.getOwner() != null) {
			pet.getOwner().setUserPassword(null);

		}
		return ResponseEntity.ok(pet);
	}

	@PutMapping("/restoreHealth/all")
	public ResponseEntity<List<Pet>> restoreAllPetsHealth() {
		petRepository.restoreAllPetsHealth();
		List<Pet> pets = petRepository.findAll();
		for (Pet pet : pets) {
			if (pet.getOwner() != null) {
				pet.getOwner().setUserPassword(null);
			}
		}
		return ResponseEntity.ok(pets);
	}

	@PutMapping("/restoreHealth/pet/{id}")
	public ResponseEntity<Pet> restoreOnePetsHealth(@PathVariable long id) {
		petRepository.restoreOnePetsHealth(id);
		Pet pet = petRepository.findById(id).get();
		if (pet.getOwner() != null) {
			pet.getOwner().setUserPassword(null);
		}
		return ResponseEntity.ok(pet);
	}

	@PutMapping("/restoreHealth/user/id/{id}")
	public ResponseEntity<List<Pet>> restoreAllUsersPetsHealth(@PathVariable long id) {
		petRepository.restoreAllUsersPetsHealth(id);
		List<Pet> pets = petRepository.findAllPetsByUserId(id);
		for (Pet pet : pets) {
			if (pet.getOwner() != null) {
				pet.getOwner().setUserPassword(null);
			}
		}
		return ResponseEntity.ok(pets);
	}

	@PutMapping("/restoreHealth/user/username/{userName}")
	public ResponseEntity<List<Pet>> restoreAllUsersPetsHealthWithUsername(@PathVariable String userName) {
		petRepository.restoreAllUsersPetsHealth(userRepository.findByUsername(userName).getId());
		List<Pet> pets = petRepository.findAllUsersPetsByUsername(userName);
		for (Pet pet : pets) {
			if (pet.getOwner() != null) {
				pet.getOwner().setUserPassword(null);
			}
		}
		return ResponseEntity.ok(pets);
	}

	@PutMapping("/restoreHealth/pet/petName/{petName}")
	public ResponseEntity<Pet> restoresPetsHealthWithPetsName(@PathVariable String petName) {
		petRepository.restoreOnePetsHealth(petRepository.findByPetName(petName).getId());
		Pet pet = petRepository.findById(petRepository.findByPetName(petName).getId()).get();
		if (pet.getOwner() != null) {
			pet.getOwner().setUserPassword(null);
		}
		return ResponseEntity.ok(pet);
	}

	// HOPEFULLY THIS WORKS
	@CrossOrigin(origins = "http://fothu.zone")
	@PutMapping("/id/{petId}/strength/{strength}/agility/{agility}/intelligence/{intelligence}")
	public ResponseEntity<Pet> updatePetWithNewStats(@PathVariable long id, @PathVariable int strength, @PathVariable int agility, @PathVariable int intelligence) {
		petRepository.setPetStats(id, strength, agility, intelligence);
		Pet pet = petRepository.findById(id).get();
		if (pet.getOwner() != null) {
			pet.getOwner().setUserPassword(null);
		}
		return ResponseEntity.ok(pet);
	}

	// pets/levelUp/petId/{petId}?highStat={highStat}&mediumStat={mediumStat}&lowStat={lowStat}
	@PutMapping("/levelUp/petId/{petId}")
	public ResponseEntity<Pet> levelUpPet(@PathVariable long petId, @RequestParam String highStat, @RequestParam String mediumStat, @RequestParam String lowStat) {
		try {
			Pet leveledUpPet = petService.levelUpPet(petId, highStat, mediumStat, lowStat);
			webSocketBrokerController.updateUserSubscription(leveledUpPet.getOwner().getId());
			return ResponseEntity.ok(leveledUpPet);
		} catch (Exception e) {
			Pet failedToLevelUpPet = petRepository.findById(petId).get();
			webSocketBrokerController.sendErrorMessage(failedToLevelUpPet.getOwner().getId(), "Failed to level up " + failedToLevelUpPet.getName());
		}
		return null;
	}
}
