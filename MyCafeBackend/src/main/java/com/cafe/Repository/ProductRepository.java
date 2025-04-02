package com.cafe.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafe.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	List<Product> findByCategoryId(Integer id);
	Optional<Product> findById(Integer id);
}
