<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 등록 페이지</title>
</head>
<body>
	<div>
		<h1>글 등록</h1>
	</div>
	<form action="insertBoard.do" method="post" name="boardform" enctype="multipart/form-data">
		<table>
			<tr>
				<th><label for="writer">글쓴이</label></th>
				<td><input type="text" id="writer" name="writer" required="required" placeholder="글쓴이를 입력해주세요."></td>
			</tr>
			<tr>
				<th><label for="password">비밀번호</label></th>
				<td><input type="password" id="password" name="password" required="required" placeholder="비밀번호를 입력해주세요."></td>
			</tr>
			<tr>
				<th><label for="title">제목</label></th>
				<td><input type="text" id="title" name="title" required="required" placeholder="제목을 입력해주세요."></td>
			</tr>
			<tr>
				<th><label for="content">내용</label></th>
				<td><textarea rows="5" cols="20" id="content" name="content" required="required" placeholder="내용을 입력해주세요."></textarea></td>
			</tr>
			<tr>
				<th><label for="uploadFile">첨부파일</label></th>
				<td><input type="file" id="uploadFile" name="uploadFile" accept="image/*"></td>
			</tr>
			<tr>
				<td>
					<a href="javascript:addBoard()">[등록]</a>
					<a href="javascript:history.go(-1)">[뒤로]</a>
					<a href="boardList.do">[목록]</a>
				</td>
			</tr>
		</table>
	</form>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">

	function addBoard() {
		var form = document.boardform;
		
		var blank_pattern = /[\s]/g;
		var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
		
		/*** 이름 유효성 검사 ***/
		var writer_v = form.writer.value;
		
		//입력을 안할 경우
		if(writer_v == '' || writer_v == null) {
			alert("이름을 입력해주세요!");
			form.writer.select();
			return;
		}
		
		//공백만 입력 된 경우
		//var blank_pattern = /^\s+|\s+$/g;
		if(writer_v.replace(blank_pattern,'') == "") {
			alert("공백만 입력되었습니다.");
			form.writer.select();
			return;
		}
		
		//문자열에 공백이 있는 경우
		//var blank_pattern = /[\s]/g;
		if(blank_pattern.test(writer_v) == true) {
			alert("공백이 입력되었습니다.");
			form.writer.select();
			return;
		}
		
		//특수문자가 있는 경우
		if(special_pattern.test(writer_v) == true) {
			alert("특수문자가 입력되었습니다.");
			form.writer.select();
			return;
		}
		/*
		//공백 혹은 특수문자가 있는 경우
		if(writer_v.search(/\W|\s/g) > -1){ 
			//search 함수는 대상 문자열에 조건 문자열이 포함되어있는지 포함되어 있다면 몇번째 위치에서 처음 확인이 가능한지를 리턴해주는 함수 
			// -1이면 없다는 의미​ / -1보다 크면 있다는 조건이 된다. 
			alert("특수문자 또는 공백이 입력되었습니다.");
			form.writer.select();
			return;
		}
		*/
		/*** 비밀번호 유효성 검사 ***/
		var password_v = form.password.value;
		
		//입력을 안할 경우
		if(password_v == '' || password_v == null) {
			alert("비밀번호를 입력해주세요.");
			form.password.select();
			return;
		}
		
		//공백만 입력 된 경우
		if(password_v.replace(blank_pattern,'') == "") {
			alert("공백만 입력되었습니다.");
			form.password.select();
			return;
		}
		
		//문자열에 공백이 있는 경우
		if(blank_pattern.test(password_v) == true) {
			alert("공백이 입력되었습니다.");
			form.password.select();
			return;
		}
		
		//비밀번호 4자 이상으로 입력
		if(password_v.length < 4) {
			alert("비밀번호는 4자이상으로 입력해주세요.");
			form.password.select();
			return;
		}
		
		/** 제목 유효성 검사 **/
		var title_v = form.title.value;
		
		//입력을 안할 경우
		if(title_v == '' || title_v == null){
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
		
		/** 내용 유효성 검사 **/
		var content_v = form.content.value;
		
		//입력을 안한 경우
		if(content_v == '' || content_v == null) {
			alert("내용을 입력해주세요.");
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
		
     	form.submit(); 
	}


</script>

</html>