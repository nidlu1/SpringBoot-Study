package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {

	@Autowired //의존성 주입 (DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패했습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제되었습니다. id:"+id;
	}
	
	// email, password
	@Transactional // 함수종료시 자동 commit이 됨. 더티체킹을 통해 save함수 없이도 update가 됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { //json데이터를 요청 -> java object로 변환해서 받음(messageConverter의 jackson라이브러리)
		System.out.println("id:"+id);
		System.out.println("password:"+requestUser.getPassword());
		System.out.println("email:"+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패함");			
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//save함수는 id를 전달하지 않으면 insert하고, id를 전달시 해당 id에 대한 data가 있음 update를 해주고 없으면 insert함.
//		userRepository.save(user);
		
		//더티체킹
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	//한 페이지당 2건의 데이터를 리턴
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/user")	
	public Page<User> pageList(@PageableDefault(size = 2, sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		return pagingUser;
	}
	
	//{id} 주소로 파라미터를 전달받음
	//http://localhost:8000/blog/dummy/user/{id}
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user/4을 찾으면 내가 데이터베이스에서 못찾아오면 user가 null이 됨
		//return null이 되면 프로그램에 문제가 생김.
		//Optional로 너의 user객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해.
//		User user = userRepository.findById(id).get(); //null이 없을거라는 확실한 판단이 있을때 사용. 오류발생시 메시지가 없음. no value present
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() { // 잘못된 인수로 데이터 값이 없으면 빈 객체를 넣어줌. null은 아님.
//			@Override
//			public User get() {
//				return new User();
//			
//		});
//		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//			@Override
//			public IllegalArgumentException get() {
//				return new IllegalArgumentException("해당 유저는 없습니다. id:"+id);
//			}
//		});
		
		//		람다식
		User user = userRepository.findById(id).orElseThrow(()->{
				return new IllegalArgumentException("해당 유저는 없습니다. id:"+id);			
		});
		
//		요청: 웹브라우저 .자바 오브젝트를 이해 못함.
//		user 객체는 = 자바 오브젝트
//		변환 (웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
//		스프링부트 = messageConverter라는 애가 응답시에 자동 작동
//		만약에 자바 오브젝트를 리턴하게 되면 messageConverter가 Jacson 라이브러리를 호출해서
//		user 오브젝트를 json으로 변환해서 브라우저에 던져줌
		return user;
	}
	
	//http://localhost:8000/blog/dummy/join(요청)
	//http의 body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) { //key=value (약속된 규칙)
//	public String join( @RequestParam String username, String password, String email) { //key=value (약속된 규칙)
//		System.out.println("username: "+username);
//		System.out.println("password: "+password);
//		System.out.println("email: "+email);
		System.out.println("id:"+user.getId());
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		System.out.println("email: "+user.getEmail());
		System.out.println("role:"+user.getRole());
		System.out.println("createdate:"+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);

		return "회원가입이 완료되었습니다.";
	}
}
