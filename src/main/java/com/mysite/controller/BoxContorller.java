package com.mysite.controller;

import com.mysite.entity.Box;
import com.mysite.service.BoxService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/msp")
public class BoxContorller {

	private final BoxService service;

	public BoxContorller(BoxService service) {
		this.service = service;
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
		return service.findBoxById(id);
	}
	
	//수거함 이름으로 검색하기
	@GetMapping("/searchByBoxName/{name}")
	public Box searchBox(@PathVariable(value = "name") String name) {
		return service.findBoxByName(name);
	}
	
	//알림이 필요한 수거함
	@GetMapping("alarm")
	public List<Box> alarm() {
		return service.alarm();
	}
	
	
}
