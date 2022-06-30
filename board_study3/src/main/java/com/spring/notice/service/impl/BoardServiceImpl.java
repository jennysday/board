package com.spring.notice.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.notice.service.BoardService;
import com.spring.notice.service.mapper.BoardMapper;
import com.spring.notice.service.utill.Common;
import com.spring.notice.service.utill.SecurityUtill;
import com.spring.notice.vo.BoardVO;
import com.spring.notice.vo.PagingVO;

@Service

public class BoardServiceImpl implements BoardService{
	
	@Autowired 
	private BoardMapper mapper;
	
	Common common = new Common();
	SecurityUtill security = new SecurityUtill();
	
	/*** 파일 다운 ***/
	@Override
	public void fileDown(BoardVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			request.setCharacterEncoding("utf-8");
			
			String fileUrl = "";
			String fileName = request.getParameter("path");
			
			fileUrl = vo.getRealPath() + File.separator + fileName;
			
			File file = new File(fileUrl); //주어진 문자열 경로를 갖는 File 객체를 생성
			System.out.println("파일 이름 >> "+file.getName());
			response.setHeader("Content-Disposition", "attachment; filename="+ file.getName());
			
			FileInputStream fileInputStream = new FileInputStream(fileUrl);
			OutputStream out = response.getOutputStream();
			
			int read = 0;
			byte[] buffer = new byte[1024];
			while((read = fileInputStream.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			
		} catch (Exception e) {
			throw new Exception("download.error");
		}
	}	
	
	/*** 글 등록 ***/
	@Override
	public void insertBoard(HttpServletRequest request, BoardVO vo) throws Exception {
		System.out.println("글등록 서비스>>" + vo);
		
		//아이피 처리
		String ip = common.getUserIp();
		System.out.println("아이피>>"+ip);
		vo.setWrite_ip(ip);
		
		//제목, 내용 xss 처리
		String title = vo.getTitle();
		String content = vo.getContent();
		title = security.htmlEn(title);
		content = security.htmlEn(content);
		System.out.println("title>> "+title);
		System.out.println("content>> "+content);
		vo.setTitle(title);
		vo.setContent(content);
		
		//작성자 양방향 암호화
		String writer = vo.getWriter();
		writer = security.encrypt(writer);
		System.out.println("작성자 양방향 암호화>>"+writer);
		vo.setWriter(writer);
		
		//비밀번호 단방향 암호화
		String password = vo.getPassword();
		password = security.SHA256Encrypt(password);
		System.out.println("비밀번호>>"+password);
		vo.setPassword(password);
		
		//파일 업로드 처리
		String realpath = vo.getRealPath();
		String fileName = "";
		String filePath = "";
		String originalFileName = vo.getUploadFile().getOriginalFilename();
		boolean fileChk = false;
		
		//uploadFile에 값이 있으면
		if(originalFileName != "") {
			MultipartFile uploadFile = vo.getUploadFile();
			
			//첨부 파일 여부 확인
			if(!uploadFile.isEmpty()) { //isEmpty() : 문자열의 길이가 0인 경우에, true를 리턴합니다.
				originalFileName = uploadFile.getOriginalFilename(); //파일이름
				String ext = FilenameUtils.getExtension(originalFileName); //확장자 구하기
				
				UUID uuid = UUID.randomUUID(); //UUID 구하기 (파일 이름 중복 방지)
				fileName = uuid + "." + ext;  // 고유 파일 이름 생성
				filePath = realpath + File.separator + fileName; //파일명을 포함한 파일 경로
				uploadFile.transferTo(new File(filePath)); //transferTo 메소드는 업로드한 파일을 지정한 파일에 저장하는 메소드이다.
			}
			
			fileChk = common.isPermisionFileMimeType(filePath);
			
			if(fileChk == true) {
				System.out.println("파일 업로드 성공");
				vo.setFile(fileName);
				//첨부파일 있을 경우 mapper
				mapper.insertBoard(vo);
			} else {
				System.out.println("유효한 확장자가 아닙니다.");
				return;
			}					
		}
		else {
			System.out.println("등록 업로드 성공");
			//첨부 파일이 없을 경우 mapper
			mapper.insertBoard(vo);
		}
	}

	/*** 글 목록 ***/
	@Override
	public Map<String, Object> getBoardList(BoardVO vo) throws Exception{
		System.out.println("글 목록 서비스>>");
		
		Map<String, Object> result = new HashMap<>();
		
		//페이징 처리
		PagingVO paging = new PagingVO();
		int page = vo.getPage();
		System.out.println("page : " + page);
		
		//페이지 번호가 0이면 페이지를 1로 변경
		if(page == 0) {
			page = 1;
			vo.setPage(page);
		}
		System.out.println("page>> " + vo.getPage());

		int limit = 10;
		int listCount = 0;
		int startRow = (page-1) * 10 + 1; //읽기 시작 할 row 번호
		int endRow = startRow + limit-1; //읽을 마지막 번호
		
		paging.setStartRow(startRow);
		paging.setEndRow(endRow);

		//전체 게시글 개수 조회
		listCount = mapper.count();
		result.put("count", listCount);
		
		//게시글 조회
		List<BoardVO> boardList = mapper.getBoardList(paging);
		int maxPage;
		int startPage;
		int endPage;
		
		//게시글이 0이 아닐 경우 if문 실행
		if(boardList.size() != 0) {
			System.out.println("게시글 개수 : " + boardList.size());
			
			//총 페이지 수
			maxPage = (int)((double)listCount / limit + 0.95); //0.95를 더해서 올림 처리
			
			//현재 페이지에 보여줄 시작 페이지 수 (1,11,21,31)
			startPage = (((int)((double)page / 10 + 0.9)) -1) * 10 + 1;
			
			//현재 페이지에 보여줄 마지막 페이지 수 (10,20,30,40)
			endPage = maxPage;
			
			if(endPage > startPage + 10 - 1) {
				endPage = startPage + 10 - 1; 
			}
			
			System.out.println("maxPage>>"+maxPage);
			System.out.println("startPage>>"+startPage);
			System.out.println("endPage>>"+endPage);
			
			result.put("page", page);
			result.put("maxPage", maxPage);
			result.put("startPage", startPage);
			result.put("endPage", endPage);
			result.put("boardList", boardList);
			
			for (BoardVO board : boardList) {
				
				//xss 화이트리스트 처리
				String title = board.getTitle();
				String content = board.getContent();
				title = security.whiteList(title);
				content = security.whiteList(content);
				
				System.out.println("title>>"+title);
				System.out.println("content>>"+content);
				board.setTitle(title);
				board.setContent(content);
				
				//작성자 양방향 복호화
				String writer = board.getWriter();
				writer = security.decrypt(writer);
				System.out.println("writer>>"+writer);
				board.setWriter(writer);
				
			}
		} else { //게시글이 0이면 page = 1로 변경
			page = 1;
			result.put("page", page);
		}
		return result;
	}

	/*** 글 상세 ***/
	@Override
	public Map<String, Object> getBoard(BoardVO vo) throws Exception {
		System.out.println("글 상세 서비스>>");
		
		Map<String, Object> result = new HashMap<>();
		
		BoardVO board = mapper.getBoard(vo);
		
		if(board == null) {
			System.out.println("상세보기 실패");
			return null;
		}
		
		System.out.println("상세보기 성공");
		
		//페이지 처리
		int page = vo.getPage();
		result.put("page", page);	
		
		//조회수 증가 처리
		mapper.HitUpdate(vo);
		
		String title = board.getTitle();
		String content = board.getContent();
		
		title = security.whiteList(title);
		content = security.whiteList(content);
		
		System.out.println("title>>"+title);
		System.out.println("content>>"+content);
		
		board.setTitle(title);
		board.setContent(content);
		
		String writer = board.getWriter();
		writer = security.decrypt(writer);
		System.out.println("writer>>"+writer);
		
		board.setWriter(writer);
		
		result.put("board", board);
		
		return result;
	}
	
	/*** 글 상세 읽기 ***/
	@Override
	public Map<String, Object> readBoard(BoardVO vo) throws Exception {
		System.out.println("글 가져오기 서비스>>");
		
		Map<String, Object> result = new HashMap<>();
		
		BoardVO board = mapper.getBoard(vo);
		
		if(board == null) {
			System.out.println("상세보기 실패");
			return null;
		}
		
		System.out.println("상세보기 성공");
		
		//페이지 처리
		int page = vo.getPage();
		result.put("page", page);
		
		String title = board.getTitle();
		String content = board.getContent();
		
		title = security.whiteList(title);
		content = security.whiteList(content);
		
		System.out.println("title>>"+title);
		System.out.println("content>>"+content);
		
		board.setTitle(title);
		board.setContent(content);
		
		String writer = board.getWriter();
		writer = security.decrypt(writer);
		System.out.println("writer>>"+writer);
		
		board.setWriter(writer);
		
		result.put("board", board);
		
		return result;
	}
	
	/*** 글 수정 ***/
	@Override
	public void modifyBoard(HttpServletResponse response, BoardVO vo) throws Exception {
		System.out.println("글 수정 서비스>>"+vo);		
		
		//게시글 번호
		int num = vo.getNum();
		
		//페이지 번호
		int page = vo.getPage();
		
		//데이터베이스에 저장된 비밀번호 가져오기 
		BoardVO board = mapper.getBoard(vo);
		String originalpwd = board.getPassword();
		System.out.println("데이터베이스에 저장된 비밀번호>>"+originalpwd);
		
		//사용자에게 입력 받은 비밀번호 가져오기
		String userpwd = vo.getPassword();
		System.out.println("입력 비밀번호>>"+userpwd);
		
		//사용자에게 받은 비밀번호 단방향 처리
		userpwd = security.SHA256Encrypt(userpwd);
		System.out.println("입력 비밀번호 단방향 처리>>"+userpwd);
		
		//저장된 비밀번호와 입력 받은 비밀번호가 일치하면
		if(originalpwd.equals(userpwd)) {
			//아이피 처리
			String ip = common.getUserIp();
			vo.setUpdate_ip(ip);
			System.out.println("수정 아이피>>"+ip);
			
			//xss
			String title = vo.getTitle();
			String content = vo.getContent();
			
			title = security.htmlEn(title);
			content = security.htmlEn(content);
			
			vo.setTitle(title);
			vo.setContent(content);
			
			//파일 업로드 처리
			String realpath = vo.getRealPath();
			String fileName = "";
			String filePath = "";
			String originalFileName = vo.getUploadFile().getOriginalFilename();
			boolean fileChk = false;
			
			System.out.println("originalFileName>>"+originalFileName);
			
			if(originalFileName != "") {
				MultipartFile uploadFile = vo.getUploadFile();
				
				if(!uploadFile.isEmpty()) { //첨부 파일 여부 확인
					originalFileName = uploadFile.getOriginalFilename(); //파일 이름 구하기
					String ext = FilenameUtils.getExtension(originalFileName); //파일 확장자 구하기
					
					UUID uuid = UUID.randomUUID(); //UUID 만들기
					fileName = uuid + "." + ext;
					filePath = realpath + File.separator + fileName;
					uploadFile.transferTo(new File(filePath)); //transferTo 메서드에 의해 저장경로에 파일이 생성됨
				}
				
				fileChk = common.isPermisionFileMimeType(filePath);
				
				if(fileChk) {
					System.out.println("파일 업로드 성공");
					vo.setFile(fileName);
					mapper.modiFileBoard(vo);
					common.alertAndGo(response, "수정 되었습니다.", "getBoard.do?num="+num+"&page="+page);
				}
				else {
					System.out.println("유효한 확장자가 아닙니다.");
					common.alertAndGo(response, "유효한 확장자가 아닙니다.", "modifyView.do?num="+num+"&page="+page);
					return;
				}	
			}
			else {
				System.out.println("수정 업로드 성공");
				mapper.modifyBoard(vo);
				common.alertAndGo(response, "수정 되었습니다.", "getBoard.do?num="+num+"&page="+page);
			}
		} else {
			common.alertAndGo(response, "비밀번호가 일치하지 않습니다.", "modifyView.do?num="+num+"&page="+page);
		}
		
		
		
		
	}

	/*** 글 삭제 ***/
	@Override
	public void deleteBoard(HttpServletResponse response, BoardVO vo) throws Exception {
		System.out.println("글 삭제 서비스>>"+vo);
		
		//페이지 번호
		int page = vo.getPage();
		System.out.println("page>>"+page);
		
		//데이터베이스에 저장된 비밀번호 가져오기 
		BoardVO board = mapper.getBoard(vo);
		String originalpwd = board.getPassword();
		System.out.println("데이터베이스에 저장된 비밀번호>>"+originalpwd);
		
		//사용자에게 입력 받은 비밀번호 가져오기
		String userpwd = vo.getPassword();
		System.out.println("입력 비밀번호>>"+userpwd);
		
		//사용자에게 받은 비밀번호 단방향 처리
		userpwd = security.SHA256Encrypt(userpwd);
		System.out.println("입력 비밀번호 단방향 처리>>"+userpwd);
		
		//저장된 비밀번호와 입력 받은 비밀번호가 일치하면
		if(originalpwd.equals(userpwd)) {
			mapper.deleteBoard(vo);
			common.alertAndGo(response, "삭제가 완료되었습니다.", "boardList.do?page="+page);
		}
		else {
			int num = vo.getNum();
			common.alertAndGo(response, "비밀번호가 일치하지 않습니다.", "deleteView.do?num="+num+"&page="+page);
		}
		
		/** alert메서드를 utill로 만들지 않고, 처리한 비밀번호 처리 로직  **/
		/* 
		System.out.println("글 삭제 서비스>>"+vo);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=utf-8");
		out.println("<script language='javascript'>");
		
		//게시글 비밀번호 가져오기
		BoardVO board = mapper.getBoard(vo);
		String originalpwd = board.getPassword();
		System.out.println("비밀번호>>"+originalpwd);
		
		String password = vo.getPassword();
		System.out.println("입력 비밀번호>>"+password);
		password = security.SHA256Encrypt(password);
		System.out.println("입력 비밀번호 SHA256Encrypt>>"+password);
		
		if(originalpwd.equals(password)) {
			mapper.deleteBoard(vo);
			out.println("alert('삭제 완료');");
			out.println("location.href='boardList.do';");
		} 
		else {
			
			out.println("alert('비밀번호가 다릅니다');");
			out.println("location.href='boardList.do';");
			
		} 
		out.println("</script>");
		out.flush();
		*/
	}
	
}
