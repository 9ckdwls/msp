package com.mysite.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.dto.OrderDTO;
import com.mysite.entity.OrderDetails;

@RestController
@RequestMapping("/msp")
public class OrderController {
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	//장바구니에 상품 추가
	@PostMapping("/basketAdd")
	public void basketAdd(@RequestBody OrderDTO orderDTO) {
		orderService.basketAdd(orderDTO);
	}
	
	//내 장바구니 보기
	@GetMapping("/myBasket")
	public List<OrderDetails> mybasket() {
		return orderService.myBakset();
	}
	
	//장바구니에서 주문
	@GetMapping("/order")
	public String order() {
		return orderService.order();
	}
	
	//장바구니 상품 수량 조절 0이면 삭제
	@PostMapping("/basketDelete")
	public void basketDelete(@RequestBody OrderDTO orderDTO) {
		orderService.basketDelete(orderDTO);
	}
	
	//주문내역 보기
	@GetMapping("/myOrderList")
	public List<OrderDetails> myOrderList() {
		return orderService.myOrderList();
	}
}
