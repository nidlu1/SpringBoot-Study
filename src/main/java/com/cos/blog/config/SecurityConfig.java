package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

@EnableGlobalMethodSecurity(prePostEnabled = true) //특정주소로 접근을 하면 권한 및 인증을 미리 체크하겠다
@EnableWebSecurity // 시큐리티 필터 추가 = 스프링시큐리티가 활성화가 되어 있는데 어떤 설정을 이 해당파일에서 하겠다.
@Configuration //빈 등록: 스프링 컨테이너(ioc)에서 객체를 관리할 수 있게 하는 것
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService principalDetailService;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean //리턴값을 스프링이 IoC.
	public BCryptPasswordEncoder encodePWD() {
		System.out.println(">>>>>SecurityConfig.encodePWD()");
		System.out.println("<<<<<SecurityConfig.encodePWD()");
		return new BCryptPasswordEncoder();
	}
	
	/*
	 *  시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	 *  해당 password가 뭐로 해시가 되어 회원가입이 되었는지 알아야
	 *  같은 해쉬로 암호화해서 DB에 있는 해시랑 비교할 수 있음.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println(">>>>>SecurityConfig.configure(AuthenticationManagerBuilder)");
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD()); //principalDetailService 을 안넣으면 패스워드비교를 못함.
		System.out.println("<<<<<SecurityConfig.configure(AuthenticationManagerBuilder)");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println(">>>>>SecurityConfig.configure(HttpSecurity)");
		http
			.csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는게 좋음 스프링시큐리티가 기본적으로 작동함.)
			.authorizeRequests()
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**","/dummy/**") //해당 url은
				.permitAll() //모든접근허용
				.anyRequest() //다른 url은
				.authenticated() //인증받아야함
			.and()
				.formLogin()
				.loginPage("/auth/loginForm") //인증이 필요한 페이지로 이동시 해당 url로 이동함.
				.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채 대신 로그인 해준다.
				.defaultSuccessUrl("/") //로그인 성공시 이동하는 url
//				.failureUrl("fail") //로그인 실패시 이동하는 url.  userdetails타입을 가지고 있는 오브젝트타입을 생성해야함.
				;
		System.out.println("<<<<<SecurityConfig.configure(HttpSecurity)");
	}
}


/* Xss -> 자바스크립트 공격  naver->lusy 필터로 방어
 *  CSRF 공격 : referrer 검증(같은 도메인상에서 요청이 없으면 차단)
 */
