package zone.fothu.pets.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.exception.UserNotFoundException;
import zone.fothu.pets.exception.UserNotUpdatedException;
import zone.fothu.pets.model.profile.User;
import zone.fothu.pets.model.profile.UserDTO;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM pets.users WHERE id = ?1")
    User findById(int id) throws UserNotFoundException;

    @Override
    @Query(nativeQuery = true, value = "SELECT * FROM pets.users")
    List<User> findAll();

    @Query(nativeQuery = true, value = "SELECT * FROM pets.users WHERE LOWER(username) = ?1")
    User findByUsername(String username) throws UserNotFoundException;

    @Query(nativeQuery = true, value = "SELECT * FROM pets.users WHERE password = ?1")
    User findByPassword(String password) throws UserNotFoundException;

    @Query(nativeQuery = true, value = "SELECT * FROM pets.users WHERE LOWER(username) = ?1 AND user_password = ?2")
    User findByUsernameAndPassword(String username, String password) throws UserNotFoundException;

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE pets.users SET username = ?2, user_password = ?3, favorite_color = ?4 WHERE id = ?1")
    void updateUser(int id, String username, String userPassword, String favoriteColor)
        throws UserNotUpdatedException, PSQLException;

    @Query(nativeQuery = true, value = "SELECT * FROM pets.users where LOWER(username) = ?1 AND secret_password = ?2")
    UserDTO getRecoveredUser(String username, String encodedSecretPassword);

    @Query(nativeQuery = true, value = "SELECT * FROM pets.users WHERE id NOT IN (?1, 2147483647)")
	List<User> getAvailableChallengeUsers(int id);



}
