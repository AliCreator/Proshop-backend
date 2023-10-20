package com.advance.dtomapper;

import org.springframework.beans.BeanUtils;

import com.advance.dto.UserDTO;
import com.advance.entity.User;

public class DtoMapper {

	public static User convertToUser(UserDTO dto) {
		User user = new User();
		BeanUtils.copyProperties(dto, user);
		return user; 
	}
	
	public static UserDTO convertToDTO(User user) {
		UserDTO dto = new UserDTO();
		BeanUtils.copyProperties(user, dto);
		return dto; 
	}
}
