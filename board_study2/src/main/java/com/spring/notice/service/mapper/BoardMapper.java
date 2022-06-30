package com.spring.notice.service.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.notice.vo.BoardVO;
import com.spring.notice.vo.PagingVO;

@Repository

public class BoardMapper {
	
	@Autowired
	SqlSession mybatis;
	
	//글 등록
	public void insertBoard(BoardVO vo) {
		mybatis.insert("board.insertBoard", vo);
	}
	
	//글 목록
	public List<BoardVO> getBoardList(PagingVO paging) {
		return mybatis.selectList("board.getBoardList", paging);
	} 
	
	//글 상세
	public BoardVO getBoard(BoardVO vo) {
		return mybatis.selectOne("board.getBoard", vo);
	}
	
	//글 수정 : 파일 x
	public void modifyBoard(BoardVO vo) {
		mybatis.update("board.modifyBoard", vo);
	}
	
	//글 수정 :파일 o
	public void modiFileBoard(BoardVO vo) {
		mybatis.update("board.modiFileBoard", vo);
	}
	
	//글 삭제
	public void deleteBoard(BoardVO vo) {
		mybatis.delete("board.deleteBoard", vo);
	}
	
	//전체 게시글 개수 조회
	public int count() {
		return mybatis.selectOne("board.count");
	}
	
	//조회수 증가
	public void HitUpdate(BoardVO vo) {
		mybatis.update("board.HitUpdate", vo);
	}
	
	
}
