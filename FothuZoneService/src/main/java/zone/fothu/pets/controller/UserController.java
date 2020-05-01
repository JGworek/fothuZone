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
import zone.fothu.pets.model.User;
import zone.fothu.pets.repository.PetRepository;
import zone.fothu.pets.repository.UserRepository;

@RestController
@RequestMapping(path="/users")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			//logger.info("All pages retrieved");
			return ResponseEntity.ok(userRepository.findAll());
		} catch (PageNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users not found", e);
		}
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		try {
			//logger.info("Page #"+id+" retrieved");
			return ResponseEntity.ok(userRepository.findById(id));
		} catch (PageNotFoundException e) {
			//logger.info("Page #"+id+" not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
		}
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		try {
			//logger.info("Page #"+id+" retrieved");
			return ResponseEntity.ok(userRepository.findByUsername(username));
		} catch (PageNotFoundException e) {
			//logger.info("Page #"+id+" not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
		}
	}
	
	@GetMapping("/username/{username}/password/{password}")
	public ResponseEntity<User> getUserByUsernameAndPassword(String username, String password) {
		try {
			//logger.info("Page #"+id+" retrieved");
			return ResponseEntity.ok(userRepository.findByUsernameAndPassword(username, password));
		} catch (PageNotFoundException e) {
			//logger.info("Page #"+id+" not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
		}
	}
	
	
}
