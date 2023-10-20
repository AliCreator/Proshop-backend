package com.advance.service;

import java.util.List;

import com.advance.entity.Review;

public interface ReviewService {

	Review addReview(Review review); 
	Review getReview(Long reviewId); 
	Review getReviewByUserId(Long userId); 
	List<Review> getAllReview(); 
	Review updateReview(Review review); 
	Boolean deleteReview(Long reviewId); 
}
