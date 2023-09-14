package hello.securityjwt.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.securityjwt.auth.PrincipalDetails;
import hello.securityjwt.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// login 요청시 적용됨
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
//
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse respnse)
			throws AuthenticationException {
		log.debug("JwtAuthenticationFilter 로그인중");
		try {
//			BufferedReader br = request.getReader();
//			String input = null;
//			String buffers="";
//			while((input = br.readLine())!=null) {
//				buffers = buffers + input;
//			}
			ObjectMapper objectMapper = new ObjectMapper();
			User user = objectMapper.readValue(request.getInputStream(), User.class);
			log.debug("user={}", user.toString());
			
			UsernamePasswordAuthenticationToken authenticationToken	
				= new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			
			// PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			log.debug("authentication={}", authentication);
				
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			log.debug("principalDetails={}", principalDetails.getUser());
			
//		 	authentication 객체가 session 영역에 저장
			return authentication;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// attemptAuthentication 인증이 완료된 후 실행 
	// JWT 토큰 생성후 토큰을 response 함
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.debug("정상적으로 인증이 완료됨");
		super.successfulAuthentication(request, response, chain, authResult);
	}
}
