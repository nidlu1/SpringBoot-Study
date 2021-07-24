let board = {
	init:function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		$("#btn-update").on("click", ()=>{
			this.update();
		});
		$("#btn-delete").on("click", ()=>{
			this.deleteById();
		});
	},	
	save: function(){
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};
		$.ajax({
			type: "POST",
			url:  "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json;charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			alert("글쓰기가 완료되었다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	update: function(){
		let id = $("#id").val();

		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};
		$.ajax({
			type: "PUT",
			url:  `/api/board/${id}`,
			data: JSON.stringify(data),
			contentType: "application/json;charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			alert("글수정이 완료되었다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	deleteById: function(){
		let id = $("#id").text();
		alert(id);
		$.ajax({
			type: "DELETE",
			url:  "/api/board/"+id,
		}).done(function(resp){
			console.dir(resp);
			alert(`${resp}: 삭제가 완료되었다.`);
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	}
}
board.init();