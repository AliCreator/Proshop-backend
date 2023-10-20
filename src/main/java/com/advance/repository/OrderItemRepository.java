package com.advance.repository;

import org.springframework.data.repository.CrudRepository;

import com.advance.entity.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long>{

}
