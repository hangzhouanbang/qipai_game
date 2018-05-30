package com.anbang.qipai.game.plan.bean;

public class Notices {
	
	private String id;//公告ID,游戏界面显示的公告只有一条
	private String notice;//公告信息
	private Integer state;//状态{1代表启用，0代表禁用}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	
}
