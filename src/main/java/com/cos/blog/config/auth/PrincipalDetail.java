package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Getter;
/*
 * 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
 * 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다. (PrincipalDetail 클래스)
 */
@Getter //board 글쓰기의 글쓴이를 알기 위해 추가
public class PrincipalDetail implements UserDetails {

	private User user; //컴포지션: 객체를 품고있는 것
	
	//PrincipalDetailService를 위한 생성자
	public PrincipalDetail(User user) {
		this.user=user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정이 만료되지 않았는지 리턴함 (true: 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠겨있지 않았는지 리턴한다 (true: 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 만료되지 않았는지 리턴한다. (true: 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 활성화(사용가능)가 되었는지 리턴한다 (true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//계정의 권한
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
//		일반적인 사용
//		collectors.add(new GrantedAuthority() {			
//			@Override
//			public String getAuthority() {
//				return "ROLE_"+user.getRole(); //스프링이 인식하려면 권한을 넣을때 ROLE_를 삽입해야함. ROLE_USER
//			}
//		});
		
//		람다식 파라미터는 1개, 함수도 1개일때 사용
		collectors.add(()-> {return "ROLE_"+user.getRole();});
		return collectors;
	}
}
