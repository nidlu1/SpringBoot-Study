package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴
@Entity //엔티티가 가장 밑에 있는게 조아
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob//대용량 데이터
	private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인됨.
	
	@ColumnDefault("0")
	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER) // many=board, user = one // EAGER  즉시. LAZY: 지연
	@JoinColumn(name="userid") 
	private User user; //DB는 오브젝트랑 저장할 수 없어서 FK를 사용, 자바는 오브젝트를 저장할 수 있지만 DB에 맞춤. but JPA등장.
	
//	@JoinColumn(name="replyid") //fk가 필요 없음: 리플이 여러개가 들어갈 수 있어 원자성을 해침
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //mappedby 연관관계의 주인이 아니다.(fk가 아님) db에 컬럼을 만들지 마시오. board는 reply클래스의 필드명. 
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
