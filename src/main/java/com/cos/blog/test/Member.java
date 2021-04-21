//package com.cos.blog.test;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//
////@Getter
////@Setter
//@Data
//@NoArgsConstructor
////@RequiredArgsConstructor //final이 붙은 변수의 생성자
//public class Member {
//	private int id;
//	private String username;
//	private String password; //변경가능성 있음
//	private String email;
//	
//	@Builder //빌더 어노테이션으로 컨트롤러에서 빌더패턴 사용 가능. 장점: 값 넣을때 순서지킬 필요 없음. 생성자로 필드값 넣을시 코드작성시 헷갈림.
//	public Member(int id, String username, String password, String email) {
//		this.id = id;
//		this.username = username;
//		this.password = password;
//		this.email = email;
//	}
//	
//	
//}
