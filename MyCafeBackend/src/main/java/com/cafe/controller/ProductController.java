package com.cafe.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.jwt.JwtFilter;
import com.cafe.model.Category;
import com.cafe.model.Product;
import com.cafe.service.CategoryService;
import com.cafe.service.ProductService;

@RestController
@RequestMapping(path="/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@PostMapping(path="/add")
	public ResponseEntity<String> addProduct(@RequestBody Map<String, Object> requestBody){
		try {
			if(!jwtFilter.isAdmin()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("access Denied. Only admins can add the products");
			}
			
			String name= requestBody.containsKey("name") ? requestBody.get("name").toString() :null;
			String description= requestBody.containsKey("description") ? requestBody.get("description").toString() :null;
			Integer price= requestBody.containsKey("price") ? Integer.valueOf(requestBody.get("price").toString()) :null;
			String status= requestBody.containsKey("status") ? requestBody.get("status").toString() :null;
			Integer categoryId= requestBody.containsKey("categoryId") ? Integer.valueOf(requestBody.get("categoryId").toString()) :null;
			
			Optional<Category> categoryOptional= categoryService.getCategoryById(categoryId);
			if(categoryOptional.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category ID.category does not exists");
			}
			
			Category category= new Category();
			category.setId(categoryId);
			
			Product product = new Product();
			product.setName(name);
			product.setDescription(description);
			product.setPrice(price);
			product.setCategory(categoryOptional.get());
			product.setStatus("true");
			
			productService.addProduct(product);
			
			return ResponseEntity.ok("Product added successfully!!");
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured while adding the product.");
		}
		
	}
	
	@GetMapping(path="/all")
	public List<Product> allProducts(){
		return productService.getallProduct();
	}
	
	@PostMapping(path="/update")
	public ResponseEntity<String> updateProduct(@RequestBody Product productDetails){
		return productService.updateProduct(productDetails);
	}
	
	@DeleteMapping(path="/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id){
		return productService.deleteProduct(id);
	}
	
	@PostMapping(path="/updateStatus")
	public ResponseEntity<String> updateStatus(@RequestBody Product product){
		return productService.updateStatus(product);
	}
	
	@GetMapping(path="/getByCategory")
	public ResponseEntity<List<Product>> findProductsByCategory(@RequestParam("id") Integer id){
		return productService.findByCategory(id);
	}
	
	@GetMapping(path="/getProduct/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id){
		return productService.getProductById(id);
	}

}
