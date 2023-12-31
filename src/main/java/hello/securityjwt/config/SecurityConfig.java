package hello.securityjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import hello.securityjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final CorsConfigurationSource corsConfigurationSource;
	private final UserRepository userRepository;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	
		/*
		 * 등록시 내가 만든 필터보다 먼저 실행 before 또는 after 
		 */
		//http.addFilterBefore(new JwtFilter3(), BasicAuthenticationFilter.class);	// FilterConfig 로 이관 드
		
		http.csrf(AbstractHttpConfigurer::disable);

		// login 페이지 사용안함
		http.formLogin(form -> form.disable());
		// authorization basic : id, pw 를 가지고 있다가 계속 로그인을 시도하면서 인증하는 방식으로 사용안함
		http.httpBasic(form -> form.disable());
		
//		http.addFilter(new JwtAuthenticationFilter(authenticationManagerBuilder));
//		http.addFilter(new JwtAuthenticationFilter(authenticationManager));
//		http.addFilter(new JwtAuthorizationFilter(authenticationManager));
		http.apply(new customJwtFilter());
		
		// 인증없이 허용 URL 설정
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/api/v1/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
				.requestMatchers("/api/v1/manager/**").hasAnyRole("MANAGER", "ADMIN")
				.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
				);
		
		// cross origin 사용유무 설정
		http.cors(cors -> cors.configurationSource(corsConfigurationSource));
		
		return http.build();
	}
	
	public class customJwtFilter extends AbstractHttpConfigurer<customJwtFilter, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			http.addFilter(new JwtAuthenticationFilter(authenticationManager));
			http.addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
		}
	}
}
