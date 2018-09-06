package com.anbang.qipai.game.web.fb;

import java.util.List;

public class FpmjLawsFB {
	private String panshu;
	private String renshu;
	private String hognzhongcaishen = "false";
	private String dapao = "false";
	private String sipaofanbei = "false";
	private String zhuaniao = "false";
	private String niaoshu = "4";

	public FpmjLawsFB(List<String> lawNames) {
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
			} else if (lawName.equals("hzdcs")) {// 红中当财神
				hognzhongcaishen = "true";
			}
			if (lawName.equals("dp")) {// 打炮
				dapao = "true";
			}
			if (lawName.equals("spfb")) {// 四炮翻倍
				sipaofanbei = "true";
			}
			if (lawName.equals("zn")) {// 抓鸟
				zhuaniao = "true";
			}
			if (lawName.equals("szn")) {// 四只鸟
				setNiaoshu("4");
			} else if (lawName.equals("lzn")) {// 六只鸟
				setNiaoshu("6");
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

	public String getHognzhongcaishen() {
		return hognzhongcaishen;
	}

	public void setHognzhongcaishen(String hognzhongcaishen) {
		this.hognzhongcaishen = hognzhongcaishen;
	}

	public String getZhuaniao() {
		return zhuaniao;
	}

	public void setZhuaniao(String zhuaniao) {
		this.zhuaniao = zhuaniao;
	}

	public String getNiaoshu() {
		return niaoshu;
	}

	public void setNiaoshu(String niaoshu) {
		this.niaoshu = niaoshu;
	}

	public String getDapao() {
		return dapao;
	}

	public void setDapao(String dapao) {
		this.dapao = dapao;
	}

	public String getSipaofanbei() {
		return sipaofanbei;
	}

	public void setSipaofanbei(String sipaofanbei) {
		this.sipaofanbei = sipaofanbei;
	}

}
