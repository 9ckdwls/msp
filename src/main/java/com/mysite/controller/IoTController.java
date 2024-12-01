package com.mysite.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mysite.dto.IoTResponse;
import com.mysite.service.IoTService;
import com.mysite.service.UserService;

@RestController
@RequestMapping("/msp")
public class IoTController {

	private final IoTService ioTService;
	private final UserService userService;

	public IoTController(IoTService ioTService, UserService userService) {
		this.ioTService = ioTService;
		this.userService = userService;
	}

	// IoT 문열기
	@GetMapping("/openBox/{id}")
	public CompletableFuture<ResponseEntity<String>> openBox(@PathVariable(name = "id") int boxId) {
		String role = userService.getCurrentUserRole(); // 사용자 권한(Role) 가져오기

		CompletableFuture<String> ioTResponse;

		if (role.equals("ROLE_ADMIN")) {
			ioTResponse = ioTService.employeeOpenBox(boxId);
		} else if (role.equals("ROLE_USER"))
			ioTResponse = ioTService.userOpenBox(boxId);
		else {
			return CompletableFuture
					.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 요청입니다."));
		}
		
		return ioTResponse.thenApply(response -> {
		    return ResponseEntity.ok("문이 열렸습니다: " + response);
		}).exceptionally(
		    ex -> {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문 열기 실패: " + ex.getMessage());
		    }
		);

	}

	// IoT 문닫기
	@GetMapping("/closeBox/{id}")
	public CompletableFuture<ResponseEntity<IoTResponse>> closeBox(@PathVariable(name = "id") int boxId) {
		String userId = userService.getCurrentUserId(); // 사용자 ID 가져오기
		String role = userService.getCurrentUserRole(); // 사용자 권한(Role) 가져오기
		
		CompletableFuture<IoTResponse> ioTResponse;

		if (role.equals("ROLE_ADMIN")) {
			ioTResponse = ioTService.employeeCloseBox(boxId);
		} else if (role.equals("ROLE_USER"))
			ioTResponse = ioTService.userCloseBox(boxId);
		else {
			return CompletableFuture
					.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body(null));
		}
		
		return ioTResponse.thenApply(response -> {
			if (role.equals("ROLE_ADMIN")) {
				ioTService.collectionComplete(userId, boxId, response.getWeight());
	        } else if (role.equals("ROLE_USER")) {
	        	ioTService.RecyclingComplete(userId, boxId, response.getWeight());
	        }
			ioTService.addBoxSensorLog(response);
			return ResponseEntity.ok(response);
		}).exceptionally(
				ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
	}

	// 윤식이랑 IoT 통신 테스트를 위한 임시 URL
	@GetMapping("/updateBoxSensorLog")
	public String info(@ModelAttribute IoTResponse Response) {
		ioTService.addBoxSensorLog(Response);
		return "ok";
	}

}
