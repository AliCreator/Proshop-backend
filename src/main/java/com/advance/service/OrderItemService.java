package com.advance.service;

import java.util.List;

import com.advance.entity.OrderItem;

public interface OrderItemService {

	OrderItem addOrderItem(OrderItem orderItem); 
	OrderItem getOrderItem(Long orderItemId); 
	List<OrderItem> getAllOrderItem(); 
	OrderItem udpateOrderItem(OrderItem orderItem); 
	Boolean deleteOrderItem(Long orderItemId);
}
