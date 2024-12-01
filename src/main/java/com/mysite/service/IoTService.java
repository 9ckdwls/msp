package com.mysite.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.mysite.dto.IoTResponse;
import com.mysite.entity.Box;
import reactor.core.publisher.Mono;
import java.util.concurrent.CompletableFuture;
import java.time.Duration;
import java.net.ConnectException;

@Service
public class IoTService {

	private final WebClient.Builder webClientBuilder;
	private final BoxService boxService;
	private final CollectionService collectionService;
	private final RecyclingService recyclingService;
	private final UserService userService;
	private final BoxSensorLogService boxSensorLogService;

	public IoTService(WebClient.Builder webClientBuilder, BoxService boxService, CollectionService collectionService,
			RecyclingService recyclingService, UserService userService, BoxSensorLogService boxSensorLogService) {
		this.webClientBuilder = webClientBuilder;
		this.boxService = boxService;
		this.collectionService = collectionService;
		this.recyclingService = recyclingService;
		this.userService = userService;
		this.boxSensorLogService = boxSensorLogService;
	}
	
	//수거함 id로 수거함 찾기
	private Box findBoxById(int id) {
		return boxService.findBoxById(id);
	}

	// 직원용 문열기
	public CompletableFuture<String> employeeOpenBox(int boxId) {
		return sendIoTRequest(findBoxById(boxId).getAddress(), "/outopen");
	}

	// 사용자용 문열기
	public CompletableFuture<String> userOpenBox(int boxId) {
		
		return sendIoTRequest(findBoxById(boxId).getAddress(), "/inopen");
	}

	// 공통된 IoT 요청을 처리하는 메서드
	// 공통된 IoT 요청을 처리하는 메서드
	private CompletableFuture<String> sendIoTRequest(String address, String uri) {
	    WebClient webClient = webClientBuilder.baseUrl("http://" + address).build();

	    Mono<String> responseMono = webClient.get().uri(uri)
	            .retrieve().bodyToMono(String.class).timeout(Duration.ofSeconds(60)) // 시간 초과 처리
	            .onErrorResume(e -> {
	                System.out.println("오류 발생!");
	                if (e instanceof java.net.SocketTimeoutException) {
	                    return Mono.just("시간 초과로 인해 요청을 처리할 수 없습니다.");
	                } else if (e instanceof ConnectException) {
	                    // 네트워크 연결 문제 처리
	                    return Mono.just("네트워크 연결 실패: IoT 장비에 연결할 수 없습니다.");
	                } else {
	                    return Mono.just("네트워크 오류로 요청을 처리할 수 없습니다.");
	                }
	            });

	    System.out.println("서비스 단에서 리턴");
	    return responseMono.toFuture();
	}


	// 직원용 문닫기
    public CompletableFuture<IoTResponse> employeeCloseBox(int boxId) {
        return sendIoTResponseRequest(findBoxById(boxId).getAddress(), "/outclose");
    }

    // 사용자용 문닫기
    public CompletableFuture<IoTResponse> userCloseBox(int boxId) {
        return sendIoTResponseRequest(findBoxById(boxId).getAddress(), "/inclose");
    }

	// 공통된 IoT 요청 (응답 타입이 IoTResponse인 경우)
	private CompletableFuture<IoTResponse> sendIoTResponseRequest(String address, String uri) {
		WebClient webClient = webClientBuilder.baseUrl("http://" + address).build();
		
		Mono<IoTResponse> responseMono = webClient.get().uri(uri)
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
	
	//수거함 센서로그 추가
	public void addBoxSensorLog(IoTResponse iotResponse) {
		boxSensorLogService.addBoxSensorLog(iotResponse);
		//수거함 센서로그 값이 바뀌면 수거함 상태도 바꿔야 함
		boxService.boxUpdate(iotResponse);
	}
	
	//수거자 수거 완료
	public void collectionComplete(int BoxId, int weight) {
		//수거 로그 추가
		collectionService.addLog(userService.getCurrentUserId(), BoxId, weight);
	}
	
	//일반사용자 분리 완료
	public void RecyclingComplete(int BoxId, int weight) {
		//분리 로그 추가
		recyclingService.addLog(userService.getCurrentUserId(), BoxId, weight);
		//사용자 포인트 증가
		userService.addPoint(userService.getCurrentUserId(), weight);
	}
}
