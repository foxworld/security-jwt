package hello.securityjwt.config;

public interface JwtProperties {
	String SECRET = "foxworld"; // 우리 서버만 알고 있는 비밀값
	int EXPIRATION_TIME = 60000*600;
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}
