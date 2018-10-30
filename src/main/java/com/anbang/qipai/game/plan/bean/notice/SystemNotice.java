package com.anbang.qipai.game.plan.bean.notice;

import com.anbang.qipai.game.conf.SystemNoticePlace;

public class SystemNotice {
	private String id;
	private String state;// 状态:启用,禁用
	private String adminName;// 管理员名称
	private SystemNoticePlace place;// 发布位置
	private String content;// 公告信息
	private long createTime;// 发布时间
	private boolean valid;// 是否可用

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public SystemNoticePlace getPlace() {
		return place;
	}

	public void setPlace(SystemNoticePlace place) {
		this.place = place;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
