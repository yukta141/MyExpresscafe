package com.cafe.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.jwt.CustomerUsersDetailsService;
import com.cafe.jwt.JwtFilter;
import com.cafe.jwt.JwtUtil;
import com.cafe.model.User;
import com.cafe.service.UserService;
import com.cafe.wrapper.UserWrapper;


@RestController
@RequestMapping(path="/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	 private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomerUsersDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@Autowired
//	private EmailService emailService;
	
	
	@PostMapping(path="/signup")
	public ResponseEntity<String> signupUser(@RequestBody User user){
		String response = userService.userSignUp(user);
		return ResponseEntity.ok(response);
	}
	 
	 @PostMapping("/login")
	 public ResponseEntity<?> login(@RequestBody User user) {
	     try {
	         User authenticatedUser = userService.getUserByEmail(user.getEmail());

	         if (authenticatedUser == null) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                     .body("{\"message\":\"User not found\"}");
	         }

	         if (!passwordEncoder.matches(user.getPassword(), authenticatedUser.getPassword())) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                     .body("{\"message\":\"Bad Credentials\"}");
	         }

	         Authentication auth = authenticationManager.authenticate(
	                 new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

	         if (auth.isAuthenticated()) {
	             if ("true".equalsIgnoreCase(authenticatedUser.getStatus())) {
	                 String token = jwtUtil.createToken(authenticatedUser.getEmail(), authenticatedUser.getRole());
	                 return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
	             } else {
	                 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                         .body("{\"message\":\"Wait for admin approval.\"}");
	             }
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                 .body("{\"message\":\"Bad Credentials\"}");
	     }
	     return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	             .body("{\"message\":\"Authentication failed\"}");
	 }
	 
	 
	 @GetMapping("/all")
	    public ResponseEntity<?> getAllUsers() {
	        if (!jwtFilter.isAdmin()) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body("{\"message\":\"Access Denied: Admins only\"}");
	        }
	        
	        List<UserWrapper> users = userService.getAllUser();
	        return ResponseEntity.ok(users);
	    }
	 
	 @GetMapping("/allAdmins")
		 public ResponseEntity<?> getAllAdmin(){
			 if(!jwtFilter.isAdmin()) {
				 return ResponseEntity.status(HttpStatus.FORBIDDEN)
						 .body("{\"message\":\"Access Denied: Admins only\"}");
			 }
			 List<String>admin = userService.getAllAdmin();
			 return ResponseEntity.ok(admin);
		 }
	    
	 
	 @PostMapping(path="update")
	 public ResponseEntity<String> update(@RequestBody Map<String, Object> requestBody ){
		 try {
			 if(!jwtFilter.isAdmin()) {
				 return ResponseEntity.status(HttpStatus.FORBIDDEN)
						 .body("{\"message\":\"Access Denied. Only admins can update user status.\"}");
			 }
			 
			 Integer id = Integer.valueOf(requestBody.get("id").toString());
			 String status = requestBody.get("status").toString();
			 
			 if(id==null || status==null) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						 .body("{\"message\":\"Invalid Request. ID and Status are required.\"}");
			 }
			 
			 Optional<User> optionalUser= userService.getUserById(id);
			 if(optionalUser.isEmpty()) {
				 return ResponseEntity.status(HttpStatus.NOT_FOUND)
						 .body("{\"message\":\"User not found.\"}");
			 }
			 
			 User user= optionalUser.get();
			 user.setStatus(status);
			 userService.saveUser(user);
			 
			  List<String> adminEmails = userService.getAllAdmin();

//		        // Send email notification to all admins
//		        String subject = "User Approval Notification";
//		        String message = "User " + user.getName() + " has been approved by Admin: " + jwtFilter.getCurrentUser();
//		        emailService.sendEmail(adminEmails, subject, message);
			 
			 return ResponseEntity.ok("{\"message\":\"User status updated successfully.\"}");
			 
		 }catch(Exception e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					 .body("{\"message\":\"An error occured.\"}");
		 }
	 }
	 
	 @GetMapping("/checkToken")
	    public ResponseEntity<Map<String, String>> checkToken() {
	        if (jwtFilter.getCurrentUser() == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(Collections.singletonMap("message", "Invalid or missing token"));
	        }

	        Map<String, String> response = new HashMap<>();
	        response.put("username", jwtFilter.getCurrentUser());
	        response.put("role", jwtFilter.isAdmin() ? "admin" : "user");

	        return ResponseEntity.ok(response);
	    }


	

}
