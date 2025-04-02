package com.cafe.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe.Repository.CategoryRepository;
import com.cafe.model.Category;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public String addCategory(Category category) {
		if(categoryRepository.existsByName(category.getName())) {
			return "Category already exists";
		}
		categoryRepository.save(category);
			return "category added successfully!";
	}
	
	 public ResponseEntity<List<Category>> getAllCategory(Boolean filterValue) {
	        try {
	            if (Boolean.TRUE.equals(filterValue)) {
	                log.info("Fetching categories with active products");
	                return new ResponseEntity<>(categoryRepository.findCategoriesWithActiveProducts(), HttpStatus.OK);
	            }
	            log.info("Fetching all categories (Admin View)");
	            return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
	        } catch (Exception ex) {
	            log.error("Error fetching categories", ex);
	            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	
	public Optional<Category> getCategoryById(Integer id){
		return categoryRepository.findById(id);
	}
	
	public void updateCategory(Category category) {
		 categoryRepository.save(category);
	}
	
	public static String getUuid() {
		Date date = new Date();
		long time = date.getTime();
		return "BILL-" +time;
	}

}
