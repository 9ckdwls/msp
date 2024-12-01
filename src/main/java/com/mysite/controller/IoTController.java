package com.mysite.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
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
	public String openBox(@PathVariable(name = "id") int boxId) {
	    String role = userService.getCurrentUserRole(); // 사용자 권한(Role) 가져오기
	    CompletableFuture<String> ioTResponse;

	    if (role.equals("ROLE_ADMIN")) {
	        ioTResponse = ioTService.employeeOpenBox(boxId);
	    } else if (role.equals("ROLE_USER")) {
	        ioTResponse = ioTService.userOpenBox(boxId);
	    } else {
	        return null;
	    }

	    try {
	        // 비동기 작업이 끝날 때까지 기다리고 결과 반환
	        String response = ioTResponse.join(); // join() 사용하여 비동기 결과를 기다림
	        System.out.println("비동기 처리 끝?");
	        System.out.println(response);
	        return response;  // join()이 완료되면 그 값을 CompletableFuture로 반환
	    } catch (CompletionException e) {
	        // 예외 처리
	    	System.out.println("예외 실행");
	        return null;
	    }
	}

	// IoT 문닫기
	@GetMapping("/closeBox/{id}")
	public IoTResponse closeBox(@PathVariable(name = "id") int boxId) {
		String role = userService.getCurrentUserRole(); // 사용자 권한(Role) 가져오기
		
		CompletableFuture<IoTResponse> ioTResponse;

		if (role.equals("ROLE_ADMIN")) {
			ioTResponse = ioTService.employeeCloseBox(boxId);
		} else if (role.equals("ROLE_USER"))
			ioTResponse = ioTService.userCloseBox(boxId);
		else {
			return null;
		}
		
		try {
	        // 비동기 작업이 끝날 때까지 기다리고 결과 반환
			IoTResponse response = ioTResponse.join(); // join() 사용하여 비동기 결과를 기다림
	        System.out.println(response);
	        return response;  // join()이 완료되면 그 값을 CompletableFuture로 반환
	    } catch (CompletionException e) {
	        // 예외 처리
	    	System.out.println("예외 실행");
	        return null;
	    }
	}

	// 윤식이랑 IoT 통신 테스트를 위한 임시 URL
	@GetMapping("/updateBoxSensorLog")
	public String info(@ModelAttribute IoTResponse Response) {
		ioTService.addBoxSensorLog(Response);
		return "ok";
	}

}
