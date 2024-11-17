package com.mysite.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public CompletableFuture<ResponseEntity<String>> openBox(@PathVariable Long id) {
		String userId = userService.getCurrentUserId(); // 사용자 ID 가져오기
		String role = userService.getCurrentUserRole(); // 사용자 권한(Role) 가져오기

		// 직원일 때 문열기
		if (role.equals("ROLE_ADMIN")) { // 관리자 권한 체크
			return ioTService.employeeOpenBox() // IoT 문열기 요청
					.thenApply(response -> {
						// 성공적으로 문을 열었을 경우
						return ResponseEntity.ok("문이 열렸습니다: " + response);
					}).exceptionally(ex -> {
						// 예외 발생 시
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body("문 열기 실패: " + ex.getMessage());
					});
		} else { // todo 여기에 일반유저의 경우 문열기 처리
			// 권한이 없을 경우 403 Forbidden 반환
			return CompletableFuture
					.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("문을 열 권한이 없습니다."));
		}
	}

	@GetMapping("/closeBox/{id}")
	public CompletableFuture<ResponseEntity<String>> closeBox(@PathVariable Long id) {
		String userId = userService.getCurrentUserId(); // 사용자 ID 가져오기
		String role = userService.getCurrentUserRole(); // 사용자 권한(Role) 가져오기

		// 직원일 때 문닫기
		if (role.equals("ROLE_ADMIN")) { // 관리자 권한 체크
			return ioTService.employeeCloseBox() // IoT 문닫기 요청
					.thenApply(response -> {
						// 성공적으로 문을 닫았을 경우
						return ResponseEntity.ok("문이 닫혔습니다: " + response);
					}).exceptionally(ex -> {
						// 예외 발생 시
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body("문 닫기 실패: " + ex.getMessage());
					});
		} else { // todo 여기에 일반유저의 경우 문닫기 처리
			// 권한이 없을 경우 403 Forbidden 반환
			return CompletableFuture
					.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("문을 닫을 권한이 없습니다."));
		}
	}
}
