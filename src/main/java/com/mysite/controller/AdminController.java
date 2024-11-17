package com.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;

// 관리자 권한을 확인하기 위한 일시 페이지
@Controller
@ResponseBody
public class AdminController {
	@GetMapping("/admin")
	public String adminP() {
		return "Admin Controller";
	}
	
}
