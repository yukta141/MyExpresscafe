package com.cafe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;

import com.cafe.Repository.ProductRepository;
import com.cafe.jwt.JwtFilter;
import com.cafe.model.Product;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	public List<Product> getallProduct(){
		return productRepository.findAll();
	}
	
	public ResponseEntity<String> updateProduct(Product productDetails){
		try {
		if(!jwtFilter.isAdmin()) {
			return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied. Only admin can update the product");
		}
		
		if(productDetails.getId()==null) {
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product id not found");
		}
		
		Optional<Product> optionalProduct= productRepository.findById(productDetails.getId());
		
		if(optionalProduct.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
		}
		
		Product existingProduct= optionalProduct.get();
		existingProduct.setName(productDetails.getName());
//		existingProduct.setCategory(productDetails.getCategory());
		existingProduct.setDescription(productDetails.getDescription());
		existingProduct.setPrice(productDetails.getPrice());
		if(productDetails.getStatus()!= null) {
			existingProduct.setStatus(productDetails.getStatus());
		}
		
		
		productRepository.save(existingProduct);
		
		return ResponseEntity.ok("Product updated successfully");
		
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error Occured");
		}
	}
	
	
	public ResponseEntity<String> deleteProduct(Integer id){
		try {
			if(!jwtFilter.isAdmin()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied. Only admin can delete the product.");
			}
			
			Optional<Product> optionalProduct= productRepository.findById(id);
			if(optionalProduct.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
			}
			
			productRepository.deleteById(id);	
			return ResponseEntity.ok("Product Deleted successfully");
		
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured");
		}
	}
	
	public ResponseEntity<String> updateStatus(Product product){
		try {
			if(!jwtFilter.isAdmin()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Deanied. only admin can update the status");
			}
			
			Optional<Product> optionalProduct= productRepository.findById(product.getId());
			if(optionalProduct.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
			}
			
			Product products= optionalProduct.get();
			products.setStatus(product.getStatus());
			System.out.println("status of product is:"+ product.getStatus());
			productRepository.save(products);
			
			return ResponseEntity.ok("Status updated successfully");
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured");
		}
	}
	
	public ResponseEntity<List<Product>> findByCategory(Integer id){
		List<Product> products= productRepository.findByCategoryId(id);
		
		if(products.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
		}
		return ResponseEntity.ok(products);
	}
	
	public ResponseEntity<Product> getProductById(Integer id){
		Optional<Product> optionalproduct= productRepository.findById(id);
		
		if(optionalproduct.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.ok(optionalproduct.get());
	}
}
