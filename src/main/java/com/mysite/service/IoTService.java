package com.mysite.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mysite.dto.IoTResponse;
import reactor.core.publisher.Mono;
import java.util.concurrent.CompletableFuture;
import java.time.Duration;
import java.net.ConnectException;

@Service
public class IoTService {

	private final WebClient.Builder webClientBuilder;

	public IoTService(WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}

	// 직원용 문열기
	public CompletableFuture<String> employeeOpenBox(String address) {
		return sendIoTRequest(address, "/outopen");
	}

	// 사용자용 문열기
	public CompletableFuture<String> userOpenBox(String address) {
		return sendIoTRequest(address, "/inopen");
	}

	// 공통된 IoT 요청을 처리하는 메서드
	private CompletableFuture<String> sendIoTRequest(String address, String uri) {
		WebClient webClient = webClientBuilder.baseUrl("http://" + address).build();

		Mono<String> responseMono = webClient.get().uri(uri) // 동적으로 URI 설정
				.retrieve().bodyToMono(String.class).timeout(Duration.ofSeconds(60)) // 시간 초과 처리
				.onErrorResume(e -> {
					if (e instanceof java.net.SocketTimeoutException) {
						return Mono.just("시간 초과로 인해 요청을 처리할 수 없습니다.");
					} else if (e instanceof ConnectException) {
						// 네트워크 연결 문제 처리
						return Mono.just("네트워크 연결 실패: IoT 장비에 연결할 수 없습니다.");
					} else {
						return Mono.just("네트워크 오류로 요청을 처리할 수 없습니다.");
					}
				});

		return responseMono.toFuture();
	}

	// 직원용 문닫기
    public CompletableFuture<IoTResponse> employeeCloseBox(String address) {
        return sendIoTResponseRequest(address, "/outclose");
    }

    // 사용자용 문닫기
    public CompletableFuture<IoTResponse> userCloseBox(String address) {
        return sendIoTResponseRequest(address, "/intclose");
    }

	// 공통된 IoT 요청 (응답 타입이 IoTResponse인 경우)
	private CompletableFuture<IoTResponse> sendIoTResponseRequest(String address, String uri) {
		WebClient webClient = webClientBuilder.baseUrl("http://" + address).build();
		
		Mono<IoTResponse> responseMono = webClient.get().uri(uri) // 동적으로 URI 설정
				.retrieve().bodyToMono(IoTResponse.class).timeout(Duration.ofSeconds(60)) // 시간 초과 처리
				.onErrorResume(e -> {
					if (e instanceof java.net.SocketTimeoutException) {
						return Mono.just(new IoTResponse("시간 초과", 0));
					} else if (e instanceof ConnectException) {
						// 네트워크 연결 문제 처리
						return Mono.just(new IoTResponse("네트워크 연결 실패", 0));
					} else {
						return Mono.just(new IoTResponse("네트워크 오류", 0));
					}
				});

		return responseMono.toFuture();
	}
}
