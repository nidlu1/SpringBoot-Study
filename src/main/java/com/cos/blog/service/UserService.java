package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 서비스 사용이유: 1. 트랜잭션 관리  2.서비스 의미(CRUD를 직접함)
//스프링이 컴포넌트 스캔을 통해 bean에 등록을 해줌. IoC를 해줌.
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
		System.out.println(">>>>>UserService.회원가입()");
		String rawPassword = user.getPassword(); //원문 비번
		String encPassword = encoder.encode(rawPassword); //해시된 비번
		user.setPassword(encPassword);
	//실제로 DB에 insert를 하고 아래에서 return이 되면 완성,
		user.setRole(RoleType.USER);
		userRepository.save(user);
		System.out.println("<<<<<UserService.회원가입()");
	}
	
	/*
	@Transactional(readOnly = false) //Select 할때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료(정합성)
	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		return userRepository.login(user.getUsername(), user.getPassword());
	}*/
}

/*
 * DB 격리수준
 * 
 */