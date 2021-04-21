package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴
//@DynamicInsert // insert시 컬럼값이 null인 필드는 제외함. @ColumnDefault와 쓰인다.
@Entity //User 클래스가 MySQL에 테이블이 생성이 된다.
public class User {

	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB(mysql, oracle...)의 넘버링 전략을 따라간다.
	private int id; //시퀀스, auto_increment
	
	@Column(nullable = false, length = 30, unique = true)
	private String username; //아이디

	@Column(nullable = false, length = 100) //해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable = false, length = 30)
	private String email;
	
//	@ColumnDefault("'user'")
//	private String role; //Enum을 쓰는게 좋다 = domain(범위를 정해짐)을 사용가능. admin, user, manager
	@Enumerated(EnumType.STRING) //DB는 Roletype이 없으니 String값이라고 정의해야함.
	private RoleType role;
	
	@CreationTimestamp //시간 자동입력
	private Timestamp createDate;
	
}
