<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세 페이지</title>
</head>
<body>
	<div>
		<h1>글 상세</h1>
	</div>

	<table>
		<tr>
			<th>게시글 번호</th>
			<td><c:out value="${result.board.num}"></c:out></td>
		</tr>
		<tr>
			<th>제목</th>
			<td><c:out escapeXml="false" value="${result.board.title}"></c:out></td>
		</tr>
		<tr>
			<th>조회수</th>
			<td><c:out value="${result.board.hit}"></c:out></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><c:out value="${result.board.writer}"></c:out></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><c:out value="${result.board.password}"></c:out></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><c:out escapeXml="false" value="${result.board.content}"></c:out></td>
		</tr>
		<tr>
			<th>등록일</th>
			<td><fmt:formatDate value="${result.board.write_date}" type="both" pattern="yyyy년 MM월dd일 HH:mm:ss"/></td>
		</tr>
		<c:if test="${result.board.write_date ne result.board.update_date}">
			<tr>
				<th>수정일</th>
				<td><fmt:formatDate value="${result.board.update_date}" type="both" pattern="yyyy년 MM월dd일 HH:mm:ss"/></td>
			</tr>
		</c:if>
		<tr>
			<th>등록 아이피</th>
			<td><c:out value="${result.board.write_ip}"></c:out></td>
		</tr>
		<c:if test="${result.board.update_ip ne null}">
			<tr>
				<th>수정 아이피</th>
				<td><c:out value="${result.board.update_ip}"></c:out></td>
			</tr>
		</c:if>
		<c:if test="${result.board.file ne null}">
			<tr>
				<th>첨부파일</th>
				<td><img alt="첨부파일" src="/upload2/${result.board.file}"></td>
			</tr>
		</c:if>
		<tr>
			<td>
				<a href="modifyView.do?num=${result.board.num}">[수정]</a>
				<a href="deleteView.do?num=${result.board.num}">[삭제]</a>
				<a href="javascript:history.go(-1)">[뒤로]</a>
				<a href="boardList.do">[목록]</a>
			</td>
		</tr>
	</table>
</body>
</html>