package com.mysite.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true) // 예상치 못한 필드를 무시
@Getter
@Setter
public class IoTResponse {
    private int id; // 수거함 id
    private int temperature; // 온도
    private int humidity; // 습도
    private int weight; // 무게인데 포인트 0점, 10점, 20점
    private int sparkStatus; // 불꽃
    private int fire; // 화재 여부
    private int weightNow;  // 수거함 용량 퍼센트
    private String status;  // 추가된 필드
    private String message; // 추가된 필드

    public IoTResponse() {
    }

    public IoTResponse(String status, int weight) {
        this.status = status;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "IoTResponse{" +
               "id=" + id +
               ", temperature=" + temperature +
               ", humidity=" + humidity +
               ", weight=" + weight +
               ", sparkStatus=" + sparkStatus +
               ", fire=" + fire +
               ", weightNow=" + weightNow +
               ", status='" + status + '\'' +
               ", message='" + message + '\'' +
               '}';
    }
}
