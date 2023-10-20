package com.advance.service;

import java.util.List;

import com.advance.entity.Product;

public interface ProductService {

	Product addProduct(Long userId, Product product);
	Product updateProduct(Product product); 
	Product getProduct(Long productId); 
	Iterable<Product> getAllProducts();
	Boolean deleteProduct(Long productId); 
}
