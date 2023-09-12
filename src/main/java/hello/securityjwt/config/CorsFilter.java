package hello.securityjwt.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsFilter {
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true); // 내서버가 응답시 json을 자바스크립에서 처리핤 있을지를 설정 true 자바스크립트 허용 
		configuration.setAllowedOrigins(Arrays.asList("*")); // 모든 ip를 허용
		configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 header에 응답을 허용
		configuration.setAllowedMethods(Arrays.asList("*")); // 모든 post get put delete patch 허용

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", configuration);
	
		return source;
	}

}
