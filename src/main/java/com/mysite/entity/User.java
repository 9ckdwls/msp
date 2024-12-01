package com.mysite.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
	@Id
	private String userId;
	private String userPw;
	
	private String userName;
	private String userAdd;
	private int userP;
	private Date userDate;
	private String userEmail;
	
	private String userRole;
	
}
