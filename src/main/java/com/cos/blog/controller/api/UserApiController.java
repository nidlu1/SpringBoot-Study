package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDTO;
//import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/auth/joinProc")
	public ResponseDTO<Integer> save(@RequestBody User user) { //username, password, email
		System.out.println(">>>>UserApiController.save()");
		userService.회원가입(user);
		System.out.println("<<<<<UserApiController.save()");
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); //자바오브젝트를 json으로 변환해서 리턴 (jackson라이브러리)
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
