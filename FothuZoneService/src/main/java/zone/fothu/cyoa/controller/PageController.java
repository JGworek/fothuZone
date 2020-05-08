package zone.fothu.cyoa.controller;

import java.util.ArrayList;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import zone.fothu.cyoa.exception.PageNotFoundException;
import zone.fothu.cyoa.model.Page;
import zone.fothu.cyoa.repository.PageRepository;

@RestController
@CrossOrigin
@RequestMapping(path = "/cyoapages")
public class PageController {

	@Autowired
	PageRepository pageRepository;
	
	@GetMapping("/all")
	public ResponseEntity<ArrayList<Page>> getAllPages() {
		try {
			return ResponseEntity.ok(pageRepository.findAll());
		} catch (PageNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pages not found", e);
		}
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Page> getPageById(@PathVariable int id) {
		try {
			return ResponseEntity.ok(pageRepository.findById(id));
		} catch (PageNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found", e);
		}
	}
	
	@GetMapping("/page/{pageNumber}")
	public ResponseEntity<Page> getPageByPageNumber(@PathVariable int pageNumber) {
		try {
			return ResponseEntity.ok(pageRepository.findByPageNumber(pageNumber));
		} catch (PageNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found", e);
		}
	}
	
}
