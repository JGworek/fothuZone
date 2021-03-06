package zone.fothu.pets.controller;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zone.fothu.pets.exception.PetNotFoundException;
import zone.fothu.pets.exception.PetNotUpdatedException;
import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.model.Image;
import zone.fothu.pets.model.Pet;
import zone.fothu.pets.repository.ImageRepository;
import zone.fothu.pets.repository.PetRepository;
import zone.fothu.pets.repository.UserRepository;
import zone.fothu.pets.service.ImageService;

@RestController
@CrossOrigin
@RequestMapping(path = "/pets")
public class PetController {

    @Autowired
    PetRepository petRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ImageService imageService;

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
    public ResponseEntity<Pet> getPetById(@PathVariable int id) throws PetNotFoundException {
        Pet pet = petRepository.findById(id);
        if (pet.getOwner() != null) {
            pet.getOwner().setUserPassword(null);
        }
        return ResponseEntity.ok(pet);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Pet> getPetByName(@PathVariable String name) throws PetNotFoundException {
        Pet pet = petRepository.findByPetName(name);
        if (pet.getOwner() != null) {
            pet.getOwner().setUserPassword(null);
        }
        return ResponseEntity.ok(pet);
    }

    @GetMapping("/userid/{id}")
    public ResponseEntity<List<Pet>> getPetByUserId(@PathVariable int id) throws PetNotFoundException {
        List<Pet> pets = petRepository.findAllUsersPetsById(id);
        for (Pet pet : pets) {
            if (pet.getOwner() != null) {
                pet.getOwner().setUserPassword(null);
            }
        }
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<Pet>> getPetByUsername(@PathVariable String username) throws PetNotFoundException {
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
    
    @GetMapping("/image/id/{id}")
    public ResponseEntity<Image> getImageWithId(@PathVariable int id) {
        Image image = imageRepository.findImageById(id);
        return ResponseEntity.ok(image);
    }
    
    @PostMapping("/image/new")
    public ResponseEntity<Image> createPetImage(@RequestBody Image newImage) {
        Image image = imageService.saveNewImage(newImage);
        return ResponseEntity.ok(image);
    }
  
    @PostMapping("/id/{petId}/image/{imageId}")
    public ResponseEntity<Pet> addPetImage(@PathVariable int petId, @PathVariable int imageId) throws PetNotFoundException {
    imageRepository.setPetImage(petId, imageId);
    Pet pet = petRepository.findById(petId);
    if (pet.getOwner() != null) {
        pet.getOwner().setUserPassword(null);
    }
    return ResponseEntity.ok(pet);
    }

    @PutMapping("/update")
    public ResponseEntity<Pet> updatePet(@RequestBody Pet updatedPet)
        throws PetNotFoundException, PSQLException, PetNotUpdatedException {
        boolean success = false;
        petRepository.updatePet(updatedPet.getId(), updatedPet.getName(), updatedPet.getType(),
            updatedPet.getHunger(), updatedPet.getCurrentHealth(), updatedPet.getMaxHealth(), updatedPet.getStrength(),
            updatedPet.getAgility(), updatedPet.getIntelligence(), updatedPet.getPetLevel(), updatedPet.getCurrentXP());

        Pet pet = petRepository.findById(updatedPet.getId());
        if (pet.getOwner() != null) {
            pet.getOwner().setUserPassword(null);

        }
        return ResponseEntity.ok(pet);
    }

//    @PutMapping("/giveto/{id}")
//    public ResponseEntity<Pet> givePet(@RequestBody Pet tradedPet, @PathVariable int id)
//        throws PetNotFoundException, PSQLException, PetNotUpdatedException {
//        boolean success = false;
//        petRepository.givePet(tradedPet.getId(), tradedPet.getName(), tradedPet.getType(),
//            tradedPet.getHunger(), tradedPet.getCurrentHealth(), tradedPet.getMaxHealth(), tradedPet.getStrength(),
//            tradedPet.getAgility(), tradedPet.getIntelligence(), tradedPet.getPetLevel(), tradedPet.getCurrentXP(), id);
//        Pet pet = petRepository.findById(tradedPet.getId());
//        if (pet.getOwner() != null) {
//            pet.getOwner().setUserPassword(null);
//
//        }
//        return ResponseEntity.ok(pet);
//    }

    @PutMapping("/restoreHealth/all")
    public ResponseEntity<List<Pet>> restoreAllPetsHealth() throws PetNotUpdatedException {
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
    public ResponseEntity<Pet> restoreOnePetsHealth(@PathVariable int id)
        throws PetNotUpdatedException, PetNotFoundException {
        petRepository.restoreOnePetsHealth(id);
        Pet pet = petRepository.findById(id);
        if (pet.getOwner() != null) {
            pet.getOwner().setUserPassword(null);
        }
        return ResponseEntity.ok(pet);
    }

    @PutMapping("/restoreHealth/user/id/{id}")
    public ResponseEntity<List<Pet>> restoreAllUsersPetsHealth(@PathVariable int id) {
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
    public ResponseEntity<List<Pet>> restoreAllUsersPetsHealthWithUsername(@PathVariable String userName)
        throws PetNotFoundException, UserNotFoundException {
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
    public ResponseEntity<Pet> restoresPetsHealthWithPetsName(@PathVariable String petName)
        throws PetNotFoundException, UserNotFoundException {
        petRepository.restoreOnePetsHealth(petRepository.findByPetName(petName).getId());
        Pet pet = petRepository.findById(petRepository.findByPetName(petName).getId());
        if (pet.getOwner() != null) {
            pet.getOwner().setUserPassword(null);
        }
        return ResponseEntity.ok(pet);
    }

    // HOPEFULLY THIS WORKS
    @CrossOrigin(origins = "http://fothu.zone")
    @PutMapping("/id/{petId}/strength/{strength}/agility/{agility}/intelligence/{intelligence}")
    public ResponseEntity<Pet> updatePetWithNewStats(@PathVariable int id, @PathVariable int strength,
        @PathVariable int agility, @PathVariable int intelligence) throws PetNotFoundException {
        petRepository.setPetStats(id, strength, agility, intelligence);
        Pet pet = petRepository.findById(id);
        if (pet.getOwner() != null) {
            pet.getOwner().setUserPassword(null);
        }
        return ResponseEntity.ok(pet);
    }
}
