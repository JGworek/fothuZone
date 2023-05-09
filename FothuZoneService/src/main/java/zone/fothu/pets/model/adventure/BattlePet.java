package zone.fothu.pets.model.adventure;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import zone.fothu.pets.model.profile.Pet;

@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public abstract class BattlePet {

	private long id;
	private Battle battle;
	private Pet pet;
	private int currentHealth;
	private boolean aliveStatus;
	
}
