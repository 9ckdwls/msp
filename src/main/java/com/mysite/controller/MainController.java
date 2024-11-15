package com.mysite.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {
	@GetMapping("/")
	public String mainP() {
		
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		System.out.println("Test: " + authentication);
		System.out.println(org.hibernate.Version.getVersionString());
		
		Collection<? extends GrantedAuthority> authorites = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorites.iterator();
		GrantedAuthority auth = iter.next();
		String role = auth.getAuthority();
		
		return "Main Controller" + userId + role; 
	}

}
