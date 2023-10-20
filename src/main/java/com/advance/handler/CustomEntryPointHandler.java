package com.advance.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.advance.entity.MyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomEntryPointHandler implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		MyResponse myResponse = MyResponse.builder().timestamp(LocalDateTime.now().toString())
		.reason("You need to login!")
		.httpStatus(HttpStatus.FORBIDDEN.value())
		.status(HttpStatus.FORBIDDEN)
		.build();
		
		response.setContentType(APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		
		OutputStream stream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(stream, myResponse);
		stream.flush();
	}

}
