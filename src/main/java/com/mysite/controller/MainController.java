package com.mysite.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.service.UserService;

@Controller
@ResponseBody
public class MainController {
	
	private final UserService userService;
	
	public MainController(UserService userService) {
        this.userService = userService;
    }
	
	@GetMapping("/")
	public String mainP() {
		
		String userId = userService.getCurrentUserId(); // 사용자 ID 가져오기
        String role = userService.getCurrentUserRole(); // 사용자 권한(Role) 가져오기

        return "Main Controller - User ID: " + userId + ", Role: " + role;
	}

}
