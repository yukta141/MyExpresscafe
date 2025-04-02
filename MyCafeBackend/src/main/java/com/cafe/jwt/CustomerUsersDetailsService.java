package com.cafe.jwt;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cafe.Repository.UserRepository;
import com.cafe.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	private com.cafe.model.User userDetail;
	
	

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		userDetail= userRepository.findByEmail(username);
//	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info(username);
		  userDetail = userRepository.findByEmail(username)
		            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

		    return new org.springframework.security.core.userdetails.User(
		            userDetail.getEmail(),
		            userDetail.getPassword(),
		            new ArrayList<>() // Corrected the ArrayList instantiation
		    );
	}
	
	
//	 public User getUserDetails(String email) {
//	        return userRepository.findByEmail(email)
//	                .map(user -> {
//	                    user.setPassword(null); 
//	                })
//	                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//	    }
	 
	 public com.cafe.model.User  getUserDetails() {
		    return userDetail;
		           
		}

	


}
