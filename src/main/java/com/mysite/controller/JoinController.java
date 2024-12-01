package com.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.dto.JoinDTO;
import com.mysite.service.JoinService;

@Controller
@ResponseBody
public class JoinController {
	
	private final JoinService joinService;
	
	public JoinController(JoinService joinService) {
		this.joinService = joinService;
	}
	
	@PostMapping("/join")
	public String joinProcess(JoinDTO joinDTO) {
		return joinService.joinProcess(joinDTO);
	}
}
