package com.advance.service;

import java.util.List;

import com.advance.entity.ShippingAddress;

public interface ShippingAddressService {

	ShippingAddress addShippingAddress(ShippingAddress address); 
	ShippingAddress getShippingAddress(Long shippingAddressId); 
	ShippingAddress getShippingAddressByUserId(Long userId); 
	List<ShippingAddress> getAllShippingAddresses();
	ShippingAddress updateShippingAddress(ShippingAddress address); 
	Boolean deleteShippingAddress(Long addressId); 
}
