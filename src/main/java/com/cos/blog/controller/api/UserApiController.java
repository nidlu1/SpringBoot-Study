package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDTO;
//import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/auth/joinProc")
	public ResponseDTO<Integer> save(@RequestBody User user) { //username, password, email
		System.out.println(">>>>UserApiController.save()");
		userService.회원가입(user);
		System.out.println("<<<<<UserApiController.save()");
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); //자바오브젝트를 json으로 변환해서 리턴 (jackson라이브러리)
	}
	
	
	@PutMapping("/user")
	public ResponseDTO<Integer> update(@RequestBody User user){ // @RequestBody없으면  key=value, xxx-form-urlencoded 타입으로 받음
		System.out.println(">>>>UserApiController.update()");
		userService.회원수정(user);
		//여기서 트랜잭션이 종료되기 때문에 DB값은 변경이 되었음.
		//하지만 세셔낪은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경할 예정
		
		//세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
	
	//전통적인 로그인방식
	/*
	@PostMapping("/api/user/login")
	public ResponseDTO<Integer> login(@RequestBody User user, HttpSession session){ //오토와이어드로 필드에 넣을수도 있음
		System.out.println(">>>>>UserApiController.login()");
		User principal = userService.로그인(user); //principal: 접근주체
		if(principal != null) {
			session.setAttribute("principal", principal);
		}
		System.out.println("<<<<UserApiController.login()");
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	*/
	
	
}
