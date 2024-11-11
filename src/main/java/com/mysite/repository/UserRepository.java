package com.mysite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{
	
	//userId가 존재하는지
	Boolean existsByUserId(String userId);
	
	//userID로 UserEntity 가져오기
	UserEntity findByUserId(String userId);
}
