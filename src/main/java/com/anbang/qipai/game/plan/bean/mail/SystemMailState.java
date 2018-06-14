package com.anbang.qipai.game.plan.bean.mail;

public class SystemMailState {
	
	private String statemail;//邮件状态0-已读，1-未读
	
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

	
}
