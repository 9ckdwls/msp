package com.mysite.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Embeddable;

@Embeddable
public class BoxSensorLogId implements Serializable {

    private int id;    // 센서 로그 ID
    private Date time; // 시간

    // 기본 생성자, equals(), hashCode() 메서드 필요
    public BoxSensorLogId() {}

    public BoxSensorLogId(int id, Date time) {
        this.id = id;
        this.time = time;
    }
    
    // equals(), hashCode() 메서드 오버라이드
}
