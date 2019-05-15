package com.anbang.qipai.game.web.fb;

import java.util.List;

/**
 * 大菠萝玩法
 * 
 * @author lsc
 *
 */
public class DblLawsFB {
	private String panshu;
	private String renshu;
	private String dqef = "false";// 打枪双番
	private String dqsf = "false";// 打枪三番
	private String bx = "bubian";// 百变玩法
	private String bihuase = "false";// 比花色
	private String zidongzupai = "false";// 自动组牌
	private String yitiaolong = "false";// 一条龙

	public DblLawsFB(List<String> lawNames) {
		lawNames.forEach((lawName) -> {
			if (lawName.equals("shj")) {// 十局
				panshu = "10";
			} else if (lawName.equals("esj")) {// 二十局
				panshu = "20";
			} else if (lawName.equals("sij")) {// 四局
				panshu = "4";
			} else if (lawName.equals("yj")) {// 一局
				panshu = "1";
			} else if (lawName.equals("sansj")) {// 三十局
				panshu = "30";
			} else if (lawName.equals("sisj")) {// 四十局
				panshu = "40";
			} else if (lawName.equals("er")) {// 二人
				renshu = "2";
			} else if (lawName.equals("sanr")) {// 三人
				renshu = "3";
			} else if (lawName.equals("sir")) {// 四人
				renshu = "4";
			} else if (lawName.equals("dqef")) {// 打枪双番
				dqef = "true";
			} else if (lawName.equals("dqsf")) {// 打枪三番
				dqsf = "true";
			} else if (lawName.equals("baibian")) {// 百变
				bx = "baibian";
			} else if (lawName.equals("bhs")) {// 比花色
				bihuase = "true";
			} else if (lawName.equals("zdzp")) {// 自动组牌
				zidongzupai = "true";
			} else if (lawName.equals("ytl")) {// 一条龙
				yitiaolong = "true";
			} else {

			}
		});
	}

	public String getPanshu() {
		return panshu;
	}

	public void setPanshu(String panshu) {
		this.panshu = panshu;
	}

	public String getRenshu() {
		return renshu;
	}

	public void setRenshu(String renshu) {
		this.renshu = renshu;
	}

	public String getDqef() {
		return dqef;
	}

	public void setDqef(String dqef) {
		this.dqef = dqef;
	}

	public String getDqsf() {
		return dqsf;
	}

	public void setDqsf(String dqsf) {
		this.dqsf = dqsf;
	}

	public String getBx() {
		return bx;
	}

	public void setBx(String bx) {
		this.bx = bx;
	}

	public String getBihuase() {
		return bihuase;
	}

	public void setBihuase(String bihuase) {
		this.bihuase = bihuase;
	}

	public String getZidongzupai() {
		return zidongzupai;
	}

	public void setZidongzupai(String zidongzupai) {
		this.zidongzupai = zidongzupai;
	}

	public String getYitiaolong() {
		return yitiaolong;
	}

	public void setYitiaolong(String yitiaolong) {
		this.yitiaolong = yitiaolong;
	}
}
