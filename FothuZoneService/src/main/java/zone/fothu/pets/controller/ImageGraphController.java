package zone.fothu.pets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.graphql.data.ArgumentValue;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import zone.fothu.pets.model.adventure.ChallengeRequest;
import zone.fothu.pets.model.profile.Image;
import zone.fothu.pets.repository.ImageRepository;
import zone.fothu.pets.repository.PetRepository;
import zone.fothu.pets.service.ImageService;
import zone.fothu.utility.BeanUtil;

@Controller
public class ImageGraphController {

	private final ImageRepository imageRepository;
	private final PetRepository petRepository;
	private final ImageService imageService;
	private Image imageBean;

	@Autowired
	public ImageGraphController(ImageRepository imageRepository, PetRepository petRepository, @Lazy ImageService imageService, @Lazy Image imageBean) {
		super();
		this.imageRepository = imageRepository;
		this.petRepository = petRepository;
		this.imageService = imageService;
		this.imageBean = imageBean;
	}
	
	@QueryMapping
	public Image imageById(@Argument long id) {
		return imageRepository.findImageById(id);
	}
	
	@QueryMapping
	public List<Image> allImages(ArgumentValue<Integer> first) {
		if(first.isOmitted()) {
			return imageRepository.findAll();
		} else {
			return imageRepository.findFirst(first.value());
		}
	}
	
	@MutationMapping
	public Image createImage(@Argument String imageURL) {
		return imageRepository.save(BeanUtil.getBean(Image.class).setId(2000).setImageURL(imageURL));
	}
	
	@MutationMapping
	public Image deleteImage(@Argument long id) {
		imageRepository.delete(imageRepository.findImageById(id));
		return imageBean;
	}
	
	@MutationMapping
	public Image editImageURL(@Argument long id, @Argument String imageURL) {
		return imageRepository.save(imageRepository.findImageById(id).setImageURL(imageURL));
	}
}
