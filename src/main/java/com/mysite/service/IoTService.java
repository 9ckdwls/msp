package com.mysite.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Service
public class IoTService {

	private final WebClient webClient;

	public IoTService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://iot-device-address").build(); // IoT 장비 주소
	}

	public CompletableFuture<String> employeeOpenBox() {
		// 1. IoT 장비에 비동기 요청을 보냅니다.
		Mono<String> responseMono = webClient.post().uri("/send-signal") // IoT 장비의 문 열기 요청 URI
				.retrieve().bodyToMono(String.class);

		// 2. 응답이 오기 전까지 다른 작업 수행은 필요 없으므로 삭제

		// 3. 응답 Mono를 CompletableFuture로 변환하여 비동기적으로 처리
		return responseMono.toFuture(); // 결과를 CompletableFuture로 반환
	}
	
	public CompletableFuture<String> employeeCloseBox() {
		// 1. IoT 장비에 비동기 요청을 보냅니다.
		Mono<String> responseMono = webClient.post().uri("/send-signal") // IoT 장비의 문 닫기 요청 URI
				.retrieve().bodyToMono(String.class);

		// 2. 응답이 오기 전까지 다른 작업 수행은 필요 없으므로 삭제

		// 3. 응답 Mono를 CompletableFuture로 변환하여 비동기적으로 처리
		return responseMono.toFuture(); // 결과를 CompletableFuture로 반환
	}
}
