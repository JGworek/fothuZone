package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zone.fothu.pets.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM pets.users WHERE id = ?1")
	User findById(int id);
	
	@Query(nativeQuery = true, value = "SELECT * FROM pets.users")
	List<User> findAll();
	
	@Query(nativeQuery = true, value = "SELECT * FROM pets.users WHERE user_name = ?1")
	User findByUsername(String username);
	
	@Query(nativeQuery = true, value = "SELECT * FROM pets.users WHERE user_name = ?1 AND user_password = ?2")
	User findByUsernameAndPassword(String username, String password);
	
}
