package zone.fothu.pets.service;

import java.io.Serializable;

import org.springframework.stereotype.Service;

@Service
public class PetService implements Serializable {

	private static final long serialVersionUID = -1793499282842108356L;

	// when creating a new pet, HMW is based on type the first time
	// Str = Str/Agi/Int
	// Agi = Agi/Int/Str
	// Int = Int/Str/Agi

}
