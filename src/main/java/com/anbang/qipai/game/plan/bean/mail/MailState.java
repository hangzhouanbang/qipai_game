package com.anbang.qipai.game.plan.bean.mail;

public class MailState {
	
	private String id;
	
	private String memberid;//会员id
	
	private String mailid;//邮件id
	
	private String statemail;//邮件状态0-已读，1-未读
	
	private String receive;//奖励状态0-已领，1-未领,2-没有附件奖励
	
	private long rewardTime;//领取时间
	
	private String deletestate;//删除状态0-已删除，1-代表未删除
	
	private long createTime;//创建时间
	
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

	public String getMailid() {
		return mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	public String getStatemail() {
		return statemail;
	}

	public void setStatemail(String statemail) {
		this.statemail = statemail;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getDeletestate() {
		return deletestate;
	}

	public void setDeletestate(String deletestate) {
		this.deletestate = deletestate;
	}

	public long getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(long rewardTime) {
		this.rewardTime = rewardTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}


}
