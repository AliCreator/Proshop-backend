package com.advance.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginForm {

	@NotNull(message = "Please provide email!")
	@Email
	private String email; 
	
	@NotNull(message = "Please provide password!")
	private String password;
}
