package com.mysite.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.entity.Item;
import com.mysite.repository.ItemRepository;

@Service
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	// 상품Id로 상품검색
	public Item getItem(int itemId) {
		Optional<Item> optionalItem  = itemRepository.findById(itemId);
		return optionalItem.get();
	}

	//상품리스트 보기
	public List<Item> Items() {
		return itemRepository.findAll();
	}
	
}
