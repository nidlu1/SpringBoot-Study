//package com.cos.blog.test;
//
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//// 사용자가 요청 → 응답(HTML)
//// @Controller
//
//// 사용자가 요청 → 응답(Data)
//@RestController
//public class HttpContollerTest {
//	private static final String TAG = "HttpContorllerTest :";
//	
////localhost:8000/blog/http/lombok
//	@GetMapping("/http/lombok")
//	public String lombokTest() {
////		Member m = new Member (1, "ssar", "1234", "email");
//		Member m = Memlocalhost:8000/blog/http/lombokber.builder().username("ssar").password("1234").email("ssar@naver.com").build();
//		System.out.println(TAG+"getter :"+m.getUsername());
//		m.setUsername("cos");
//		System.out.println(TAG+"setter :"+m.getUsername());
//		return "lombok test 완료";
//	}
//	
//	//인터넷 브라우저 요청은 get밖에 못한다.
//	//http://localhost:8080/http/get (select)
//	@GetMapping("/http/get")
//	public String getTest(Member m) { //?id=1&username=ssar&password=1234&email=ssar@nate.com //MessageConverter: m을 자동으로 파싱해줌
//		return "get 요청 :"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
//	}
//	//http://localhost:8080/http/post (insert)
//	@PostMapping("/http/post") // text/plain, application/json
//	public String postTest(@RequestBody Member m) { // MessageConverter: m을 자동으로 파싱해줌
//		return "post 요청 :"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
//	}
//	//http://localhost:8080/http/put (update)
//	@PutMapping("/http/put")
//	public String putTest(@RequestBody Member m) {
//		return "put 요청:"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
//	}
//	//http://localhost:8080/http/delete (delete)
//	@DeleteMapping("/http/delete")
//	public String deleteTest(@RequestBody Member m) {
//		return "delete 요청 :"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
//	}
//}
