package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cos.blog.model.User;

// DAO
// 자동으로 bean 등록 됨
//@Repository //생략가능
public interface UserRepository extends JpaRepository<User, Integer>{ //user table 관리하는 레포지토리고 primary key는 int임

	/*
	//JPA Naming 쿼리 전략
	// SELECT * FROM user WHERE username = ?1 AND password = ?2
	User findByUsernameAndPassword(String username, String password);
	
	//Native 쿼리전략
	@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
	User login(String username, String password); */
	
	//SELECT * FROM user WHERE username = ?1
	Optional<User> findByUsername(String username);
	
}
