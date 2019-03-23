package com.anbang.qipai.game.web.fb;

import java.util.List;

/**
 * 瑞安麻将规则 form bean
 * 
 * @author Neo
 *
 */
public class RamjLawsFB {

	private String difen;
	private String taishu;
	private String panshu;
	private String renshu;
	private String dapao = "false";

	public RamjLawsFB(List<String> lawNames) {
		lawNames.forEach((lawName) -> {
			if (lawName.equals("wsf")) {// 50分
				difen = "50";
			} else if (lawName.equals("lsf")) {// 60分
				difen = "60";
			} else if (lawName.equals("stfd")) {// 三台封顶
				taishu = "3";
			} else if (lawName.equals("bxts")) {// 不限台数
				taishu = "0";
			} else if (lawName.equals("bj")) {// 八局
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
			} else if (lawName.equals("dpwf")) {// 打炮玩法
				dapao = "true";
			} else {
			}
		});
	}

	public int payForCreateRoom() {
		int gold = 100;
		if (panshu.equals("12")) {
			gold = 100;
		} else if (panshu.equals("16")) {
			gold = 200;
		} else if (panshu.equals("8")) {
			gold = 100;
		} else if (panshu.equals("4")) {
			gold = 100;
		} else {

		}
		return gold;
	}

	public int payForJoinRoom() {
		int gold = 100;
		if (panshu.equals("12")) {
			gold = 100;
		} else if (panshu.equals("16")) {
			gold = 200;
		} else if (panshu.equals("8")) {
			gold = 100;
		} else if (panshu.equals("4")) {
			gold = 100;
		} else {

		}
		return gold;
	}

	public String getDifen() {
		return difen;
	}

	public String getTaishu() {
		return taishu;
	}

	public String getPanshu() {
		return panshu;
	}

	public String getRenshu() {
		return renshu;
	}

	public String getDapao() {
		return dapao;
	}

}
