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
				<td><c:out value="${result.board.num}"></c:out></td>
				<td><input type="hidden" name="num" value="${result.board.num}"></td>
				<td><input type="hidden" name="page" value="${result.page}"></td>
			</tr>
			<tr>
				<th><label for="title">제목</label></th>
				<td><input type="text" id="title" value="${result.board.title}" name="title"></td>
			</tr>
			<tr>
				<th>조회수</th>
				<td> <c:out value="${result.board.hit}"></c:out></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><c:out value="${result.board.writer}"></c:out></td>
			</tr>
			<tr>
				<th><label for="password">비밀번호</label></th>
				<td><input type="password" id="password" name="password">${result.board.password}</td>
			</tr>
			<tr>
				<th><label for="content">내용</label></th>
				<td> <textarea rows="5" cols="20" id="content" name="content">${result.board.content}</textarea></td>
			</tr>
			<tr>
				<th>등록일</th>
				<td><c:out value="${result.board.write_date}"></c:out></td>
			</tr>
			<tr>
				<th>수정일</th>
				<td><c:out value="${result.board.update_date}"></c:out></td>
			</tr>
			<tr>
				<th>등록 아이피</th>
				<td><c:out value="${result.board.write_ip}"></c:out></td>
			</tr>
			<tr>
				<th>수정 아이피</th>
				<td><c:out value="${result.board.update_ip}"></c:out></td>
			</tr>
			<tr>
				<th> <label for="uploadFile"></label>첨부파일</th>
				<td><input type="file" id="uploadFile" name="uploadFile" accept="image/*" value="${result.board.file}">${board.file}</td>
			</tr>
			
			<tr>
				<td>
					<a href="javascript:modiBoard()">[수정]</a>
					<a href="javascript:history.go(-1)">[뒤로]</a>
					<a href="boardList.do?page=${result.page}">[목록]</a>
				</td>
			</tr>
		</table>
	</form>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	
	function modiBoard() {
		var form = document.modifyform;
		
		var blank_pattern = /[\s]/g;
		var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
		
		
		/** 제목 유효성 검사 **/
		var title_v = form.title.value;
		
		console.log(title_v);
		
		//입력을 안한 경우
		if(title_v == '' || title_v == null) {
			alert("제목을 입력해주세요.");
			form.title.select();
			return;
		}
		
		//공백만 입력 된 경우
		if(title_v.replace(blank_pattern,'') == "") {
			alert("공백만 입력되었습니다.");
			form.title.select();
			return;
		}
		
		/** 비밀번호 유효성 검사 **/
		var password_v = form.password.value;
		
		//입력을 안한 경우
		if(password_v == '' || password_v == null) {
			alert("비밀번호를 입력해주세요.");
			form.password.select();
			return;
		}
		
		//공백만 입력된 경우
		if(password_v.replace(blank_pattern, '') == "") {
			alert("공백만 입력되었습니다.");
			form.password.select();
			return;
		}
		
		//문자열에 공백이 입력 된 경우
		if(blank_pattern.test(password_v)) {
			alert("공백이 입력되었습니다.");
			form.password.select();
			return;
		}
		
		/** 내용 유효성 검사 **/
		var content_v = form.content.value;
		
		//입력을 안한 경우
		if(content_v == '' || content_v == null) {
			alert("내용을 입력해주세요");
			form.content.select();
			return;
		}
		
		//공백만 입력 된 경우
		if(content_v.replace(blank_pattern, '') == "") {
			alert("공백만 입력되었습니다.");
			form.content.select();
			return;
		}
		
		
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
		
		var chk =  confirm ("수정하시겠습니까?")

		if(chk) {		
			form.submit();
		}
	}
	
</script>

</html>