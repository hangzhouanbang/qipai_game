package com.anbang.qipai.game.web.fb;

import java.util.List;

/**
 * 温州麻将规则
 * 
 * @author lsc
 *
 */
public class WzmjLawsFB {

	private String panshu;
	private String renshu;
	private String jinjie = "false";
	private String teshushuangfan = "false";
	private String caishenqian = "false";
	private String shaozhongfa = "false";
	private String lazila = "false";

	public WzmjLawsFB(List<String> lawNames) {
		lawNames.forEach((lawName) -> {
			if (lawName.equals("bj")) {// 八局
				panshu = "8";
			} else if (lawName.equals("sej")) {// 十二局
				panshu = "12";
			} else if (lawName.equals("sj")) {// 四局
				panshu = "4";
			} else if (lawName.equals("slj")) {// 十六局
				panshu = "16";
			} else if (lawName.equals("er")) {// 二人
				renshu = "2";
			} else if (lawName.equals("sanr")) {// 三人
				renshu = "3";
			} else if (lawName.equals("sir")) {// 四人
				renshu = "4";
			} else if (lawName.equals("jj")) {// 进阶
				jinjie = "true";
			} else if (lawName.equals("tssf")) {// 特殊双翻
				teshushuangfan = "true";
			} else if (lawName.equals("sir")) {// 财神钱
				caishenqian = "true";
			} else if (lawName.equals("szf")) {// 少中发
				shaozhongfa = "true";
			} else if (lawName.equals("lzl")) {// 辣子辣
				lazila = "true";
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

	public String getJinjie() {
		return jinjie;
	}

	public void setJinjie(String jinjie) {
		this.jinjie = jinjie;
	}

	public String getTeshushuangfan() {
		return teshushuangfan;
	}

	public void setTeshushuangfan(String teshushuangfan) {
		this.teshushuangfan = teshushuangfan;
	}

	public String getCaishenqian() {
		return caishenqian;
	}

	public void setCaishenqian(String caishenqian) {
		this.caishenqian = caishenqian;
	}

	public String getShaozhongfa() {
		return shaozhongfa;
	}

	public void setShaozhongfa(String shaozhongfa) {
		this.shaozhongfa = shaozhongfa;
	}

	public String getLazila() {
		return lazila;
	}

	public void setLazila(String lazila) {
		this.lazila = lazila;
	}

}
