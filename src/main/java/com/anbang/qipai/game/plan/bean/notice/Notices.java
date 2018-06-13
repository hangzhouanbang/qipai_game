package com.anbang.qipai.game.plan.bean.notice;

public class Notices {
	
	private String id;//公告ID,游戏界面显示的公告只有一条
	private String adminname;//管理员名称
	private String notice;//公告信息
	private String place;//发布位置:0-游戏大厅，1-游戏房间，2-游戏大厅加游戏房间同时显示 
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
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getAdminname() {
		return adminname;
	}
	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}
	
	
}
