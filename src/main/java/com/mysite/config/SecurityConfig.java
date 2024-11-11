package com.mysite.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.mysite.jwt.JWTFilter;
import com.mysite.jwt.JWTUtil;
import com.mysite.jwt.LoginFilter;

import jakarta.servlet.http.HttpServletRequest;

//환경설정파일이다.
@Configuration
//웹시큐리티를 위한
@EnableWebSecurity
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;
	
	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
		this.authenticationConfiguration = authenticationConfiguration;
		this.jwtUtil = jwtUtil;
	}
	
	//비밀번호 암호화
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			.cors((cors)->cors
					.configurationSource(new CorsConfigurationSource() {
						@Override
						public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
							CorsConfiguration configuration = new CorsConfiguration();
							
							configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
							configuration.setAllowedMethods(Collections.singletonList("*"));
							configuration.setAllowCredentials(true);
							configuration.setAllowedHeaders(Collections.singletonList("*"));
							configuration.setMaxAge(3600L);
							
							configuration.setExposedHeaders(Collections.singletonList("Authorization"));
							return configuration;
						}
					} ));
		//csrf disable
		http
			.csrf((auth) -> auth.disable());
		//Form 로그인 방식 disable
		http
			.formLogin((auth) -> auth.disable());
		//http basic 인증 방식 disable
		http
			.httpBasic((auth) -> auth.disable());
		// "/login", "/", "/join" 경로는 모두 허용
		// "/admin" 경로는 "ADMIN" 권한을 가진 사용자만 허용
		//그외는 로그인 성공 후 허용
		http
			.authorizeHttpRequests((auth) -> auth
					.requestMatchers("/login", "/", "/join").permitAll()
					.requestMatchers("/admin").hasRole("ADMIN")
					.anyRequest().authenticated());
		http
			.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
		http
			.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
		//세션 설정
		//JWT를 사용할 때는 세션을 "STATELESS"로 관리
		http
			.sessionManagement((session) -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
}
