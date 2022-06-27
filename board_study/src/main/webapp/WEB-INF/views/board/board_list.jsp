<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 목록 페이지</title>

<style type="text/css">
	#mouse {cursor: pointer;}
	#mouse:hover {color:pink;}
</style>

</head>

<body>
	<div>
		<h1>글 목록</h1>
	</div>
	<table>
		<thead>
			<tr>
				<th>글 개수</th>
				<td><c:out value="${result.count}"></c:out></td>
			</tr>
			<tr>
				<th>행번호</th>
				<th>게시물 번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>등록일</th>
				<th>수정일</th>
				<th>등록 아이피</th>
				<th>수정 아이피</th>
			</tr>
		</thead>
		

		<tbody>	
			<c:choose>
				<c:when test="${fn:length(result.boardList) eq 0}">
					<tr>
						<td>게시글이 없습니다.</td>
					</tr>
				</c:when>

				<c:otherwise>
					<c:forEach var="board" items="${result.boardList}">
						<tr id="mouse" onClick="location.href='getBoard.do?num=${board.num}'">
							<td><c:out value="${board.rownum}"></c:out></td>
							<td><c:out value="${board.num}"></c:out></td>
							<td><c:out escapeXml="false" value="${board.title}"></c:out></td>
							<td><c:out value="${board.writer}"></c:out></td>
							<td><c:out value="${board.hit}"></c:out></td>
							<td><c:out value="${board.write_date}"></c:out></td>
							<c:if test="${board.write_date ne board.update_date}">
								<td><c:out value="${board.update_date}"></c:out></td>
							</c:if>
							<td><c:out value="${board.write_ip}"></c:out></td>
							<td><c:out value="${board.update_ip}"></c:out></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			
			<tr>
				<td>
					<a href="insertView.do">글쓰기</a>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>