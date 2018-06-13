package com.anbang.qipai.game.plan.bean.mail;

public class SystemMail {
	private String id;//邮件id
	
	private String title;//邮件标题
	
	private String adminname;//管理员名称
	
	private String file;//邮件图片文档
	
	private Long createtime;//生成时间
	
	private Integer number;//金币数量
	
	private Integer integral;//积分数量
	
	private Integer vipcard;//会员体验时间，按日来计算
	
	private Integer status;//状态；0-维护，1-恢复

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
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

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

}
