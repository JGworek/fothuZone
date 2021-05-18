package zone.fothu.pets.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zone.fothu.pets.exception.PetNotUpdatedException;
import zone.fothu.pets.model.profile.Pet;
import zone.fothu.pets.model.profile.User;
import zone.fothu.pets.repository.PetRepository;
import zone.fothu.pets.repository.UserRepository;

@Service
public class PetService implements Serializable {

	private static final long serialVersionUID = -1793499282842108356L;

	private final PetRepository petRepository;
	private final UserRepository userRepository;
	private Pet petBean;
	private User userBean;

	@Autowired
	public PetService(PetRepository petRepository, UserRepository userRepository, Pet petBean, User userBean) {
		super();
		this.petRepository = petRepository;
		this.userRepository = userRepository;
		this.petBean = petBean;
		this.userBean = userBean;
	}

	private final int MAX_STAT_VALUE = 10;
	private final int MIN_HEALTH_VALUE = 10;
	private final int MAX_HEALTH_VALUE = 15;

	// when creating a new pet, HML is based on type the first time
	// Str = Str/Agi/Int
	// Agi = Agi/Int/Str
	// Int = Int/Str/Agi
	public Pet createPet(Pet newPet) {
		Set<Integer> randomStatValues = new HashSet<Integer>();
		while (randomStatValues.size() < 3) {
			randomStatValues.add((int) (Math.random() * MAX_STAT_VALUE));
		}
		if (newPet.getType().equalsIgnoreCase("strength")) {
			newPet.setStrength(getLargestLevelingStat(randomStatValues));
			newPet.setIntelligence(getSmallestLevelingStat(randomStatValues));
			newPet.setAgility(getRemainingLevelingStat(randomStatValues));
		} else if (newPet.getType().equalsIgnoreCase("agility")) {
			newPet.setAgility(getLargestLevelingStat(randomStatValues));
			newPet.setStrength(getSmallestLevelingStat(randomStatValues));
			newPet.setIntelligence(getRemainingLevelingStat(randomStatValues));
		} else if (newPet.getType().equalsIgnoreCase("intelligence")) {
			newPet.setIntelligence(getLargestLevelingStat(randomStatValues));
			newPet.setAgility(getSmallestLevelingStat(randomStatValues));
			newPet.setStrength(getRemainingLevelingStat(randomStatValues));
		}
		int healthStat = (int) (Math.random() * (MAX_HEALTH_VALUE - MIN_HEALTH_VALUE)) + MIN_HEALTH_VALUE;
		newPet.setMaxHealth(healthStat);
		newPet.setCurrentHealth(healthStat);
		return petRepository.save(newPet);
	}

	public Pet levelUpPet(long petId, String highStat, String mediumStat, String lowStat) throws PetNotUpdatedException {
		Pet levelingUpPet = petRepository.findById(petId).get();
		if (highStat.equalsIgnoreCase(mediumStat) | highStat.equalsIgnoreCase(lowStat) | mediumStat.equalsIgnoreCase(lowStat)) {
			throw new PetNotUpdatedException("You cannot update a single stat more than once!");
		}
		Set<Integer> randomStatValues = new HashSet<Integer>();
		while (randomStatValues.size() < 3) {
			randomStatValues.add((int) (Math.random() * MAX_STAT_VALUE));
		}
		if (highStat.equalsIgnoreCase("strength")) {
			levelingUpPet.setStrength(levelingUpPet.getStrength() + getLargestLevelingStat(randomStatValues));
		} else if (highStat.equalsIgnoreCase("agility")) {
			levelingUpPet.setAgility(levelingUpPet.getAgility() + getLargestLevelingStat(randomStatValues));
		} else if (highStat.equalsIgnoreCase("intelligence")) {
			levelingUpPet.setIntelligence(levelingUpPet.getIntelligence() + getLargestLevelingStat(randomStatValues));
		}
		if (lowStat.equalsIgnoreCase("strength")) {
			levelingUpPet.setStrength(levelingUpPet.getStrength() + getSmallestLevelingStat(randomStatValues));
		} else if (lowStat.equalsIgnoreCase("agility")) {
			levelingUpPet.setAgility(levelingUpPet.getAgility() + getSmallestLevelingStat(randomStatValues));
		} else if (lowStat.equalsIgnoreCase("intelligence")) {
			levelingUpPet.setIntelligence(levelingUpPet.getIntelligence() + getSmallestLevelingStat(randomStatValues));
		}
		if (mediumStat.equalsIgnoreCase("strength")) {
			levelingUpPet.setStrength(levelingUpPet.getStrength() + getRemainingLevelingStat(randomStatValues));
		} else if (mediumStat.equalsIgnoreCase("agility")) {
			levelingUpPet.setAgility(levelingUpPet.getAgility() + getRemainingLevelingStat(randomStatValues));
		} else if (mediumStat.equalsIgnoreCase("intelligence")) {
			levelingUpPet.setIntelligence(levelingUpPet.getIntelligence() + getRemainingLevelingStat(randomStatValues));
		}
		int healthIncrease = (int) (Math.random() * (MAX_HEALTH_VALUE - MIN_HEALTH_VALUE)) + MIN_HEALTH_VALUE;
		levelingUpPet.setMaxHealth(levelingUpPet.getMaxHealth() + healthIncrease);
		levelingUpPet.setCurrentHealth(levelingUpPet.getCurrentHealth() + healthIncrease);
		return petRepository.save(levelingUpPet);
	}

	public int getLargestLevelingStat(Set<Integer> randomStatValues) {
		int largestLevelingStat = Collections.max(randomStatValues);
		randomStatValues.remove(largestLevelingStat);
		return largestLevelingStat;
	}

	public int getSmallestLevelingStat(Set<Integer> randomStatValues) {
		int smallestLevelingStat = Collections.min(randomStatValues);
		randomStatValues.remove(smallestLevelingStat);
		return smallestLevelingStat;
	}

	public int getRemainingLevelingStat(Set<Integer> randomStatValues) {
		int remainingLevelingStat = -1;
		for (int individualLevelValue : randomStatValues) {
			return remainingLevelingStat = individualLevelValue;
		}
		return remainingLevelingStat;
	}
}
