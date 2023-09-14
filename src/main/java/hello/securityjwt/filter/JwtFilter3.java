package hello.securityjwt.filter;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter3 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug("필터3");
/*
 * 토큰 만들어줌 : id, pw 들어와서 검증후 토큰을 만들어 응답에 전송
 * 요청할때 header 에 authorization  키에 값을 넎어줌
 * 요청시 토큰값이 맞는지 검증(rsa, hs256)
 */
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (req.getMethod().equals("POST")) {
			String headerAuth = req.getHeader("Authorization");
			log.debug("Authorization={}", headerAuth);

			if (headerAuth.equals("foxworld")) {
				chain.doFilter(req, res);
			} else {
				PrintWriter out = res.getWriter();
				out.println("인증안됨");
						
			}
		}

	}

}
