package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


@Service //빈등록 
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	/*
	 * 스프링이 로그인요청을 가로챌때, username, password 변수 2개를 가로채는데
	 * password 부분처리는 알아서 처리하니, 해당 username이 DB에 있는지 확인해서 리턴해야함. 
	 */
	@Override //오버라이딩해야 PrincipalDetail에 User정보를 담을 수 있다.
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(">>>>>PrincipalDetailService.loadUserByUsername()");
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당사용자를 찾을 수 없습니다.: "+username);
				});
		System.out.println("<<<<<PrincipalDetailService.loadUserByUsername()");
		return new PrincipalDetail(principal); // 시큐리티의 세션에 유저정보가 저장이 됨.  안할 경우 세션에는 아이디: user, 패스워드: 콘솔창비번
	}
	
}
