package com.mysite.controller;

import com.mysite.entity.Box;
import com.mysite.service.BoxService;
import com.mysite.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/msp")
public class BoxContorller {

	private final BoxService service;
	private final UserService userService;

	public BoxContorller(BoxService service, UserService userService) {
		this.service = service;
		this.userService = userService;
	}

	// 근처 수거함을 반환
	// latitude 위도
	// longitude 경도
	// radius 주위 반경(m)
	@GetMapping("/nearby")
	public List<Box> getNearbyBoxes(@RequestParam(value = "latitude") double latitude,
			@RequestParam(value = "longitude") double longitude, @RequestParam(value = "radius") double radius) {
		return service.findNearbyBoxes(latitude, longitude, radius);
	}

	//수거함 상세보기
	@GetMapping("/searchBox/{id}")
	public Box searchBox(@PathVariable(value = "id") int id) {
		Box box = service.findBoxById(id);
		return box;
	}
	
	//분리 및 수거하기
	@GetMapping("/openBox/{id}")
	public void openBox(@PathVariable(value = "id") int id) {
		String userId = userService.getCurrentUserId(); // 사용자 ID 가져오기
        String role = userService.getCurrentUserRole(); // 사용자 권한(Role) 가져오기
        
        if(role.equals("ROLE_ADMIN")) {
        	//관리자 문열기
        	//api 요청
        }
        else {
        	//일반 사용자 문열기
        	//api 요청
        }
        
        //api 요청이 성공하면 완료 표시를 위한 return 필요
	}
	
	//분리 및 수거하기 완료 시 요청을 받아서 문 닫기 api 요청 보내야함
	//이때, 무게를 받아서 포인트로 변환하는 로직 필요
	//배출량 및 수거량 로그도 추가하는 로직필요
	
	
}
