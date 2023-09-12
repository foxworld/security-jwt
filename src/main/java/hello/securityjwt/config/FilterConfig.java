package hello.securityjwt.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.securityjwt.filter.JwtFilter1;
import hello.securityjwt.filter.JwtFilter2;

@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean<JwtFilter1> fileter1() {
		FilterRegistrationBean<JwtFilter1> filter = new FilterRegistrationBean<>(new JwtFilter1());
		filter.addUrlPatterns("/*"); 
		filter.setOrder(0); // 숫자가 낮을수록 먼저 실행
		
		return filter;
	}
	
	@Bean
	public FilterRegistrationBean<JwtFilter2> fileter2() {
		FilterRegistrationBean<JwtFilter2> filter = new FilterRegistrationBean<>(new JwtFilter2());
		filter.addUrlPatterns("/*"); 
		filter.setOrder(0);
		
		return filter;
	}
	

}
