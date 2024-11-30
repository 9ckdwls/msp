package com.mysite.controller;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.dto.IoTResponse;
import com.mysite.entity.Box;
import com.mysite.repository.BoxRepository;
import com.mysite.service.CollectionService;
import com.mysite.service.IoTService;
import com.mysite.service.RecyclingService;
import com.mysite.service.UserService;

@RestController
@RequestMapping("/msp")
public class IoTController {

	private final IoTService ioTService;
	private final UserService userService;
	private final CollectionService collectionService;
	private final RecyclingService recyclingService;
	private final BoxRepository boxRepository;

	public IoTController(IoTService ioTService, UserService userService, CollectionService collectionService,
			RecyclingService recyclingService, BoxRepository boxRepository) {
		this.ioTService = ioTService;
		this.userService = userService;
		this.collectionService = collectionService;
		this.recyclingService = recyclingService;
		this.boxRepository = boxRepository;
	}

	// IoT 문열기
	@GetMapping("/openBox/{id}")
	public CompletableFuture<ResponseEntity<String>> openBox(@PathVariable int id) {
		return processBoxRequest(id, "open");
	}

	// IoT 문닫기
	@GetMapping("/closeBox/{id}")
	public CompletableFuture<ResponseEntity<String>> closeBox(@PathVariable int id) {
		return processBoxRequest(id, "close");
	}

	// 공통된 Box 처리 로직
	private CompletableFuture<ResponseEntity<String>> processBoxRequest(int id, String action) {
		String userId = userService.getCurrentUserId(); // 사용자 ID 가져오기
		String role = userService.getCurrentUserRole(); // 사용자 권한(Role) 가져오기

		// Box 검색
		Optional<Box> optionalBox = boxRepository.findById(id);
		if (!optionalBox.isPresent()) {
			return CompletableFuture
					.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 Box를 찾을 수 없습니다."));
		}

		Box box = optionalBox.get();
		String address = box.getAddress(); // ip주소 값 가져오기

		// 역할에 따른 요청 처리
		if ("open".equals(action)) {
			return handleBoxOpenRequest(role, address, userId, id);
		} else if ("close".equals(action)) {
			return handleBoxCloseRequest(role, address, userId, id);
		} else {
			return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다."));
		}
	}

	// 문열기 처리
	private CompletableFuture<ResponseEntity<String>> handleBoxOpenRequest(String role, String address, String userId,
			int id) {
		if (role.equals("ROLE_ADMIN")) {
			return handleBoxOpenRequest(ioTService.employeeOpenBox(address), userId, id);
		} else if (role.equals("ROLE_USER")) {
			return handleBoxOpenRequest(ioTService.userOpenBox(address), userId, id);
		} else {
			return CompletableFuture
					.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("문을 열 권한이 없습니다."));
		}
	}

	// 문닫기 처리
	private CompletableFuture<ResponseEntity<String>> handleBoxCloseRequest(String role, String address, String userId,
			int id) {
		if (role.equals("ROLE_ADMIN")) {
			return handleBoxCloseRequest(ioTService.employeeCloseBox(address), collectionService, null, userId, id);
		} else if (role.equals("ROLE_USER")) {
			return handleBoxCloseRequest(ioTService.userCloseBox(address), null, recyclingService, userId, id);
		} else {
			return CompletableFuture
					.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("문을 닫을 권한이 없습니다."));
		}
	}

	// 공통된 문열기 로직을 처리하는 메서드
	private CompletableFuture<ResponseEntity<String>> handleBoxOpenRequest(CompletableFuture<String> boxOpenMethod,
			String userId, int id) {
		return boxOpenMethod.thenApply(response -> {
			return ResponseEntity.ok("문이 열렸습니다: " + response);
		}).exceptionally(ex -> {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문 열기 실패: " + ex.getMessage());
		});
	}

	// 공통된 문닫기 로직을 처리하는 메서드
	private CompletableFuture<ResponseEntity<String>> handleBoxCloseRequest(
			CompletableFuture<IoTResponse> boxCloseMethod, CollectionService collectionService,
			RecyclingService recyclingService, String userId, int id) {
		return boxCloseMethod.thenApply(response -> {
			if (collectionService != null) {
				collectionService.addLog(userId, id, response.getWeight());
			} else if (recyclingService != null) {
				recyclingService.addLog(userId, id, response.getWeight());
			}

			return ResponseEntity.ok("문이 닫혔습니다: " + response);
		}).exceptionally(ex -> {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문 닫기 실패: " + ex.getMessage());
		});
	}

	// 윤식이랑 IoT 통신 테스트를 위한 임시 URL
	@GetMapping("/info")
	public void info(@RequestParam("temperature") int temperature, @RequestParam("humidity") int humidity) {
		System.out.println("temperature: " + temperature);
		System.out.println("humidity: " + humidity);
	}

}
