package com.advance.service.implementation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.advance.entity.Product;
import com.advance.entity.User;
import com.advance.exception.ApiException;
import com.advance.repository.ProductRespository;
import com.advance.repository.UserRepository;
import com.advance.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRespository productRepo;
	private final UserRepository userRepo;

	@Override
	public Product addProduct(Long userId, Product product) {
		try {
			User user = userRepo.findById(userId).get();
			product.setUser(user);
			product.setCreatedAt(LocalDate.now());
			if (product.getRating() == null) {
				product.setRating(0f);
			}
			if (product.getNumReviews() == null) {
				product.setNumReviews(0);
			}
			return productRepo.save(product);
		} catch (Exception e) {
			throw new ApiException("User was not found!");
		}
	}

	@Override
	public Product updateProduct(Product product) {

		try {
			return productRepo.save(product);
		} catch (Exception e) {
			throw new ApiException("Something went wrong!");
		}
	}

	@Override
	public Product getProduct(Long productId) {
		try {
			return productRepo.findById(productId).get();
		} catch (Exception e) {
			throw new ApiException("Product was not found!");
		}
	}

	@Override
	public Iterable<Product> getAllProducts() {
		try {
			return productRepo.findAll();
		} catch (Exception e) {
			throw new ApiException("Something went wrong!");
		}
	}

	@Override
	public Boolean deleteProduct(Long productId) {
		try {
			productRepo.deleteById(productId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
