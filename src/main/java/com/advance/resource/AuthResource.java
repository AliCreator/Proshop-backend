package com.advance.resource;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.advance.dto.UserDTO;
import com.advance.dtomapper.DtoMapper;
import com.advance.entity.MyResponse;
import com.advance.entity.User;
import com.advance.form.LoginForm;
import com.advance.provider.TokenProvider;
import com.advance.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {

	private final UserService userService;
	private final AuthenticationManager authManager;
	private final TokenProvider provider;
	
	@PostMapping("/login")
	public ResponseEntity<MyResponse> login(@RequestBody LoginForm form, HttpServletResponse response) {
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));
		User user = getUser(authentication); 
		String token = provider.generateAccessToken(user);
		Cookie cookie = new Cookie("jwt", token);
		cookie.setHttpOnly(true); 
		cookie.setMaxAge(864000);
//		cookie.setSecure(true)
		response.addCookie(cookie); 
		MyResponse myResponse = MyResponse.builder().timestamp(LocalDateTime.now().toString())
				.message("User successfully logged in!").httpStatus(HttpStatus.OK.value()).status(HttpStatus.OK)
				.build();
		return ResponseEntity.ok().body(myResponse);
	}

	@PostMapping("/register")
	public ResponseEntity<MyResponse> registerUser(@RequestBody User user) {

		UserDTO dto = userService.register(user);
		MyResponse myResponse = MyResponse.builder().timestamp(LocalDateTime.now().toString())
				.message("New user has been created!").data(Map.of("user", dto)).status(HttpStatus.CREATED)
				.httpStatus(HttpStatus.CREATED.value()).build();
		return ResponseEntity.created(getURI()).body(myResponse);
	}

	@GetMapping("/find/id/{id}")
	public ResponseEntity<MyResponse> getUserById(@PathVariable("id") Long id) {

		UserDTO dto = userService.getUserById(id);
		MyResponse myResponse = MyResponse.builder().timestamp(LocalDateTime.now().toString())
				.message("User retrieved!").httpStatus(HttpStatus.OK.value()).status(HttpStatus.OK)
				.data(Map.of("user", dto)).build();
		return ResponseEntity.ok().body(myResponse);
	}

	@GetMapping("/find/{email}")
	public ResponseEntity<MyResponse> getUserByEmail(@PathVariable("email") String email) {
		System.out.println("It didn't get here. Or did it?");

		UserDTO dto = userService.getUserByEmail(email.trim().toLowerCase());
		MyResponse myResponse = MyResponse.builder().timestamp(LocalDateTime.now().toString())
				.message("User retrieved!").httpStatus(HttpStatus.OK.value()).status(HttpStatus.OK)
				.data(Map.of("user", dto)).build();
		return ResponseEntity.ok().body(myResponse);
	}

	@PutMapping("/update/{id}")
	
	public ResponseEntity<MyResponse> updateUserName(@PathVariable("id") Long id, @RequestParam("name") String name) {
		UserDTO dto = userService.updateUserName(name, id);
		MyResponse myResponse = MyResponse.builder().timestamp(LocalDateTime.now().toString())
				.message("User name updated!").httpStatus(HttpStatus.OK.value()).status(HttpStatus.OK)
				.data(Map.of("user", dto)).build();
		return ResponseEntity.ok().body(myResponse);
	}

	@PutMapping("/update/role/{id}")
	public ResponseEntity<MyResponse> updateUserRole(@PathVariable("id") Long id) {
		UserDTO dto = userService.updateUserRole(true, id);
		MyResponse myResponse = MyResponse.builder().timestamp(LocalDateTime.now().toString())
				.message("User role updated!").httpStatus(HttpStatus.OK.value()).status(HttpStatus.OK)
				.data(Map.of("user", dto)).build();
		return ResponseEntity.ok().body(myResponse);
	}

	private URI getURI() {
		return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/find/<userId>").toUriString());
	}
	
	private UserDTO getUserDTO(Authentication auth) {
		return DtoMapper.convertToDTO(((User) auth.getPrincipal()).getUser()); 
	}
	
	private User getUser(Authentication auth) {
		return ((User) auth.getPrincipal()).getUser();
	}
}
