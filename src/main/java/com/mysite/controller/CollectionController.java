package com.mysite.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.entity.Collection;
import com.mysite.service.CollectionService;
import com.mysite.service.UserService;

@RestController
@RequestMapping("/msp")
public class CollectionController {
	
	private final CollectionService collercionService;
	private final UserService userService;
	
	public CollectionController(CollectionService collercionService, UserService userService) {
		this.collercionService = collercionService;
		this.userService = userService;
	}
	
	//내 수거 로그 가져오기
	@GetMapping("/myCollection")
	public List<Collection> getMyCollection() {
		String userId = userService.getCurrentUserId(); // 사용자 ID 가져오기
		System.out.println("sdfkjdslkfjkl");
		return collercionService.findCollectionByCollectionId_UserId(userId);
	}
}
