package com.cos.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	
	@GetMapping({"","/"})
	public String index(@AuthenticationPrincipal PrincipalDetail principal) { //스프링시큐리티 로그인시 컨트롤러에서 세션을 어떻게 찾을까?
		System.out.println(">>>>>BoardController.index(PrincipalDetail)");
		System.out.println("로그인 사용자 아이디:"+principal.getUsername());
		System.out.println("<<<<<BoardController.index(PrincipalDetail)");
		return "index";
	}
}
