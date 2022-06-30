package com.spring.notice.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.notice.vo.BoardVO;

public interface BoardService {
	
	//파일 다운
	void fileDown(BoardVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//글 등록
	void insertBoard(HttpServletRequest request, BoardVO vo) throws Exception;
	
	//글 목록 조회
	Map<String, Object> getBoardList(BoardVO vo) throws Exception;
	
	//글 상세
	Map<String, Object> getBoard(BoardVO vo) throws Exception;
	
	//글 상세 읽기
	Map<String, Object> readBoard(BoardVO vo) throws Exception;
	
	//글 수정
	void modifyBoard(HttpServletResponse response, BoardVO vo) throws Exception;
	
	//글 삭제
	void deleteBoard(HttpServletResponse response, BoardVO vo) throws Exception;
	
	
}
