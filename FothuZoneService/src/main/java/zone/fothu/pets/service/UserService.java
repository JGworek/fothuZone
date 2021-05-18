package zone.fothu.pets.service;

import java.io.Serializable;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import zone.fothu.pets.exception.UnsupportedColorException;
import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.exception.UserNotUpdatedException;
import zone.fothu.pets.exception.UsernameAlreadyExistsException;
import zone.fothu.pets.model.profile.SupportedColor;
import zone.fothu.pets.model.profile.User;
import zone.fothu.pets.model.profile.UserDTO;
import zone.fothu.pets.repository.SupportedColorRepository;
import zone.fothu.pets.repository.UserRepository;

@Service
public class UserService implements Serializable {

	private static final long serialVersionUID = 1313809918036560606L;

	private final UserRepository userRepository;
	private final SupportedColorRepository supportedColorRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private User userBean;

	@Autowired
	public UserService(UserRepository userRepository, SupportedColorRepository supportedColorRepository, BCryptPasswordEncoder passwordEncoder, User userBean) {
		super();
		this.userRepository = userRepository;
		this.supportedColorRepository = supportedColorRepository;
		this.passwordEncoder = passwordEncoder;
		this.userBean = userBean;
	}

	public User saveNewUser(User newUser) {
		boolean userExists = true;
		User createdNewUser;
		try {
			SupportedColor checkColor = supportedColorRepository.findById(newUser.getFavoriteColor()).get();
		} catch (NullPointerException e) {
			throw new UnsupportedColorException("Submitted color not supported!");
		}
		try {
			User checkUser = userRepository.findByUsername(newUser.getUserPassword());
		} catch (NullPointerException e) {
			userExists = false;
			newUser.setUserPassword(passwordEncoder.encode(newUser.getUserPassword()));
			newUser.setSecretPassword(passwordEncoder.encode(newUser.getSecretPassword()));
			userRepository.save(newUser);
		}

		if (userExists = false) {
			createdNewUser = userRepository.findById(userRepository.findLatestUserId()).get();
			createdNewUser.setUserPassword(null);
			createdNewUser.setSecretPassword(null);
		} else {
			throw new UsernameAlreadyExistsException("Username already taken!");
		}
		return createdNewUser;
	}

	public User logInNewUser(User loggingInUser) throws UserNotFoundException {
		if (passwordEncoder.matches(loggingInUser.getUserPassword(), userRepository.findByUsername(loggingInUser.getUsername().toLowerCase()).getUserPassword())) {
			User userFromDatabase = userRepository.findByUsername(loggingInUser.getUsername().toLowerCase());
			userFromDatabase.setUserPassword(null);
			return userFromDatabase;
		} else {
			throw new UserNotFoundException();
		}
	}

	public User updateUser(User updatingUser) throws UserNotFoundException, PSQLException, UserNotUpdatedException {
		String encodedPassword = passwordEncoder.encode(updatingUser.getUserPassword());
//        String encodedSecretPassword = passwordEncoder.encode(updatingUser.getSecretPassword());
		userRepository.updateUser(updatingUser.getId(), updatingUser.getUsername(), encodedPassword, updatingUser.getFavoriteColor());
		User returnedUser = userRepository.findById(updatingUser.getId()).get();
		returnedUser.setUserPassword(null);
		returnedUser.setSecretPassword(null);
		return returnedUser;
	}

	public List<User> getAllUsers() {
		List<User> userList = userRepository.findAll();
		for (User user : userList) {
			user.setUserPassword(null);
			user.setSecretPassword(null);
		}
		return userList;
	}

	public List<User> getAllUserDTOs() {
		List<User> userList = userRepository.findAll();
		for (User user : userList) {
			user.setUserPassword(null);
			user.setSecretPassword(null);
		}
		return userList;
	}

	public User getUserWithId(long id) throws UserNotFoundException {
		User userFromId = userRepository.findById(id).get();
		userFromId.setUserPassword(null);
		userFromId.setSecretPassword(null);
		return userFromId;
	}

	public User getUserWithUsername(String username) throws UserNotFoundException {
		User userFromUsername = userRepository.findByUsername(username.toLowerCase());
		userFromUsername.setUserPassword(null);
		userFromUsername.setSecretPassword(null);
		return userFromUsername;
	}

	public User getUserWithUsernameAndPassword(String username, String password) throws UserNotFoundException {
		String encodedPassword = passwordEncoder.encode(password);
		User userFromUsernameAndPassword = userRepository.findByUsernameAndPassword(username.toLowerCase(), encodedPassword);
		userFromUsernameAndPassword.setUserPassword(null);
		userFromUsernameAndPassword.setSecretPassword(null);
		return userFromUsernameAndPassword;
	}

	public UserDTO recoverUser(UserDTO recoveringUser) throws UserNotFoundException {
		UserDTO recoveredUser = userRepository.getRecoveredUser(recoveringUser.getUsername().toLowerCase(), passwordEncoder.encode(recoveringUser.getSecretPassword()));
		return recoveredUser;
	}

	public List<User> getAvailableChallengeUsers(long id) {
		List<User> availableChallengeUsers = userRepository.getAvailableChallengeUsers(id);
		for (User user : availableChallengeUsers) {
			user.setUserPassword(null);
			user.setSecretPassword(null);
		}
		return availableChallengeUsers;
	}

	public User getAThing() {
		return userBean;
	}

}
