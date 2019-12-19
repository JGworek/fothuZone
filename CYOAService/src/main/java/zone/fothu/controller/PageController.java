package zone.fothu.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import zone.fothu.exception.PageNotFoundException;
import zone.fothu.model.Page;
import zone.fothu.repository.PageRepository;

@RestController
@RequestMapping(path = "/cyoapages")
public class PageController {

	@Autowired
	PageRepository pageRepository;
	
	@Autowired
	private Logger logger = Logger.getLogger(PageController.class);
	
	@GetMapping("/all")
	public ResponseEntity<ArrayList<Page>> getAllPages() {
		try {
			logger.info("All pages retrieved");
			return ResponseEntity.ok(pageRepository.findAll());
		} catch (PageNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pages not found", e);
		}
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Page> getPageById(@PathVariable int id) {
		try {
			logger.info("Page #"+id+" retrieved");
			return ResponseEntity.ok(pageRepository.findById(id));
		} catch (PageNotFoundException e) {
			logger.info("Page #"+id+" not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found", e);
		}
	}
	
	@GetMapping("/page/{pageNumber}")
	public ResponseEntity<Page> getPageByPageNumber(@PathVariable int pageNumber) {
		try {
			logger.info("Page #"+pageNumber+" retrieved");
			return ResponseEntity.ok(pageRepository.findByPageNumber(pageNumber));
		} catch (PageNotFoundException e) {
			logger.info("Page #"+pageNumber+" not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found", e);
		}
	}
	
}
