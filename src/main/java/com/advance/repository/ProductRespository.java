package com.advance.repository;

import org.springframework.data.repository.CrudRepository;

import com.advance.entity.Product;

public interface ProductRespository extends CrudRepository<Product, Long>{

}
