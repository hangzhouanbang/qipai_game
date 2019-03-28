package com.anbang.qipai.game.web.fb;

import java.util.List;

public class DdzLawsFB {
	private String panshu;
	private String renshu;
	private String difen;
	private String qxp = "false";
	private String szfbxp = "false";

	public DdzLawsFB(List<String> lawNames) {
		lawNames.forEach((lawName) -> {
			if (lawName.equals("bj")) {// 八局
				panshu = "8";
			} else if (lawName.equals("sj")) {// 四局
				panshu = "4";
			} else if (lawName.equals("slj")) {// 十六局
				panshu = "16";
			} else if (lawName.equals("er")) {// 二人
				renshu = "2";
			} else if (lawName.equals("sanr")) {// 三人
				renshu = "3";
			} else if (lawName.equals("qxp")) {// 去小牌
				qxp = "true";
			} else if (lawName.equals("szfbxp")) {// 三张分不洗牌
				szfbxp = "true";
			} else if (lawName.equals("yf")) {// 一分
				difen = "1";
			} else if (lawName.equals("ef")) {// 二分
				difen = "2";
			} else if (lawName.equals("sf")) {// 三分
				difen = "3";
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

	public String getDifen() {
		return difen;
	}

	public void setDifen(String difen) {
		this.difen = difen;
	}

	public String getQxp() {
		return qxp;
	}

	public void setQxp(String qxp) {
		this.qxp = qxp;
	}

	public String getSzfbxp() {
		return szfbxp;
	}

	public void setSzfbxp(String szfbxp) {
		this.szfbxp = szfbxp;
	}

}
