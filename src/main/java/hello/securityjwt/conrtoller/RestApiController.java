package hello.securityjwt.conrtoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hello.securityjwt.model.User;
import hello.securityjwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestApiController {
	@Autowired private UserRepository repository;
	@Autowired private BCryptPasswordEncoder passwordEncoder; 
	
	@GetMapping("/home")
	public String home() {
		return "<h1>home</h1>";
	}
	@PostMapping("/token")
	public String token() {
		return "<h1>token</h1>";
	}
	
	@GetMapping("join")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("join")
	public String join(@RequestBody User user) {
		log.debug("user={}", user);
		user.setRoles("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		repository.save(user);
		return "회원가입완료";
	}
	
}
