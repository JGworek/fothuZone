package zone.fothu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zone.fothu.pets.model.profile.PetDTO;

public interface PetDTORepository extends JpaRepository<PetDTO, Integer> {

}
