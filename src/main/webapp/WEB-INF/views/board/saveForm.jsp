<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
		    <label for="title">Title</label>
		    <input type="text" class="form-control" placeholder="Enter title" id="title">
		</div>
    <div class="form-group">
        <label for="content">Content</label>
        <textarea class="form-control summernote" rows="5" id="content"></textarea>
    </div>
  </form>	
  <button class="btn btn-primary" id="btn-save" >글쓰기 완료</button>
</div>
    

<%@ include file="../layout/footer.jsp"%>
<%@ include file="../layout/script.jsp"%>
<!-- summernote는 bootstrap 4.4.1 버전에서만 작동해서 추가함 -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<script src="/js/board.js"></script>
<script>
  $('.summernote').summernote({
    tabsize: 2,
    height: 300
  });
</script>