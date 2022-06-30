package com.spring.notice.vo;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class BoardVO {
	
	//필드
	private int rownum;
	private int num;
	private String title;
	private String content;
	private String writer;
	private String password;
	private MultipartFile uploadFile;
	private String file;
	private String realPath = "C:/workspaces/workspace_company/board_study/src/main/webapp/upload";
	private int hit;
	private Timestamp write_date;
	private Timestamp update_date;
	private String write_ip;
	private String update_ip;
	private int page;
	
	//메서드
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getRealPath() {
		return realPath;
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public Timestamp getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Timestamp write_date) {
		this.write_date = write_date;
	}
	public Timestamp getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}
	public String getWrite_ip() {
		return write_ip;
	}
	public void setWrite_ip(String write_ip) {
		this.write_ip = write_ip;
	}
	public String getUpdate_ip() {
		return update_ip;
	}
	public void setUpdate_ip(String update_ip) {
		this.update_ip = update_ip;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	@Override
	public String toString() {
		return "BoardVO [rownum=" + rownum + ", num=" + num + ", title=" + title + ", content=" + content + ", writer="
				+ writer + ", password=" + password + ", uploadFile=" + uploadFile + ", file=" + file + ", realPath="
				+ realPath + ", hit=" + hit + ", write_date=" + write_date + ", update_date=" + update_date
				+ ", write_ip=" + write_ip + ", update_ip=" + update_ip + ", page=" + page + "]";
	}
}
