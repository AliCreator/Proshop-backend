package com.advance.repository;

import org.springframework.data.repository.CrudRepository;

import com.advance.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
