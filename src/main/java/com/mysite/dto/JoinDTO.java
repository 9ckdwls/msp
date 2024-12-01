package com.mysite.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {
	private String userId;
	private String userPw;
	
	private String userName;
	private String userAdd;
	private int userP;
	private String userEmail;
	
	private String userRole;
}
