package com.mysite.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.dto.OrderDTO;
import com.mysite.entity.Item;
import com.mysite.entity.OrderDetails;
import com.mysite.entity.OrderId;
import com.mysite.repository.OrderRepository;
import com.mysite.service.ItemService;
import com.mysite.service.UserService;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final ItemService itemService;
	private UserService userService;

	public OrderService(OrderRepository orderRepository, ItemService itemService, UserService userService) {
		this.orderRepository = orderRepository;
		this.itemService = itemService;
		this.userService = userService;
	}

	// 장바구니에 상품 추가
	public void basketAdd(OrderDTO orderDTO) {
		List<OrderDetails> orders = orderRepository.findByOrderIdUserIdAndState(userService.getCurrentUserId(), 0);
		Item item = itemService.getItem(orderDTO.getItemId());
		
		OrderDetails newOrder = new OrderDetails();
		OrderId newOrderId = new OrderId();

		if (!orders.isEmpty()) {
			for (OrderDetails order : orders) {
				// 기존 장바구니에 주문이 있고 동일한 상품이 있다면
				if (order.getOrderId().getItemId() == orderDTO.getItemId()) {
					order.setCount(order.getCount() + orderDTO.getCount());
					order.setPrice(order.getCount() * item.getPrice());
					orderRepository.save(order);
					return;
				}
			}
			
			newOrderId.setId(orders.get(0).getOrderId().getId());
			newOrderId.setUserId(userService.getCurrentUserId());
			newOrderId.setItemId(orderDTO.getItemId());

			newOrder.setOrderId(newOrderId);
			newOrder.setCount(orderDTO.getCount());
			newOrder.setPrice(item.getPrice() * orderDTO.getCount());
			newOrder.setState(0);
			
			orderRepository.save(newOrder);
			
		} else {
			// 기존 장바구니에 주문이 없다면 새로운 주문 추가
			Optional<OrderDetails> topOrder = orderRepository.findTopByOrderByOrderIdIdDesc();
			int newOrderIdId = topOrder.map(order -> order.getOrderId().getId() + 1).orElse(1); // 첫 주문일 경우 1로 시작

			newOrderId.setId(newOrderIdId);
			newOrderId.setUserId(userService.getCurrentUserId());
			newOrderId.setItemId(orderDTO.getItemId());

			newOrder.setOrderId(newOrderId);
			newOrder.setCount(orderDTO.getCount());
			newOrder.setPrice(item.getPrice() * orderDTO.getCount());
			newOrder.setState(0);
			
			orderRepository.save(newOrder);
		}
	}
	
	//내 장바구니 보기
	public List<OrderDetails> myBakset() {
		return orderRepository.findByOrderIdUserIdAndState(userService.getCurrentUserId(), 0);
	}

	// 장바구니에서 상품 주문
	public String order() {
		List<OrderDetails> orders = orderRepository.findByOrderIdUserIdAndState(userService.getCurrentUserId(), 0);
		
		if(orders.isEmpty()) {
			return "fail";
		}
		else {
			for(OrderDetails order : orders) {
				order.setState(1);
				order.setDate(new Date());
				orderRepository.save(order);
			}
			return "ok";
		}
	}

	//장바구니 상품 수량 조절 0이면 삭제
	public void basketDelete(OrderDTO orderDTO) {
		List<OrderDetails> orders = orderRepository.findByOrderIdUserIdAndState(userService.getCurrentUserId(), 0);
		Item item = itemService.getItem(orderDTO.getItemId());
		
		if (!orders.isEmpty()) {
			for (OrderDetails order : orders) {
				if (order.getOrderId().getItemId() == orderDTO.getItemId()) {
					if(orderDTO.getCount() == 0) {
						orderRepository.delete(order);
						return;
					}
					order.setCount(orderDTO.getCount());
					order.setPrice(orderDTO.getCount() * item.getPrice());
					orderRepository.save(order);
				}
			}
		} else {
			return;
		}
	}

	//주문내역 보기
	public List<OrderDetails> myOrderList() {
		return orderRepository.findByOrderIdUserIdAndState(userService.getCurrentUserId(), 1);
	}

}
