package com.anbang.qipai.game.plan.bean.members;

public class MemberLoginLimitRecord {
	private String id;
	private String memberId;
	private String nickname;
	private String desc;
	private long createTime;
	private boolean efficient;// 是否生效

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public boolean isEfficient() {
		return efficient;
	}

	public void setEfficient(boolean efficient) {
		this.efficient = efficient;
	}

}
