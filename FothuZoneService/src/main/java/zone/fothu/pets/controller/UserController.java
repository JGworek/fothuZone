package zone.fothu.pets.controller;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.exception.UserNotUpdatedException;
import zone.fothu.pets.model.User;
import zone.fothu.pets.repository.UserRepository;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			return ResponseEntity.ok(userRepository.findAll());
		} catch (RuntimeException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Users not found", e);
		}
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		try {
			return ResponseEntity.ok(userRepository.findById(id));
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "User not found", e);
		}
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		try {
			return ResponseEntity.ok(userRepository.findByUsername(username));
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "User not found", e);
		}
	}

	@GetMapping("/username/{username}/password/{password}")
	public ResponseEntity<User> getUserByUsernameAndPassword(String username, String password) {
		try {
			return ResponseEntity.ok(userRepository.findByUsernameAndPassword(username, password));
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "User not found", e);
		}
	}

	@PostMapping("/new")
	public User createUser(@RequestBody User newUser) {
		try {
			return userRepository.save(newUser);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "User not created", e);
		}
	}

	@PatchMapping("/update")
	public User updateUser(@RequestBody User updatedUser) throws UserNotFoundException, PSQLException {
		boolean success = false;
		try {
			userRepository.updateUser(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getUserPassword(),
					updatedUser.getFavoriteColor());
			success = true;
		} catch (UserNotUpdatedException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "User not updated", e);
		}
		if (success == true) {
			return userRepository.findById(updatedUser.getId());
		} else {
			return updatedUser;
		}
	}

}
