package zone.fothu.pets.controller;

import java.io.Serializable;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
            return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/all/DTO")
    public ResponseEntity<List<UserDTO>> getAllUserDTOs() {
            return ResponseEntity.ok(userService.getAllUserDTOs());

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
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
    public ResponseEntity<User> getUserByUsernameAndPassword(@PathVariable String username,
        @PathVariable String password) {
        try {
            return ResponseEntity.ok(userService.getUserWithUsernameAndPassword(username, password));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "User not found", e);
        }
    }

    @PostMapping("/new")
    public User createUser(@RequestBody User newUser) {
        try {
            return userService.saveNewUser(newUser);
            // return userRepository.save(newUser);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "User not created", e);
        }
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User loggingInUser) {
        try {
            return userService.logInNewUser(loggingInUser);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Username or Password is incorrect", e);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username or Password is incorrect", e);
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
    public User updateUser(@RequestBody User updatedUser)
        throws UserNotFoundException, PSQLException, UserNotUpdatedException {
        return userService.updateUser(updatedUser);
    }
    
    @GetMapping("/availableChallengeUsers/userId/{id}")
    public ResponseEntity<List<User>> getAllAvailableChallengeUsers(@PathVariable int id) {
    	return ResponseEntity.ok(userService.getAvailableChallengeUsers(id));
    }
    
    @GetMapping("/availableChallengeUserDTOs/userId/{id}")
    public ResponseEntity<List<UserDTO>> getAllAvailableChallengeUserDTOs(@PathVariable int id) {
    	return ResponseEntity.ok(userService.getAvailableChallengeUserDTOs(id));
    }
}
