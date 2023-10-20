package com.advance.entity;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(value = Include.NON_DEFAULT)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@NotNull(message = "Please provide a name")
	private String name; 
	
	@NotNull(message = "Please provide an image")
	private String image;
	
	
	@NotNull(message = "Please provide a brand")
	private String brand;
	
	@NotNull(message = "Please provide a category")
	private String category;
	
	@NotNull(message = "Please provide a description")
	private String description; 
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private Set<Review> reviews;
	
	
	@NotNull(message = "Please provide a rating average")
	private Float rating;
	
	
	@NotNull(message = "Please provide the number of reviews")
	private Integer numReviews;
	
	@NotNull(message = "Please provdie the price")
	private Double price;
	
	
	@NotNull(message = "Please provide the count in the stock")
	private Integer countInStock;
	
	private LocalDate createdAt;
	
}
