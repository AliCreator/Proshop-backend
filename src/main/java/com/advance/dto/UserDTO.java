package com.advance.dto;

import java.time.LocalDate;

import com.advance.enumeration.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(value = Include.NON_DEFAULT)
public class UserDTO {
	
	
	
	private Long id;
	
	@NotNull(message = "Please provide a name")
	private String name; 
	
	@Email
	@NotNull(message = "Please provide an email")
	private String email; 
	
	private Boolean isAdmin; 
	
	private LocalDate createdAt;
	
	private UserRole role;



}
