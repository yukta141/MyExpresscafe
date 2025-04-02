package com.cafe.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cafe.Repository.UserRepository;
import com.cafe.model.User;
import com.cafe.wrapper.UserWrapper;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String userSignUp(User user) {
		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
		if(existingUser.isPresent()) {
			return "Email already exists";
		}
		
		User users= new User();
		users.setName(user.getName());
		users.setContactNumber(user.getContactNumber());
		users.setEmail(user.getEmail());
		users.setPassword(passwordEncoder.encode(user.getPassword()));
		users.setStatus("false");
		users.setRole("user");
		
		userRepository.save(users);
		return "user registered successfully";
		
		
	}
	
	 public User getUserByEmail(String email) {
	        return userRepository.findByEmail(email)
	                .orElse(null); 
	    }
	 
	 public List<UserWrapper>getAllUser(){
		return userRepository.findAllUsersWithRoleUser();		 
	 }
	 
	 public Optional<User> getUserById(Integer id){
		 return userRepository.findById(id);
	 }
	 
	 public User saveUser(User user) {
		 return userRepository.save(user);
	 }
	 
	 public List<String> getAllAdmin(){
		 return userRepository.findAllAdmin();
	 }
	
	

}
