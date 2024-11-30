package com.mysite.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.entity.Recycling;
import com.mysite.service.RecyclingService;
import com.mysite.service.UserService;

@RestController
@RequestMapping("/msp")
public class RecyclingController {
	
	private final RecyclingService recyclingService;
	private final UserService userService;
	
	public RecyclingController(RecyclingService recyclingService, UserService userService) {
		this.recyclingService = recyclingService;
		this.userService = userService;
	}

	//내 재활용 로그 가져오기
	@GetMapping("/myRecycling")
	public List<Recycling> getMyRecycling() {
		String userId = userService.getCurrentUserId(); // 사용자 ID 가져오기
		return recyclingService.findCollectionByRecyclingId_UserId(userId);
	}
}
