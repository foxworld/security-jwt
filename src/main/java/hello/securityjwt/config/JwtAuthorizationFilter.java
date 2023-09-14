package hello.securityjwt.config;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/*
 * 권한(인증은 됐으나 해당주소가 권한이 존재하는지 체크) 또는 인증이 필요한 주소면 해당 클래스가 실행된다
 * 만약에 권한 또는 인증이 필요없으면 실행되지 않는다
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		
	}
	
	@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			super.doFilterInternal(request, response, chain);
			log.debug("인증 또는 권한이 필요한 주소 요청이 됨");
			
			String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
			log.debug("jwtHeader={}",jwtHeader);
		}

}
