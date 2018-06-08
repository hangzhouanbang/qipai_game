package com.anbang.qipai.game.plan.bean.members;

/**
 * 会员
 * 
 * @author Neo
 *
 */
public class Member {

	private String id;
	private boolean vip;
	private long createTime;
	private MemberRights rights;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public MemberRights getRights() {
		return rights;
	}

	public void setRights(MemberRights rights) {
		this.rights = rights;
	}

}
