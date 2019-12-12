package zone.fothu.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import zone.fothu.model.Page;

public interface PageRepository extends JpaRepository<Page, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM cyoa.page WHERE id = ?1")
	Page findById(int id);
	
	@Query(nativeQuery = true, value = "SELECT * FROM cyoa.page")
	ArrayList<Page> findAll();
	
	@Query(nativeQuery = true, value = "SELECT * FROM cyoa.page WHERE page_number = ?1")
	Page findByPageNumber(int pageNumber);
}