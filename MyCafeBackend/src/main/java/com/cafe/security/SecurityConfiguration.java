package com.cafe.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	
	@Bean
	public SecurityFilterChain securityfilterChain(HttpSecurity http) throws Exception{
		http
		.authorizeHttpRequests(authorizeRequests->
		authorizeRequests
		.requestMatchers(requestMatcher("/user/**")).permitAll()
		.requestMatchers(requestMatcher("/category/**")).permitAll()
		.requestMatchers(requestMatcher("/product/**")).permitAll()
//		.requestMatchers(requestMatcher("/userlogin")).permitAll()

//		.requestMatchers(requestMatcher("/**")).permitAll()
		

			.anyRequest().authenticated())
			.formLogin(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.logout(Customizer.withDefaults());
//			.sessionManagement(session -> session
//					.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//					.invalidSessionUrl("/login?expired"));
		return http.build();
	}
	
	private RequestMatcher requestMatcher(String pattern)
	{
		return new AntPathRequestMatcher(pattern);
	}
	
	
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		config.setAllowedMethods(Arrays.asList("PUT","PATCH","DELETE","GET","POST"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
	
	@Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	
}
