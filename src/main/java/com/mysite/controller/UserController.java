package com.mysite.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mysite.entity.User;
import com.mysite.service.UserService;

@RestController
@RequestMapping("/msp")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/myInfo")
	public User myInfo() {
		String userId = userService.getCurrentUserId(); // 사용자 ID 가져오기

		User user = userService.findUserByUserId(userId);
		return user;
	}
}
