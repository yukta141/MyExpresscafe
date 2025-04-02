package com.cafe.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.jwt.JwtFilter;
import com.cafe.model.Category;
import com.cafe.service.CategoryService;

@RestController
@RequestMapping(path="/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@PostMapping(path="/add")
	public ResponseEntity<String> addCategory(@RequestBody Category category){
		if(!jwtFilter.isAdmin()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied. Only admins can add categories");						
		}
		String response= categoryService.addCategory(category);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Category>> getAllCategories(
	        @RequestParam(name = "filterValue", required = false) Boolean filterValue) {
	    return categoryService.getAllCategory(filterValue);
	}
	
	@PostMapping(path="/update")
	public ResponseEntity<String> updateCategory(@RequestBody Map<String, Object> requestBody){
		try {
			if(!jwtFilter.isAdmin()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Only admins can update category");
			}
			
			Integer id= requestBody.containsKey("id")? Integer.valueOf(requestBody.get("id").toString()):null;
			String name= requestBody.containsKey("name")? requestBody.get("name").toString():null;
			
			Optional<Category> optionalCategory= categoryService.getCategoryById(id);
			if(optionalCategory.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
			}
			
			Category category= optionalCategory.get();
			category.setName(name);
			categoryService.updateCategory(category);
			
			return ResponseEntity.status(HttpStatus.OK).body("category added successfully");
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured while updating the category");
		}
	}

}
