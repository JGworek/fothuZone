package zone.fothu.pets.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zone.fothu.pets.exception.UnsupportedColorException;
import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.exception.UserNotUpdatedException;
import zone.fothu.pets.exception.UsernameAlreadyExistsException;
import zone.fothu.pets.model.profile.User;
import zone.fothu.pets.model.profile.UserDTO;
import zone.fothu.pets.repository.SupportedColorRepository;
import zone.fothu.pets.repository.UserRepository;

@Service
public class UserService implements Serializable {

	private static final long serialVersionUID = 1313809918036560606L;

	private final UserRepository userRepository;
	private final SupportedColorRepository supportedColorRepository;
	private User userBean;

	@Autowired
	public UserService(UserRepository userRepository, SupportedColorRepository supportedColorRepository, User userBean) {
		super();
		this.userRepository = userRepository;
		this.supportedColorRepository = supportedColorRepository;
		this.userBean = userBean;
	}

	public User saveNewUser(User newUser) {
		boolean userExists = true;
		User createdNewUser;
		try {
			supportedColorRepository.findById(newUser.getFavoriteColor()).get();
		} catch (NullPointerException e) {
			throw new UnsupportedColorException("Submitted color not supported!");
		}
		try {
			userRepository.findByUsername(newUser.getUserPassword());
		} catch (NullPointerException e) {
			userExists = false;
			newUser.setUserPassword(newUser.getUserPassword());
			userRepository.save(newUser);
		}

		if (userExists = false) {
			createdNewUser = userRepository.findById(userRepository.findLatestUserId()).get();
			createdNewUser.setUserPassword(null).setEmailAddress(null);
		} else {
			throw new UsernameAlreadyExistsException("Username already taken!");
		}
		return createdNewUser;
	}

	public User logInNewUser(User loggingInUser) throws UserNotFoundException {
		try {
			if (loggingInUser.getUserPassword().equals(userRepository.findByUsername(loggingInUser.getUsername().toLowerCase()).getUserPassword())) {
				return userRepository.findByUsername(loggingInUser.getUsername().toLowerCase()).setUserPassword(null).setEmailAddress(null);
			} else {
				throw new UserNotFoundException("Username or Password is incorrect");
			}
		} catch (NullPointerException e) {
			throw new UserNotFoundException("Username or Password is incorrect");
		}
	}

	public User updateUser(User updatingUser) throws UserNotFoundException, UserNotUpdatedException {
		String encodedPassword = updatingUser.getUserPassword();
//        String encodedSecretPassword = passwordEncoder.encode(updatingUser.getSecretPassword());
		userRepository.updateUser(updatingUser.getId(), updatingUser.getUsername(), encodedPassword, updatingUser.getFavoriteColor());
		User returnedUser = userRepository.findById(updatingUser.getId()).get();
		returnedUser.setUserPassword(null).setEmailAddress(null);
		return returnedUser;
	}

	public List<User> getAllUsers() {
		List<User> userList = userRepository.findAll();
		for (User user : userList) {
			user.setUserPassword(null).setEmailAddress(null);
		}
		return userList;
	}

	public List<User> getAllUserDTOs() {
		List<User> userList = userRepository.findAll();
		for (User user : userList) {
			user.setUserPassword(null).setEmailAddress(null);
		}
		return userList;
	}

	public User getUserWithId(long id) throws UserNotFoundException {
		User userFromId = userRepository.findById(id).get();
		userFromId.setUserPassword(null).setEmailAddress(null);
		return userFromId;
	}

	public User getUserWithUsername(String username) throws UserNotFoundException {
		User userFromUsername = userRepository.findByUsername(username.toLowerCase());
		userFromUsername.setUserPassword(null).setEmailAddress(null);
		return userFromUsername;
	}

	public User getUserWithUsernameAndPassword(String username, String password) throws UserNotFoundException {
		String encodedPassword = password;
		User userFromUsernameAndPassword = userRepository.findByUsernameAndPassword(username.toLowerCase(), encodedPassword);
		userFromUsernameAndPassword.setUserPassword(null).setEmailAddress(null);
		return userFromUsernameAndPassword;
	}

	public UserDTO recoverUser(UserDTO recoveringUser) throws UserNotFoundException {
		UserDTO recoveredUser = userRepository.getRecoveredUser(recoveringUser.getUsername().toLowerCase(), recoveringUser.getSecretPassword());
		return recoveredUser;
	}

	public List<User> getAvailableChallengeUsers(long id) {
		List<User> availableChallengeUsers = userRepository.getAvailableChallengeUsers(id);
		for (User user : availableChallengeUsers) {
			user.setUserPassword(null).setEmailAddress(null);
		}
		return availableChallengeUsers;
	}

	public User getAThing() {
		return userBean;
	}

}
