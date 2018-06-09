package com.anbang.qipai.game.plan.bean.mail;

public class TrackPoint {
	
	private String id;
	
	private String memberid;
	
	private boolean state;//true-有小红点，false-没有小红点

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	

}
