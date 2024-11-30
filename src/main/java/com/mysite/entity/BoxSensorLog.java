package com.mysite.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoxSensorLog {

    @EmbeddedId
    private BoxSensorLogId id; // 복합 키 클래스

    private int temperature; // 온도
    private int humidity;    // 습도
    private int weight;      // 무게
    private int sparkStatus; // 스파크 여부 (1: 있음, 0: 없음)
    private int fire;
}
