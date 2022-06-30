<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 삭제 페이지</title>
</head>
<body>
	<div>
		<h2>글 삭제</h2>
	</div>
	
	<form action="deleteBoard.do?num=${result.board.num}&page=${result.page}" method="post" name="deleteform">
		<table>
			<tr>
				<th>게시글 번호</th>
				<td><c:out value="${result.board.num}"></c:out></td>
			</tr>
			<c:set var="title" value="${result.board.title}"></c:set>
			<c:choose>
				<c:when test="${fn:contains(title, 'script')}">
					<th>제목</th>
					<td><c:out escapeXml="false" value="${result.board.title}"></c:out></td>
				</c:when>
				<c:otherwise>
					<th>제목</th>
					<td><c:out escapeXml="true" value="${result.board.title}"></c:out></td>
				</c:otherwise>
			</c:choose>
			<tr>
				<th>작성자</th>
				<td><c:out value="${result.board.writer}"></c:out></td>
			</tr>
			<tr>
				<th><label for="password">비밀번호</label></th>
				<td><input type="password" name="password">${result.board.password}</td>
			</tr>
			
			<tr>
				<td>
					<!-- <a href="javascript:deleteform.submit()">[삭제]</a> -->
					<a href="javascript:delBoard()">[삭제]</a>
					<a href="javascript:history.go(-1)">[뒤로]</a>
					<a href="boardList.do">[목록]</a>
				</td>
			</tr>
		</table>
	</form>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	function delBoard() {
		
		var form = document.deleteform;
		
		var blank_pattern = /[\s]/g;
		
		/** 비밀번호 유효성 검사 **/
		var password_v = form.password.value;
		
		//입력을 안한 경우
		if(password_v == '' || password_v == null) {
			alert("비밀번호를 입력해주세요.");
			form.password.select();
			return;
		}
		
		//공백만 입력 된 경우
		if(password_v.replace(blank_pattern, '') == "") {
			alert("공백만 입력되었습니다.");
			form.password.select();
			return;
		}
		
		//문자열에 공백이 입력 된 경우
		if(blank_pattern.test(password_v)) { //test() ㅡ 찾는 문자열이, 들어있는지 아닌지를 알려준다.
			alert("공백이 입력되었습니다.");
			form.password.select();
			return;
		}
		
		var chk = confirm("정말로 삭제하시겠습니까?");
		
		if(chk) {
			form.submit();
		}
	} 
</script>
</html>