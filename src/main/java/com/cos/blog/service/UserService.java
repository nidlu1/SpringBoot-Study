package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Transactional
	public void 회원수정(User user) {
		System.out.println(">>>>>UserService.회원수정()");
		//수정시에는 영속성 컨텍스트 User 오브젝트르 영속화시키고, 영속화된 User 오브젝트를 수정
		//Select를 해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!
		//영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패: 아이디를 찾을 수 없습니다.");
		});
		
		// Validate 체크 => oauth에 값이 없으면 수정가능
		if(persistance.getOauth() == null || persistance.getOauth().equals("")){
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());			
		}
		
		//회원수정 함수 종료시 = 서비스 종료시 = 트랜잭션 종료 = commit이 자동으로 됨.
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 변화된 내용을 update문을 날린다.
		//DB는 변경이 되었지만 세션값은 변경되지 않은 상태이다. 
		System.out.println("<<<<<UserService.회원수정()");
	}

	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
				return new User();
		});
		return user;
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