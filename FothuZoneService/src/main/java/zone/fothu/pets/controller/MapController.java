package zone.fothu.pets.controller;

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

import zone.fothu.pets.model.Map;
import zone.fothu.pets.repository.MapRepository;
import zone.fothu.pets.repository.PetRepository;

@RestController
@CrossOrigin
@RequestMapping(path = "/map")
public class MapController {
    
    @Autowired
    MapRepository mapRepository;
    
    @Autowired
    PetRepository PetRepository;
    
    @GetMapping("/all")
    public ResponseEntity<List<Map>> getAllMaps() {
        List<Map> maps = mapRepository.findAll();
        return ResponseEntity.ok(maps);
    }
    
    @GetMapping("/id/{id}")
    public ResponseEntity<Map> getMapById(@PathVariable int id) {
        Map map = mapRepository.findMapById(id);
        return ResponseEntity.ok(map);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Map>> getAllMapsByName(@PathVariable String name) {
        String mapName = name.replace("%20", " ");
        List<Map> maps = mapRepository.findMapsByName(mapName);
        return ResponseEntity.ok(maps);
    }
    
    @PostMapping("/new")
    public ResponseEntity<List<Map>> createNewMaps(@RequestBody List<Map> newMaps) {
        for(Map map : newMaps) {
            mapRepository.save(map);
        }
        List<Map> maps = mapRepository.findMapsByName(newMaps.get(0).getName());
        return ResponseEntity.ok(maps);
    }
}
