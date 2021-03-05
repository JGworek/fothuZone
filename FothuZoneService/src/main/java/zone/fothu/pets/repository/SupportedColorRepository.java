package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.profile.SupportedColor;

public interface SupportedColorRepository extends JpaRepository<SupportedColor, String> {

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.supported_user_colors")
	List<SupportedColor> getAll();

}
