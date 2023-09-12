package hello.securityjwt.conrtoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestApiController {
	
	@GetMapping("/home")
	public String home() {
		return "<h1>home</h1>";
	}
	@PostMapping("/token")
	public String token() {
		return "<h1>token</h1>";
	}
}
