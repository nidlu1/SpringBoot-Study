package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void 글쓰기(Board board, User user) { //title , content
		System.out.println(">>>>>BoardService.글쓰기()");
		board.setCount(0);
		board.setUser(user); //글쓴이 정보 넣기
		boardRepository.save(board);
		System.out.println("<<<<<BoardService.글쓰기()");
	}

	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
		System.out.println(">>>>>BoardService.글목록()");
		System.out.println("<<<<<BoardService.글목록()");
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		System.out.println(">>>>>BoardService.글상세보기()");
		System.out.println("<<<<<BoardService.글상세보기()");
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
				});
	}

	@Transactional
	public void 삭제하기(int id) {
		System.out.println(">>>>>BoardService.삭제하기()");
		boardRepository.deleteById(id);
		System.out.println("<<<<<BoardService.삭제하기()");
	}

	@Transactional
	public void 수정하기(int id, Board requestBoard) {
		System.out.println(">>>>>BoardService.수정하기()");
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료시(service가 종료 될 때) 트랜잭션이 종료. 이때 더티체킹 - 자동업데이트가 됨. db flush
		System.out.println("<<<<<BoardService.수정하기()");
	}
}
