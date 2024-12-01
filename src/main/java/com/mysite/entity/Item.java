package com.mysite.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {
	
	@Id
	private int id;
	
	private String name;
	
	private int price;
}
