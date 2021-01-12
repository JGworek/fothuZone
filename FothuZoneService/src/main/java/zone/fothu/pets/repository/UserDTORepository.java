package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zone.fothu.pets.model.profile.UserDTO;

public interface UserDTORepository extends JpaRepository<UserDTO, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM pets.users WHERE id = ?1")
    UserDTO findById(int id);
	
    @Query(nativeQuery = true, value = "SELECT * FROM pets.users")
	List<UserDTO> findAllDTOs();
	
    @Query(nativeQuery = true, value = "SELECT * FROM pets.users WHERE id NOT IN (?1, 2147483647)")
	List<UserDTO> getAvailableChallengeUserDTOs(int id);
	
}
