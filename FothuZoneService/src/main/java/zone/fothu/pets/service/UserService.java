package zone.fothu.pets.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.model.User;
import zone.fothu.pets.repository.UserRepository;

@Service
public class UserService implements Serializable {

    private static final long serialVersionUID = 1313809918036560606L;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public User saveNewUser(User newUser) {
        newUser.setUserPassword(passwordEncoder.encode(newUser.getUserPassword()));
        userRepository.save(newUser);
        newUser.setUserPassword(null);
        return newUser;
    }

    public User logInNewUser(User loggingInUser) throws UserNotFoundException {
        if (passwordEncoder.matches(loggingInUser.getUserPassword(),
            userRepository.findByUsername(loggingInUser.getUsername()).getUserPassword())) {
            User userFromDatabase = userRepository.findByUsername(loggingInUser.getUsername());
            userFromDatabase.setUserPassword(null);
            return userFromDatabase;
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            user.setUserPassword(null);
        }
        return userList;
    }

    public User getUserWithId(int id) throws UserNotFoundException {
        User userFromId = userRepository.findById(id);
        userFromId.setUserPassword(null);
        return userFromId;
    }

    public User getUserWithUsername(String username) throws UserNotFoundException {
        User userFromUsername = userRepository.findByUsername(username);
        userFromUsername.setUserPassword(null);
        return userFromUsername;
    }

    public User getUserWithUsernameAndPassword(String username, String password) throws UserNotFoundException {
        String secretPassword = passwordEncoder.encode(password);
        User userFromUsernameAndPassword = userRepository.findByUsernameAndPassword(username.toLowerCase(), secretPassword);
        userFromUsernameAndPassword.setUserPassword(null);
        return userFromUsernameAndPassword;
    }
}
