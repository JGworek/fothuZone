package zone.fothu.pets.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zone.fothu.pets.model.adventure.Dungeon;
import zone.fothu.pets.repository.DungeonRepository;
import zone.fothu.pets.service.DungeonService;

@RestController
@CrossOrigin
@RequestMapping(path = "/dungeons")
public class DungeonController implements Serializable {

	private static final long serialVersionUID = 6402680653396347736L;

	private final DungeonRepository dungeonRepository;
	private final DungeonService dungeonService;

	@Autowired
	public DungeonController(DungeonRepository dungeonRepository, @Lazy DungeonService dungeonService) {
		super();
		this.dungeonRepository = dungeonRepository;
		this.dungeonService = dungeonService;
	}

	@Cacheable("allDungeons")
	@GetMapping("/all")
	public ResponseEntity<List<Dungeon>> getAllMaps() {
		List<Dungeon> maps = dungeonRepository.findAll();
		return ResponseEntity.ok(maps);
	}

	@Cacheable(value = "dungeon", key = "#id")
	@GetMapping("/id/{id}")
	public ResponseEntity<Dungeon> getMapById(@PathVariable long id) {
		Dungeon map = dungeonRepository.findMapById(id);
		return ResponseEntity.ok(map);
	}

	@Cacheable(value = "dungeon", key = "#name")
	@GetMapping("/name/{name}")
	public ResponseEntity<Dungeon> getMapByName(@PathVariable String name) {
		String mapName = name.replace("%20", " ");
		Dungeon map = dungeonRepository.findMapByName(mapName);
		return ResponseEntity.ok(map);
	}

	@PostMapping("/new")
	public ResponseEntity<Dungeon> createNewMaps(@RequestBody Dungeon newDungeon) {
		return ResponseEntity.ok(dungeonService.createNewDungeon(newDungeon));
	}
}