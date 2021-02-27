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
	@Query(nativeQuery = true, value = "INSERT INTO pets.images VALUES (DEFAULT, ?imageURL)")
	void saveNewImage(String imageURL);

	@Query(nativeQuery = true, value = "SELECT MAX(id) FROM pets.images")
	int findLatestImageId();

	@Query(nativeQuery = true, value = "SELECT * FROM pets.images WHERE id = ?imageId")
	Image findImageById(int imageId);

	@Query(nativeQuery = true, value = "SELECT * FROM pets.images WHERE image_url = ?imageURL")
	Image findImageByURL(String imageURL);

	@Query(nativeQuery = true, value = "SELECT * FROM pets.images")
	List<Image> getAllImages();

	@Query(nativeQuery = true, value = "SELECT * FROM pets.images ORDER BY RANDOM() LIMIT ?1")
	List<Image> getRandomImagesOfCertainNumber(int numberOfImages);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE pets.pets SET image_id = ?petId WHERE id = ?imageId")
	void setPetImage(int petId, int imageId);

}
