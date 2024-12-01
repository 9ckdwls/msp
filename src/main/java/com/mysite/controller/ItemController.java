package com.mysite.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.entity.Item;
import com.mysite.service.ItemService;

@RestController
@RequestMapping("/msp")
public class ItemController {
	private final ItemService itemService;
	
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	//상품리스트 보기
	@GetMapping("items")
	public List<Item> Items() {
		return itemService.Items();
	}
}
