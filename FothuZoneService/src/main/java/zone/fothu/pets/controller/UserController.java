package zone.fothu.pets.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.exception.UserNotUpdatedException;
import zone.fothu.pets.model.profile.User;
import zone.fothu.pets.model.profile.UserDTO;
import zone.fothu.pets.repository.UserRepository;
import zone.fothu.pets.service.UserService;

@RestController
@CrossOrigin
@RequestMapping(path = "/users")
public class UserController implements Serializable {

	private static final long serialVersionUID = -6621821132473638274L;

	private final UserService userService;
	private UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository, @Lazy UserService userService) {
		super();
		this.userRepository = userRepository;
		this.userService = userService;
	}

//	@GetMapping("/ac")
//	public ResponseEntity<User> getAUser() {
//		return ResponseEntity.ok(userService.getAThing());
//	}

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<User> getUserById(@PathVariable long id) {
		try {
			return ResponseEntity.ok(userService.getUserWithId(id));
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "User not found", e);
		}
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		try {
			return ResponseEntity.ok(userService.getUserWithUsername(username));
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "User not found", e);
		}
	}

	@GetMapping("/username/{username}/password/{password}")
	public ResponseEntity<User> getUserByUsernameAndPassword(@PathVariable String username, @PathVariable String password) {
		try {
			return ResponseEntity.ok(userService.getUserWithUsernameAndPassword(username, password));
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "User not found", e);
		}
	}

//	@PostMapping("/new")
//	public ResponseEntity<User> createUser(@RequestBody UserDTO newUser) {
//		User createdNewUser;
//		try {
//			createdNewUser = userService.saveNewUser(newUser);
//		} catch (UsernameAlreadyExistsException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		} catch (UnsupportedColorException e) {
//			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
//		}
//		return ResponseEntity.ok(createdNewUser);
//	}

	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody User loggingInUser) throws UserNotFoundException {
		try {
			return ResponseEntity.ok(userService.logInNewUser(loggingInUser));
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username or Password is incorrect");
		}
	}

	@PostMapping("login/recovery")
	public UserDTO recoverUser(@RequestBody UserDTO recoveringUser) {
		try {
			return userService.recoverUser(recoveringUser);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username or Secret Password is incorrect", e);
		}
	}

	@PutMapping("/update")
	public User updateUser(@RequestBody User updatedUser) throws UserNotFoundException, UserNotUpdatedException {
		return userService.updateUser(updatedUser);
	}

	@GetMapping("/availableChallengeUsers/userId/{id}")
	public ResponseEntity<List<User>> getAllAvailableChallengeUsers(@PathVariable long id) {
		return ResponseEntity.ok(userService.getAvailableChallengeUsers(id));
	}
}