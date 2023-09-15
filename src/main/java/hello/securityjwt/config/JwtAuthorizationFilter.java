package hello.securityjwt.config;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;

import hello.securityjwt.auth.PrincipalDetails;
import hello.securityjwt.model.User;
import hello.securityjwt.repository.UserRepository;
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
	private UserRepository userRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}
	
	@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
				throws IOException, ServletException {

			log.debug("인증 또는 권한이 필요한 주소 요청이 됨");
			
			String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
			
			
			// Header에 토근이 있는지 검증
			if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
				log.debug("jwtHeader=없음");
				chain.doFilter(request, response);
				return;
			}
			
			String jwtToken = jwtHeader.replace("Bearer ", "");
			log.debug("jwtToken={}",jwtToken);
			String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build()
					.verify(jwtToken).getClaim("username")
					.asString();
			
			if(username != null) {
				User userEntity = userRepository.findByUsername(username);
				PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
				
				// Jwt토큰 서명을 통해서 서명이 정상이면 Authentication 를 생성한다
				log.debug("auth={}", principalDetails.getAuthorities());
				Authentication authentication = 
						new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
				
				// 강제로 시큐리티의 세션에 접근하여 Authentication 객체에 저장
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				chain.doFilter(request, response);
			}
		}

}
