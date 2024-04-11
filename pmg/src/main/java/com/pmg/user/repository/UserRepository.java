package com.pmg.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmg.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserId(String userId);
	Optional<User> findByUserPassword(String userPassword);
	
}
