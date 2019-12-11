package zone.fothu.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import zone.fothu.model.Page;

public interface PageRepository extends JpaRepository<Page, Integer> {

	Page findById(int id);
	ArrayList<Page> findAll();
	
	@Query(nativeQuery = true, value = "SELECT * FROM Page where pageNumber = ?1")
	  Page findByPageNumber(int pageNumber);
}