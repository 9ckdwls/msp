package com.mysite.service;

import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.dto.JoinDTO;
import com.mysite.entity.User;
import com.mysite.repository.UserRepository;

@Service
public class JoinService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository =userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public String joinProcess(JoinDTO joinDTO) {
		String userId = joinDTO.getUserId();
		String userPw = joinDTO.getUserPw();
		
		String userName = joinDTO.getUserName();
		String userAdd = joinDTO.getUserAdd();
		int userP = joinDTO.getUserP();
		String userEmail = joinDTO.getUserEmail();
		
		Boolean isExist = userRepository.existsByUserId(userId);
		
		if(isExist) {
			return null;
		}
		
		User data = new User();
		data.setUserId(userId);
		data.setUserPw(bCryptPasswordEncoder.encode(userPw));

		if(joinDTO.getUserRole() == null) {
			//권한을 주려면 "ROLE_" 뒤에 써줘야 한다고 함
			data.setUserRole("ROLE_USER");
		} else if(joinDTO.getUserRole().equals("admin9")) {
			data.setUserRole("ROLE_ADMIN");
		} else {
			return null;
		}
		
		data.setUserName(userName);
		data.setUserAdd(userAdd);
		data.setUserP(userP);
		data.setUserDate(new Date());
		data.setUserEmail(userEmail);
		
		userRepository.save(data);
		
		return "ok";
	}
}
