package com.payment.db.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.payment.db.entities.User;

public interface UserRepository extends JpaRepository<User, Serializable> {
	@Query("select u from User u where u.userName = ?1 and u.status = ?2")
	Optional<User> findByUserName(String username, String status);
	
	@Query("select u from User u where u.userId = ?1 and u.status = ?2")
	Optional<User> findByUserId(Long userId, String status);
	
	Boolean existsByUserName(String username);
	
	Boolean existsByUserId(Long id);

}
