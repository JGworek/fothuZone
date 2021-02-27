package zone.fothu.pets.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import zone.fothu.pets.exception.PetNotFoundException;
import zone.fothu.pets.model.profile.Image;
import zone.fothu.pets.model.profile.Pet;
import zone.fothu.pets.repository.ImageRepository;
import zone.fothu.pets.repository.PetRepository;
import zone.fothu.pets.service.ImageService;

@RestController
@CrossOrigin
@RequestMapping(path = "/images")
public class ImageController implements Serializable {

	private static final long serialVersionUID = -6651376789756065729L;

	@Autowired
	ImageRepository imageRepository;
	@Autowired
	PetRepository petRepository;
	@Autowired
	ImageService imageService;

	@GetMapping("/all")
	public ResponseEntity<List<Image>> getAllImages() {
		return ResponseEntity.ok(imageRepository.getAllImages());
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Image> getImageWithId(@PathVariable int id) {
		Image image = imageRepository.findImageById(id);
		return ResponseEntity.ok(image);
	}

	@GetMapping("/new")
	public ResponseEntity<Image> createPetImage(@RequestBody Image newImage) {
		Image image = imageService.saveNewImage(newImage);
		return ResponseEntity.ok(image);
	}

	@PostMapping("set/petId/{petId}/imageId/{imageId}")
	public ResponseEntity<Pet> addPetImage(@PathVariable int petId, @PathVariable int imageId) throws PetNotFoundException {
		imageRepository.setPetImage(petId, imageId);
		Pet pet = petRepository.findById(petId);
		if (pet.getOwner() != null) {
			pet.getOwner().setUserPassword(null);
		}
		return ResponseEntity.ok(pet);
	}

	// images/some?numberOfImages=#
	@GetMapping("/some")
	public ResponseEntity<List<Image>> getSomeRandomPetImages(@RequestParam int numberOfImages) {
		return ResponseEntity.ok(imageService.getRandomImagesOfCertainNumber(numberOfImages));
	}

}
