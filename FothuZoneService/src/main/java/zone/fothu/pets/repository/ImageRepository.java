package zone.fothu.pets.repository;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import zone.fothu.pets.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO pets.images VALUES (DEFAULT, ?1, ?2, ?3)")
    void saveNewImage(String imageURL, String imageOwnerUsername, String imageOwnerName);
    
    @Query(nativeQuery = true, value = "SELECT MAX(id) FROM pets.images")
    int findLatestImageId();
    
    @Query(nativeQuery = true, value = "SELECT * FROM pets.images WHERE id = ?1")
    Image findImageById(int imageId);
    
    @Query(nativeQuery = true, value = "SELECT * FROM pets.images WHERE image_url = ?1")
    Image findImageByURL(String imageURL);
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE pets.pets SET image_id = ?2 WHERE id = ?1")
    void setPetImage(int petId, int imageId);

}
