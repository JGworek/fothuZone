package zone.fothu.pets.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class LevelUpService implements Serializable {

	private static final long serialVersionUID = 4593021991429401842L;
	
    int getLargestLevelingStat(Set<Integer> levelValues) {
    	int largestLevelingStat = Collections.max(levelValues);
    	levelValues.remove(largestLevelingStat);
    	return largestLevelingStat;
    	
    }
    
    int getSmallestLevelingStat(Set<Integer> levelValues) {
    	int smallestLevelingStat = Collections.min(levelValues);
    	levelValues.remove(smallestLevelingStat);
    	return smallestLevelingStat;
    }
    
    int getRemainingLevelingStat(Set<Integer> levelValues) {
    	int remainingLevelingStat = -1;
    	for(int individualLevelValue: levelValues) {
    		return remainingLevelingStat = individualLevelValue;
    	}
		return remainingLevelingStat;
    }

}
