package com.cafe.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafe.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	List<Category> findAll();
    boolean existsByName(String name);
    
    @Query("SELECT c FROM Category c WHERE c.id IN (SELECT DISTINCT p.category.id FROM Product p WHERE p.status = 'true')")
    List<Category> findCategoriesWithActiveProducts();

}

