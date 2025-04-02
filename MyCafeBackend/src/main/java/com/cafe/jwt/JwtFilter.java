package com.cafe.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	 private CustomerUsersDetailsService service;
	
	Claims claims= null;
	private String username=null;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token= request.getHeader("Authorization");
		if(token!= null && token.startsWith("Bearer ")) {
			token=token.substring(7);
		    username= jwtUtil.extractUsername(token);
			claims= jwtUtil.extractAllClaims(token);
		
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = service.loadUserByUsername(username);
			
			if(jwtUtil.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		}
		filterChain.doFilter(request, response);
	}
	
	public boolean isAdmin() {
		return "admin".equalsIgnoreCase((String) claims.get("role"));
	}
	
	public boolean isUser() {
		return "user".equalsIgnoreCase((String) claims.get("role"));
	}
	public String getCurrentUser() {
		return username;
	}

}
