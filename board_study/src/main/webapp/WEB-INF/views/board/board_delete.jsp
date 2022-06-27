<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	<form action="deleteBoard.do?num=${result.board.num}" method="post" name="deleteform">
		<table>
			<tr>
				<th>게시글 번호</th>
				<td>${result.board.num}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${result.board.title}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${result.board.writer}</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="password">${result.board.password}</td>
			</tr>
			
			<tr>
				<td>
					<a href="javascript:deleteform.submit()">[삭제]</a>
					<a href="javascript:history.go(-1)">[뒤로]</a>
					<a href="boardList.do">[목록]</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>