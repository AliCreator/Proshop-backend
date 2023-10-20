package com.advance.filter;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.advance.provider.TokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

	private final TokenProvider provider; 
	
	private static final String JWT = "jwt";
	private static final String[] PUBLIC_URLS = { "/auth/register", "/auth/login", "/products", "/products/find/**", "/products/update/**" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Cookie[] cookies = request.getCookies(); 
		System.out.println("I am here");
		String jwt = null; 
		for(Cookie cookie:cookies) {
			if(JWT.equals(cookie.getName())) {
				jwt = cookie.getValue();
				break;
			}
		}
		System.out.println(jwt);
		
		if(jwt != null && !jwt.isEmpty()) {
			Long id = getUser(request, jwt);
			if(provider.isTokenValid(jwt, id)) {
				List<GrantedAuthority> authorities = provider.getAuthorities(jwt);
				Authentication authentication = provider.getAuthentication(authorities, id, request);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(request, response);

	}

	private Long getUser(HttpServletRequest request, String jwt) {
		return provider.getSubject(request, jwt);
	}

	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//		|| !asList(request.getCookies()).contains(JWT)
		return request.getCookies() == null 
				|| request.getMethod().equalsIgnoreCase("OPTION")
				|| asList(PUBLIC_URLS).contains(request.getRequestURI());
	}

}
