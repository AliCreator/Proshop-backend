package com.advance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.advance.filter.CustomAuthenticationFilter;
import com.advance.handler.CustomAccessDeniedHandler;
import com.advance.handler.CustomEntryPointHandler;

import lombok.RequiredArgsConstructor;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomEntryPointHandler entryPointHandler;
	private final CustomAuthenticationFilter authenticationFilter;
	private final UserDetailsService detailsService;
	private final BCryptPasswordEncoder encoder;

	public static final String[] PUBLIC_URLS = {"/auth/register/**", "/auth/login/**", "/products/**",};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(withDefaults()).csrf(c -> c.disable());
		http.authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_URLS).permitAll());
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.exceptionHandling(
				ex -> ex.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(entryPointHandler));
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(detailsService);
		provider.setPasswordEncoder(encoder);
		return new ProviderManager(provider);
	} 
}
