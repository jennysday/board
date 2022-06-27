<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정 페이지</title>
</head>
<body>
	<div>
		<h1>글 수정</h1>
	</div>
	
	<form action="modifyBoard.do" method="post" name="modifyform" enctype="multipart/form-data">
		<table>
			<tr>
				<th>게시글 번호</th>
				<td>${result.board.num}</td>
				<td><input type="hidden" name="num" value="${result.board.num}"></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" value="${result.board.title}" name="title"></td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${result.board.hit}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${result.board.writer}</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>${result.board.password}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><input type="text" value="${result.board.content}" name="content"></td>
			</tr>
			<tr>
				<th>등록일</th>
				<td>${result.board.write_date}</td>
			</tr>
			<tr>
				<th>수정일</th>
				<td>${result.board.update_date}</td>
			</tr>
			<tr>
				<th>등록 아이피</th>
				<td>${result.board.write_ip}</td>
			</tr>
			<tr>
				<th>수정 아이피</th>
				<td>${result.board.update_ip}</td>
			</tr>
			<tr>
				<th> <label for="uploadFile"></label>첨부파일</th>
				<td><input type="file" id="uploadFile" name="uploadFile" accept="image/*" value="${result.board.file}">${board.file}</td>
			</tr>
			
			<tr>
				<td>
					<a href="javascript:modiBoard()">[수정]</a>
					<a href="javascript:history.go(-1)">[뒤로]</a>
					<a href="boardList.do">[목록]</a>
				</td>
			</tr>
		</table>
	</form>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	
	function modiBoard() {
		var form = document.modifyform;
		
		/** 첨부 파일 유효성 검사 **/
		var file = $("#uploadFile").val(); 
		var fileForm = /(.*?)\.(jpg|jpeg|png|gif|bmp|pdf)$/;	
		var maxSize = 5 * 1024 * 1024;
		var fileSize;
		
		//파일 유무 확인
		if(file != "" && file != null) {
		
			//파일 사이즈 확인
			fileSize = document.getElementById("uploadFile").files[0].size;
			
			if(!file.match(fileForm)) { //파일 형식 유효성 검사
				alert("이미지 파일만 업로드 가능합니다.");
				$("#uploadFile").val("");
				return;
			} else if(fileSize > maxSize) { //파일 용량 유효성 검사
				alert("파일 사이즈는 5MB까지 가능합니다.");
				$("#uploadFile").val("");
				return;
			}
		}

		form.submit();
	}
	
</script>

</html>