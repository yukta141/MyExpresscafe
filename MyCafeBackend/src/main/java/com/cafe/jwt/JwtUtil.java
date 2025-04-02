package com.cafe.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtUtil {

	private String secret_key="n1fOq1N2Mb8eYX6aVrK2xwO0Rtx1a6RyVmsrQRO3bXE=";
	
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject); 
	}
	
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		  final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
}
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		return extractClaims(token, Claims::getExpiration).before(new Date());
	}
	
	public String createToken(String username, String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		return generateToken(claims, username);
		 
	}
	
	private String generateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
			   .setClaims(claims)
			   .setSubject(subject)
			   .setIssuedAt(new Date(System.currentTimeMillis()))
			   .setExpiration(new Date(System.currentTimeMillis()+1000 *60* 60*10))
			   .signWith(SignatureAlgorithm.HS256, secret_key).compact();
			   
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
