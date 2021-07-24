package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDTO;
import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;

	@PostMapping("/api/board")
	public ResponseDTO<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println(">>>>BoardApiController.save()");
		boardService.글쓰기(board, principal.getUser());
		System.out.println("<<<<<BoardApiController.save()");
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDTO<Integer> deleteById(@PathVariable int id){
		System.out.println(">>>>BoardApiController.deleteById()");
		boardService.삭제하기(id);
		System.out.println("<<<<BoardApiController.deleteById()");
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	};
	
	@PutMapping("/api/board/{id}")
	public ResponseDTO<Integer> update(@PathVariable int id, @RequestBody Board board){
		System.out.println(">>>>BoardApiController.update()");
		boardService.수정하기(id, board);
		System.out.println("<<<<BoardApiController.update()");
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
}
