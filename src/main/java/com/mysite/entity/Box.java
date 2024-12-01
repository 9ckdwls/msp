package com.mysite.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Getter
@Setter
public class Box {
	@Id
	private int id;
	
	private String name;
	
	private String address;
	
	private Point location;
	
	private int used;
	
	private int fire;
	
	//반환되는 JSON 값을 문자열로 바꾸기
	@JsonProperty("location")
    public String getLocationAsText() {
        return location != null ? location.toText() : null;
    }
}
