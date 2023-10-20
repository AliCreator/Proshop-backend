package com.advance.resource;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.advance.entity.MyResponse;
import com.advance.entity.Product;
import com.advance.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductResource {

	private final ProductService productService;

	@PostMapping("/add/{userId}")
	public ResponseEntity<MyResponse> addProduct(@PathVariable("userId") Long userId, @RequestBody Product product) {
		Product newProduct = productService.addProduct(userId, product);
		MyResponse myResponse = MyResponse.builder().timestamp(LocalDateTime.now().toString())
				.message("Product has been added!").httpStatus(HttpStatus.CREATED.value()).status(HttpStatus.CREATED)
				.data(Map.of("product", newProduct)).build();

		return ResponseEntity.created(getURI()).body(myResponse);
	}
	
	@GetMapping("/find/{productId}")
	public ResponseEntity<MyResponse> getProductByProductId(@PathVariable("productId") Long productId) {
		Product product = productService.getProduct(productId);
		MyResponse myResponse = MyResponse.builder().timestamp(LocalDateTime.now().toString())
		.message("Product retrieved!")
		.httpStatus(HttpStatus.OK.value())
		.status(HttpStatus.OK)
		.data(Map.of("product", product))
		.build(); 
		
		return ResponseEntity.ok().body(myResponse);
	}
	
	@GetMapping
	public ResponseEntity<MyResponse> getAllProducts() {
		Iterable<Product> allProducts = productService.getAllProducts();
		List<Product> products = new ArrayList<>(); 
		allProducts.forEach(products::add);
		MyResponse myResponse = MyResponse.builder().timestamp(LocalDateTime.now().toString())
				.message("Products retrieved!")
				.httpStatus(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.data(Map.of("products", products))
				.build(); 
				
				return ResponseEntity.ok().body(myResponse);
	}

	private URI getURI() {
		return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/find/<productId>").toUriString());
	}

}
