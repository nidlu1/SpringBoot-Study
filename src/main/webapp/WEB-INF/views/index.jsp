<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>

<div class="container">
	<c:forEach var="board" items="${boards.content}">
		<div class="card m-2">
			<div class="card-body">
			  <h4 class="card-title">${board.title}</h4>
			  <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</c:forEach>

	<c:if test='${boards.first}'>
		<c:set var="disabledFirst" value="disabled"></c:set>
	</c:if>
	<c:if test='${boards.last}'>
		<c:set var="disabledLast" value="disabled"></c:set>
	</c:if>
	<ul class="pagination justify-content-center">
		<li class="page-item ${disabledFirst}"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
		<li class="page-item ${disabledLast}"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
	</ul>
</div>
<%@ include file="layout/footer.jsp"%>
<%@ include file="layout/script.jsp"%>
