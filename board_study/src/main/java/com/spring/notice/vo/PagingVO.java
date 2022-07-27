package com.spring.notice.vo;

public class PagingVO {
	
	//필드
	private int startRow; //시작행
	private int endRow;  //마지막 행
	
	//메서드
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}


	@Override
	public String toString() {
		return "PagingVO [startRow=" + startRow + ", endRow=" + endRow + "]";
	}
	
}
