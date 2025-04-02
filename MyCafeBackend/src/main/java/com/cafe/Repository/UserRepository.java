package com.cafe.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafe.model.User;
import com.cafe.wrapper.UserWrapper;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
//	User findByEmailId(@Param("email") String email);
	Optional<User> findByEmail(String email);
	

    @Query("SELECT new com.cafe.wrapper.UserWrapper(u.id, u.name, u.email, u.contactNumber, u.status) FROM User u WHERE u.role = 'USER'")
    List<UserWrapper> findAllUsersWithRoleUser();
    
    @Query("select u.email from User u where u.role='admin'")
    List<String> findAllAdmin();
}
