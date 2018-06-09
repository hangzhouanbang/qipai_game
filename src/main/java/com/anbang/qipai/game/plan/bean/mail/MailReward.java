package com.anbang.qipai.game.plan.bean.mail;

public class MailReward {
	
	private String id;
	
	private Integer number;//金币数量
	
	private Integer integral;//积分数量
	
	private Integer vipcard;//会员体验时间，按日来计算

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getVipcard() {
		return vipcard;
	}

	public void setVipcard(Integer vipcard) {
		this.vipcard = vipcard;
	}
	
	

}
