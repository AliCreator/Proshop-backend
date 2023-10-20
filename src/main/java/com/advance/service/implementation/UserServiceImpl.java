package com.advance.service.implementation;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.advance.dto.UserDTO;
import com.advance.dtomapper.DtoMapper;
import com.advance.entity.User;
import com.advance.enumeration.UserRole;
import com.advance.exception.ApiException;
import com.advance.repository.UserRepository;
import com.advance.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final BCryptPasswordEncoder encoder;

	@Override
	public UserDTO register(User user) {

		if (getEmailCount(user.getEmail()) > 0)
			throw new ApiException("Email is already registered. Please login!");
		try {
			
			user.setIsAdmin(false);
			user.setEmail(user.getEmail().trim().toLowerCase());
			System.out.println("It gets here");
			user.setPassword(encoder.encode(user.getPassword()));
			System.out.println("It gets here after password");
			user.setRole(UserRole.ROLE_USER);
			user.setCreatedAt(LocalDate.now());
			
			return DtoMapper.convertToDTO(userRepo.save(user));

		} catch (Exception e) {
			throw new ApiException("Something went wrong!");
		}
	}

	private Integer getEmailCount(String email) {
		return userRepo.getEmailCount(email);
	}

	@Override
	public UserDTO updateUserName(String name, Long userId) {
		try {
			User user = userRepo.findById(userId).get();
			user.setName(name);
			return DtoMapper.convertToDTO(userRepo.save(user));
		} catch (Exception e) {
			throw new ApiException("User was not found!");
		}
	}

	@Override
	public UserDTO updateUserRole(Boolean isAdmin, Long userId) {
		try {
			User user = userRepo.findById(userId).get();
			user.setIsAdmin(true);
			return DtoMapper.convertToDTO(userRepo.save(user));
		} catch (Exception e) {
			throw new ApiException("User was not found!");
		}
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		try {
			return DtoMapper.convertToDTO(userRepo.getUserByEmail(email));
		} catch (Exception e) {
			throw new ApiException("User was not found!");
		}
	}

	@Override
	public UserDTO getUserById(Long userId) {
		try {
			return DtoMapper.convertToDTO(userRepo.findById(userId).get());
		} catch (Exception e) {
			throw new ApiException("User was not found!");
		}
	}

}
