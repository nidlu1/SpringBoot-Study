package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDTO;

@ControllerAdvice //exception 발생시 이 클래스 실행
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value=IllegalArgumentException.class)
	public String handleArgumentException(IllegalArgumentException e) {
		System.out.println(">>>>>GlobalExceptionHandler.handleArgumentException(IllegalArgumentException)");
		System.out.println("<<<<<GlobalExceptionHandler.handleArgumentException(IllegalArgumentException)");
		return "<h1>"+e.getMessage()+"</h1>";
	}
	
	@ExceptionHandler(value=Exception.class)
	public ResponseDTO<String> exception(Exception e) {
		System.out.println(">>>>>GlobalExceptionHandler.exception(Exception e)");
		System.out.println("<<<<<GlobalExceptionHandler.exception(Exception e)");
		return new ResponseDTO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}
