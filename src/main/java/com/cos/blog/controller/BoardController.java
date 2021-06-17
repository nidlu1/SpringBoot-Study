package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;

//import com.cos.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	@GetMapping({"","/"})
	public String index(Model model /*@AuthenticationPrincipal PrincipalDetail principal*/, @PageableDefault(size = 3, sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {  //스프링시큐리티 로그인시 컨트롤러에서 세션을 어떻게 찾을까?
		System.out.println(">>>>>BoardController.index()");
//		System.out.println("로그인 사용자 아이디:"+principal.getUsername());
		model.addAttribute("boards",boardService.글목록(pageable));
		
		System.out.println("<<<<<BoardController.index()");
		return "index"; //viewResolver 작동!
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		System.out.println(">>>>>BoardController.findById()");
		model.addAttribute("board", boardService.글상세보기(id));
		System.out.println("<<<<<BoardController.findById()");
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		System.out.println(">>>>>BoardController.updateForm()");
		model.addAttribute("board", boardService.글상세보기(id));
		System.out.println("<<<<<BoardController.updateForm()");		
		return "board/updateForm";
	}
	
	
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		System.out.println(">>>>>BoardController.saveForm()");
		System.out.println("<<<<<BoardController.saveForm()");
		return "board/saveForm";
	}
}
