package com.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@ResponseBody
public class AdminController {
	@GetMapping("/admin")
	public String adminP() {
		return "Admin Controller";
	}
	
}
