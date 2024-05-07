package com.otothang.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otothang.models.User;
import org.springframework.data.jpa.repository.Query;


public interface Repository extends JpaRepository<User, Long>{
	User findByUserName(String userName);
	@Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
	public User findByVerificationCode(String code);
}
