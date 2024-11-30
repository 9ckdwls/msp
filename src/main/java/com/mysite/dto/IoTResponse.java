package com.mysite.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // 예상치 못한 필드를 무시
public class IoTResponse {
    private int weight;
    private String status;  // 추가된 필드
    private String message; // 추가된 필드

    // 기본 생성자
    public IoTResponse() {
    }

    // status와 weight를 받는 생성자 추가
    public IoTResponse(String status, int weight) {
        this.status = status;
        this.weight = weight;
    }

    // Getter 및 Setter 메서드
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "IoTResponse{" +
               "weight=" + weight +
               ", status='" + status + '\'' +
               ", message='" + message + '\'' +
               '}';
    }
}
