package com.mysite.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mysite.dto.CustomUserDetails;
import com.mysite.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTFilter extends OncePerRequestFilter{
	
	private final JWTUtil jwtUtil;
	
	public JWTFilter(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorzation = request.getHeader("Authorization");
		
		if(authorzation == null || !authorzation.startsWith("Bearer ")) {
			System.out.println("token null");
			
			filterChain.doFilter(request, response);
			
			return;
		}
		
		String token = authorzation.split(" ")[1];
		
		if(jwtUtil.isExpired(token)) {
			System.out.println("token expired");
			
			filterChain.doFilter(request, response);
			
			return;
		}
		
		String userId = jwtUtil.getUserId(token);
		String role =jwtUtil.getRole(token);
		
		User userEntity = new User();
		userEntity.setUserId(userId);
		userEntity.setUserPw("pw");
		userEntity.setUserRole(role);
		
		CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
		
		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authToken);
		
		filterChain.doFilter(request, response);
	}

}






