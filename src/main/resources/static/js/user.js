// console.log(">>user.js");
let index = {
	// let _this = this; //function(){}사용시 this바인딩 위해
	init:function(){
		$("#btn-save").on("click", ()=>{ //function(){} 대신 ()=>{}를 사용한 이유: this를 바인딩하기 위해서
		// $("#btn-save").on("click",function(){
			this.save(); // ()=>{}사용시, this = index /function(){}사용시 this = window
		});
		/*
		$("#btn-login").on("click", ()=>{
			this.login();
		});*/
	},
	
	save: function(){
		// alert("user의 세이브함수 호출");
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		// console.log(data);
		// ajax 호출시 default가 비동기호출
		// ajax 통신을 통해서 3개의 파라미터를 json으로 변경하여 insert 요청!!
		// ajax가 통신을 성공하고나서 json을 리턴하면 서버가 자동으로 자바 오브젝트로 변환해줌.
		$.ajax({
			type: "POST",
			url:  "/auth/joinProc",
			data: JSON.stringify(data), //http body데이터
			contentType: "application/json;charset=utf-8", //body데이터가 어떤 타입인지 (MIME)
			dataType: "json" //요청을 서버로 보내서 응답이 왔을 때, 기본적으로 모든 것이 문자열(stream)이지만 (문자열 구조가 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){
			alert("회원가입이 완료되었다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	/*
	login: function(){
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
		};
		$.ajax({
			type: "POST",
			url:  "/api/user/login",
			data: JSON.stringify(data),
			contentType: "application/json;charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			alert("로그인이 완료되었다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}*/
}
index.init();