package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
//그냥 주소가 /이면 index.jsp로 허용
// static 이하 js/**, css/**, image/** 허용

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		System.out.println(">>>>>UserController.joinForm()");
		System.out.println("<<<<<UserController.joinForm()");
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		System.out.println(">>>>>UserController.loginForm()");
		System.out.println("<<<<<UserController.loginForm()");
		return "user/loginForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {// data를 리턴하는 컨트롤러 함수
		System.out.println(">>>>>UserController.kakaoCallback()");
		System.out.println("인증코드:"+code);
		// POST방식으로 key=value 데이터 요청(카카오쪽으로)
		//Retrofit2
		//OkHttp
		//RestTemplate
		
		//1. 토큰 받아오기
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type","authorization_code");
		params.add("client_id","ae79a8b1d9fffefcca85d96ad612315f");
		params.add("redirect_uri","http://localhost:8000/auth/kakao/callback" );
		params.add("code",code);
		
		//HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params,headers);
		
		//Http 요청하기 - POST방식 - 그리고 response 변수의 응답받음
		ResponseEntity<String> response = rt.exchange(
					"https://kauth.kakao.com/oauth/token",
					HttpMethod.POST,
					kakaoTokenRequest,
					String.class
				);
		
		// Gson, JsonSimple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;
		try {
			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		System.out.println("카카오 엑세스 토큰:"+oAuthToken.getAccess_token());
		
		//2. 사용자 정보 요청
		RestTemplate rt2 = new RestTemplate();
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				
		//HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		//Http 요청하기 - POST방식 - 그리고 response 변수의 응답받음
		ResponseEntity<String> response2 = rt2.exchange(
					"https://kapi.kakao.com/v2/user/me",
					HttpMethod.POST,
					kakaoProfileRequest2 ,
					String.class
				);
			
		System.out.println("카카오 프로파일:"+response2.getBody());
		
		// Gson, JsonSimple, ObjectMapper
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile= null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// User 오브젝트 : username, password, email
		System.out.println("카카오 id(번호):"+kakaoProfile.getId());
		System.out.println("카카오 이메일:"+kakaoProfile.getKakao_account().getEmail());
		System.out.println("블로그 유저네임:"+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그 이메일:"+kakaoProfile.getKakao_account().getEmail());
		//UUID -> 중보되지 않는 어떤 특정값을 만들어내는 알고리즘
		UUID garbagePassword = UUID.randomUUID();
		System.out.println("블로그 패스워드:"+cosKey);
		
		User kakaoUser = User.builder().
				username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId()).
				password(cosKey).
				email(kakaoProfile.getKakao_account().getEmail()).
				oauth("kakao").
				build();
		// 가입자 혹은 비가입자 체크해서 처리.
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
			System.out.println("기존회원이 아닙니다....!!");
			userService.회원가입(kakaoUser);		
		}
		
		// 로그인처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("자동로그인을 진행합니다.");
		
		System.out.println("<<<<<UserController.kakaoCallback()");
		return "redirect:/";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		System.out.println(">>>>>UserController.updateForm()");
		System.out.println("<<<<<UserController.updateForm()");
		return "user/updateForm";
	}
}
