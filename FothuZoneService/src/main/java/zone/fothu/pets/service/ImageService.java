package zone.fothu.pets.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zone.fothu.pets.model.profile.Image;
import zone.fothu.pets.repository.ImageRepository;

@Service
public class ImageService implements Serializable {

	private static final long serialVersionUID = 8877383693450666109L;

	@Autowired
	ImageRepository imageRepository;

	public Image saveNewImage(Image newImage) {
		if (imageRepository.findImageByURL(newImage.getImageURL()) == null) {
			imageRepository.saveNewImage(newImage.getImageURL());
			return imageRepository.findImageById(imageRepository.findLatestImageId());
		} else {
			return imageRepository.findImageByURL(newImage.getImageURL());
		}
	}

	public List<Image> getAllImages() {
		return imageRepository.getAllImages();
	}

	public List<Image> getRandomImagesOfCertainNumber(int numberOfImages) {
		return imageRepository.getRandomImagesOfCertainNumber(numberOfImages);
	}
}
