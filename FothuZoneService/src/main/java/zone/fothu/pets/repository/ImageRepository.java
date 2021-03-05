package zone.fothu.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.profile.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO pets.images VALUES (DEFAULT, ?1)")
	void saveNewImage(String imageURL);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT MAX(id) FROM pets.images")
	int findLatestImageId();

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.images WHERE id = ?1")
	Image findImageById(int imageId);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.images WHERE image_url = ?1")
	Image findImageByURL(String imageURL);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.images")
	List<Image> getAllImages();

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM pets.images ORDER BY RANDOM() LIMIT ?1")
	List<Image> getRandomImagesOfCertainNumber(int numberOfImages);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET image_id = ?1 WHERE id = ?2")
	void setPetImage(int petId, int imageId);

}
