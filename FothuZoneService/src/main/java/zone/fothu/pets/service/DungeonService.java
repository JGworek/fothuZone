package zone.fothu.pets.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zone.fothu.pets.model.adventure.Dungeon;
import zone.fothu.pets.model.adventure.Floor;
import zone.fothu.pets.repository.DungeonRepository;

@Service
public class DungeonService implements Serializable {

	private static final long serialVersionUID = -3450268372157430349L;

	DungeonRepository dungeonRepository;

	@Autowired
	public DungeonService(DungeonRepository dungeonRepository) {
		super();
		this.dungeonRepository = dungeonRepository;
	}

	public Dungeon createNewDungeon(Dungeon newDungeon) {
		return cleanOutPasswords(dungeonRepository.save(newDungeon));
	}

	Dungeon cleanOutPasswords(Dungeon dungeon) {
		if (dungeon.getBossPet() != null) {
			dungeon.getBossPet().getOwner().setUserPassword(null).setEmailAddress(null);
		}
		for (Floor floor : dungeon.getFloors()) {
			if (floor.getMiniBossPet() != null) {
				floor.getMiniBossPet().getOwner().setUserPassword(null).setEmailAddress(null);
			}
		}
		return dungeon;
	}

}
