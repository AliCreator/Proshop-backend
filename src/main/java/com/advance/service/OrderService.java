package com.advance.service;

import java.util.List;

import com.advance.entity.Order;

public interface OrderService {

	Order addOrder(Order order); 
	Order getOrder(Long orderId); 
	List<Order> getAllOrders(); 
	Order updateOrder(Order order); 
	Boolean deleteOrder(Long orderId); 
}
