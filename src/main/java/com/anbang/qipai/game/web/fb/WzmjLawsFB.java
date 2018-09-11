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
	private String jinjie1 = "false";
	private String jinjie2 = "false";
	private String gangsuanfen = "false";
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
			} else if (lawName.equals("jj1")) {// 进阶
				jinjie1 = "true";
			} else if (lawName.equals("jj2")) {// 进阶
				jinjie2 = "true";
			} else if (lawName.equals("tssf")) {// 特殊双翻
				teshushuangfan = "true";
			} else if (lawName.equals("csq")) {// 财神钱
				caishenqian = "true";
			} else if (lawName.equals("szf")) {// 少中发
				shaozhongfa = "true";
			} else if (lawName.equals("lzl")) {// 辣子辣
				lazila = "true";
			} else if (lawName.equals("gsf")) {// 杠算分
				gangsuanfen = "true";
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

	public String getJinjie1() {
		return jinjie1;
	}

	public void setJinjie1(String jinjie1) {
		this.jinjie1 = jinjie1;
	}

	public String getJinjie2() {
		return jinjie2;
	}

	public void setJinjie2(String jinjie2) {
		this.jinjie2 = jinjie2;
	}

	public String getGangsuanfen() {
		return gangsuanfen;
	}

	public void setGangsuanfen(String gangsuanfen) {
		this.gangsuanfen = gangsuanfen;
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
