package zone.fothu.pets.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zone.fothu.pets.model.adventure.DungeonMap;
import zone.fothu.pets.repository.DungeonMapRepository;
import zone.fothu.pets.repository.PetRepository;

@RestController
@CrossOrigin
@RequestMapping(path = "/map")
public class DungeonMapController implements Serializable {

    private static final long serialVersionUID = 6402680653396347736L;

    @Autowired
    DungeonMapRepository mapRepository;

    @Autowired
    PetRepository PetRepository;

    @GetMapping("/all")
    public ResponseEntity<List<DungeonMap>> getAllMaps() {
        List<DungeonMap> maps = mapRepository.findAll();
        return ResponseEntity.ok(maps);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DungeonMap> getMapById(@PathVariable int id) {
        DungeonMap map = mapRepository.findMapById(id);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<DungeonMap>> getAllMapsByName(@PathVariable String name) {
        String mapName = name.replace("%20", " ");
        List<DungeonMap> maps = mapRepository.findMapsByName(mapName);
        return ResponseEntity.ok(maps);
    }

    @PostMapping("/new")
    public ResponseEntity<List<DungeonMap>> createNewMaps(@RequestBody List<DungeonMap> newMaps) {
        for (DungeonMap map : newMaps) {
            mapRepository.save(map);
        }
        List<DungeonMap> maps = mapRepository.findMapsByName(newMaps.get(0).getName());
        return ResponseEntity.ok(maps);
    }
}
