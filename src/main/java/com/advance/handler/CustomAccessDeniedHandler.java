package com.advance.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.advance.entity.MyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		MyResponse myResponse = MyResponse.builder().timestamp(LocalDateTime.now().toString())
				.reason("You don't have permission!").httpStatus(HttpStatus.UNAUTHORIZED.value())
				.status(HttpStatus.UNAUTHORIZED).build();

		response.setContentType(APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		OutputStream stream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(stream, myResponse);

		stream.flush();
	}

}
