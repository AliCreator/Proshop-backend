package com.advance.provider;

import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.advance.entity.User;
import com.advance.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {

	private static final String KEY = "SUPER-SECRET-KEY";
	private static final String AUTHORITIES = "Authorities";
	private static final String AUDIENCE = "Proshop Users";
	private static final String ISSUER = "JAN ALI ZAHEDI";

	private final UserRepository userRepo;

	public String generateAccessToken(User user) {
		return JWT.create().withIssuer(ISSUER).withAudience(AUDIENCE).withIssuedAt(new Date()).withExpiresAt(new Date(currentTimeMillis() + 864000000L ))
				.withArrayClaim(AUTHORITIES, getClaimsFromUser(user))
				.withSubject(String.valueOf(user.getId()))
				.sign(Algorithm.HMAC512(KEY));
	}

	
	public Authentication getAuthentication(List<GrantedAuthority> authorities, Long id, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userRepo.findById(id).get(),null, authorities);
		token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return token;
	}
	
	public List<GrantedAuthority> getAuthorities(String token) {
		String[] claims = getClaimsFromToken(token); 
		return stream(claims).map(SimpleGrantedAuthority::new).collect(toList());
	}
	
	public Boolean isTokenValid(String token, Long id) {
		return Objects.nonNull(id) && !isTokenExpired(token); 
	}
	
	public Long getSubject(HttpServletRequest request, String token) {
		try {
			return Long.valueOf(getJWTVerifier().verify(token).getSubject()); 
		} catch (TokenExpiredException e) {
			request.setAttribute("expiredMessage", e.getMessage());
			throw e;
		} catch (InvalidClaimException e) {
			request.setAttribute("invalidClaim", e.getMessage());
			throw e;
		}
	}
	
	private Boolean isTokenExpired(String token) {
		Date expDate = getJWTVerifier().verify(token).getExpiresAt(); 
		return expDate.before(new Date()); 
	}
	
	
	private String[] getClaimsFromUser(User user) {
		return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
	}
	
	private String[] getClaimsFromToken(String token) {
		return getJWTVerifier().verify(token).getClaim(AUTHORITIES).asArray(String.class); 
	}
	
	private JWTVerifier getJWTVerifier() {
		JWTVerifier verifier;
		try {
			verifier = JWT.require(Algorithm.HMAC512(KEY)).withIssuer(ISSUER).build();
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException(e.getMessage());
		}

		return verifier;
	}
}
