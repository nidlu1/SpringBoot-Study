package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
//그냥 주소가 /이면 index.jsp로 허용
// static 이하 js/**, css/**, image/** 허용

@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		System.out.println(">>>>>UserController.joinForm()");
		System.out.println("<<<<<UserController.joinForm()");
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		System.out.println(">>>>>UserController.loginForm()");
		System.out.println("<<<<<UserController.loginForm()");
		return "user/loginForm";
	}
}
