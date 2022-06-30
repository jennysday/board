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
				<th>행 번호</th>
				<th>글 번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>첨부파일</th>
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
						<tr id="mouse" onClick="location.href='getBoard.do?num=${board.num}&page=${result.page}'">
							<td><c:out value="${board.rownum}"></c:out></td>
							<td><c:out value="${board.num}"></c:out></td>	
							<c:set var = "title" value = "${board.title}"/>
							<c:choose>
								<c:when test="${fn:contains(title,'script')}">
									<td><c:out escapeXml="false" value="${board.title}"></c:out></td>
								</c:when>
								<c:otherwise>
									<td><c:out escapeXml="true" value="${board.title}"></c:out></td>
								</c:otherwise>
							</c:choose>
							<td><c:out value="${board.writer}"></c:out></td>
							<td><c:out value="${board.hit}"></c:out></td>
							<c:choose>
								<c:when test="${board.file ne null}">
									<td><c:out value="o"></c:out></td>
								</c:when>
								<c:otherwise>
									<td><c:out value="x"></c:out></td>
								</c:otherwise>
							</c:choose>
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
					<c:if test="${result.page <= 0}">
						<c:set target="${result.page}" property="page" value="1"></c:set>
					</c:if>
					
					<c:if test="${result.page >= result.startPage}">
						<a href="boardList.do?page=${result.page - 1}">[이전]</a>
					</c:if>
					
					<c:if test="${result.page <= result.endPage}">
						<c:forEach var="pageNum" begin="${result.startPage}" end="${result.endPage}"> 
							<c:choose>
								<c:when test="${pageNum eq result.page}">
									<span>
										<a style="font-weight: bold; color: #1d84df !important;" href="boardList.do?page=${pageNum}">${pageNum}</a>
									</span>
								</c:when>
								<c:otherwise>
									<a href="boardList.do?page=${pageNum}">${pageNum}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<a href="boardList.do?page=${result.page + 1}">[다음]</a>
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td>
					<a href="insertView.do?page=${result.page}">글쓰기</a>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>