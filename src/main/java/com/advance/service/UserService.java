package com.advance.service;

import com.advance.dto.UserDTO;
import com.advance.entity.User;

public interface UserService {

	UserDTO register(User user); 
	
	UserDTO updateUserName(String name, Long userId);
	
	
	UserDTO updateUserRole(Boolean isAdmin, Long userId);
	
	UserDTO getUserByEmail(String email); 
	
	UserDTO getUserById(Long userId); 
	

}
