package com.mysite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	//userId가 존재하는지
	Boolean existsByUserId(String userId);
	
	//userID로 UserEntity 가져오기
	User findByUserId(String userId);
}
