package com.mysite.service;

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
	
	public void joinProcess(JoinDTO joinDTO) {
		String userId = joinDTO.getUserId();
		String userPw = joinDTO.getUserPw();
		
		String userName = joinDTO.getUserName();
		String userAdd = joinDTO.getUserAdd();
		int userP = joinDTO.getUserP();
		String userDate = joinDTO.getUserDate();
		String userEmail = joinDTO.getUserEmail();
		
		String userRole = joinDTO.getUserRole();
		
		Boolean isExist = userRepository.existsByUserId(userId);
		
		if(isExist) {
			return;
		}
		
		User data = new User();
		data.setUserId(userId);
		data.setUserPw(bCryptPasswordEncoder.encode(userPw));
		//권한을 주려면 "ROLE_" 뒤에 써줘야 한다고 함
		data.setUserRole("ROLE_ADMIN");
		
		data.setUserName(userName);
		data.setUserAdd(userAdd);
		data.setUserP(userP);
		data.setUserDate(userDate);
		data.setUserEmail(userEmail);
		
		userRepository.save(data);
	}
}
