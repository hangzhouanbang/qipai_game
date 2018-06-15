package com.anbang.qipai.game.plan.bean.mail;

public class SystemMailState {
	
	private String statemail;//邮件状态0-已读，1-未读
	
	private String receive;//奖励状态0-已领，1-未领,2-没有附件奖励
	
	private SystemMail systemMail;//邮件信息

	public String getStatemail() {
		return statemail;
	}

	public void setStatemail(String statemail) {
		this.statemail = statemail;
	}

	public SystemMail getSystemMail() {
		return systemMail;
	}

	public void setSystemMail(SystemMail systemMail) {
		this.systemMail = systemMail;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	
}
