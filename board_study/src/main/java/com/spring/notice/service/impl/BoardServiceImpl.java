package com.spring.notice.service.impl;

import java.io.File;
import java.io.PrintWriter;
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

@Service

public class BoardServiceImpl implements BoardService{
	
	@Autowired 
	private BoardMapper mapper;
	
	Common common = new Common();
	SecurityUtill security = new SecurityUtill();
	
	
	//글 등록
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
		title = common.htmlEn(title);
		content = common.htmlEn(content);
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
		
		//uploadFile에 값이 null이 아니면
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

	//글 목록
	@Override
	public Map<String, Object> getBoardList() throws Exception{
		System.out.println("글 목록 서비스>>");
		
		Map<String, Object> result = new HashMap<>();
		List<BoardVO> boardList = mapper.getBoardList();
		int count = mapper.count();
		
		for (BoardVO board : boardList) {
			
			//xss 화이트리스트 처리
			String title = board.getTitle();
			String content = board.getContent();
			title = common.whiteList(title);
			content = common.whiteList(content);
			
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
		
		
		result.put("boardList", boardList);
		result.put("count", count);
		
		return result;
	}

	//글 상세
	@Override
	public Map<String, Object> getBoard(BoardVO vo) throws Exception {
		System.out.println("글 상세 서비스>>");
		
		Map<String, Object> result = new HashMap<>();
		
		//조회수 증가 처리
		mapper.HitUpdate(vo);
		
		BoardVO board = mapper.getBoard(vo);
		
		String title = board.getTitle();
		String content = board.getContent();
		
		title = common.whiteList(title);
		content = common.whiteList(content);
		
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
	
	//글 상세 읽기
	@Override
	public Map<String, Object> readBoard(BoardVO vo) throws Exception {
		System.out.println("글 가져오기 서비스>>");
		
		Map<String, Object> result = new HashMap<>();
		
		BoardVO board = mapper.getBoard(vo);
		
		String title = board.getTitle();
		String content = board.getContent();
		
		title = common.whiteList(title);
		content = common.whiteList(content);
		
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
	
	//글 수정
	@Override
	public void modifyBoard(HttpServletRequest request, BoardVO vo) throws Exception {
		System.out.println("글 수정 서비스>>"+vo);		
		
		//아이피 처리
		String ip = common.getUserIp();
		vo.setUpdate_ip(ip);
		System.out.println("수정 아이피>>"+ip);
		
		//xss
		String title = vo.getTitle();
		String content = vo.getTitle();
		
		title = common.htmlEn(title);
		content = common.htmlEn(content);
		
		vo.setTitle(title);
		vo.setContent(content);
		
		//파일 업로드 처리
		String realpath = "C:/workspaces/workspace_company/board_study/src/main/webapp/upload";
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
				filePath = realpath + "/" + fileName;
				uploadFile.transferTo(new File(filePath)); //transferTo 메서드에 의해 저장경로에 파일이 생성됨
			}
			
			fileChk = common.isPermisionFileMimeType(filePath);
			
			if(fileChk) {
				System.out.println("파일 업로드 성공");
				vo.setFile(fileName);
				mapper.modiFileBoard(vo);
			}
			else {
				System.out.println("유효한 확장자가 아닙니다.");
				return;
			}	
		}
		else {
			System.out.println("수정 업로드 성공");
			mapper.modifyBoard(vo);
		}
		
	}

	//글 삭제
	@Override
	public void deleteBoard(HttpServletResponse response, BoardVO vo) throws Exception {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=utf-8");
		out.println("<script language='javascript'>");
		
		System.out.println("글 삭제 서비스>>"+vo);
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
		
	}
	
}
