package com.mysite.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.EmbeddedId;

@Entity
@Getter
@Setter
public class Recycling {

    @EmbeddedId
    private RecyclingId recyclingId;  // 복합 기본키 클래스 사용

    private int weight;
    
    private int userP;

    // 기본 생성자, getter, setter 등 생략
}
