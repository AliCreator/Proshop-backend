package com.advance.entity;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(value = Include.NON_DEFAULT)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderItem> orders;
	
	
	@OneToMany(mappedBy = "order")
	private Set<ShippingAddress> shippingAddress;
	
	@NotNull(message = "Please provide the payment method")
	private String paymentMethod;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PaymentResult> paymentResult;
	
	
	@NotNull(message = "Please provide the items price")
	private Double itemsPrice;
	
	@NotNull(message = "Please provide the tax price")
	private Double taxPrice;
	
	@NotNull(message = "Please provide the shipping price")
	private Double shippingPrice;
	
	@NotNull(message = "Please provide the total price")
	private Double totalPrice;
	
	@NotNull(message = "Please provide the status of the pay")
	private Boolean isPaid;
	
	private LocalDate paidAt; 
	
	@NotNull(message = "Please provide the status of the delivery")
	private Boolean isDelivered;
	
	private LocalDate deliveredAt; 
	
	private LocalDate createdAt; 
}
