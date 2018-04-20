package com.javaex.vo;

public class BoardVo {
	int no;
	String title;
	String content;
	int hit;
	String date;
	int user_no;
	String name;
	
	public BoardVo() {
	}
	
	public BoardVo(String title, String content, int hit, String date, int user_no) {
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.date = date;
		this.user_no = user_no;
	}
	
	public BoardVo(int no, String title, String content, int hit, String date, int user_no) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.date = date;
		this.user_no = user_no;
	}

	public BoardVo(int no, String title, String content, int hit, String date, int user_no, String name) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.date = date;
		this.user_no = user_no;
		this.name = name;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", date=" + date
				+ ", user_no=" + user_no + ", name=" + name + "]";
	}

	
	
	
	
}
