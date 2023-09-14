package hello.securityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.securityjwt.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);

}
