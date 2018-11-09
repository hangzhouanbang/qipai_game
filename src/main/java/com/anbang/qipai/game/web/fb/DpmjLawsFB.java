package com.anbang.qipai.game.web.fb;

import java.util.List;

/**
 * 点炮麻将规则
 * 
 * @author lsc
 *
 */
public class DpmjLawsFB {
	private String panshu;
	private String renshu;
	private String dianpao = "false";
	private String dapao = "false";
	private String quzhongfabai = "false";
	private String zhuaniao = "false";
	private String qingyise = "false";
	private String niaoshu = "4";

	public DpmjLawsFB(List<String> lawNames) {
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
			} else if (lawName.equals("dianpao")) {// 点炮
				dianpao = "true";
			}
			if (lawName.equals("dp")) {// 打炮
				dapao = "true";
			}
			if (lawName.equals("qzfb")) {// 去中发白
				quzhongfabai = "true";
			}
			if (lawName.equals("zn")) {// 抓鸟
				zhuaniao = "true";
			}
			if (lawName.equals("qingyise")) {// 清一色
				qingyise = "true";
			}
			if (lawName.equals("szn")) {// 四只鸟
				niaoshu = "4";
			} else if (lawName.equals("lzn")) {// 六只鸟
				niaoshu = "6";
			} else {

			}
		});
	}

	public String getQingyise() {
		return qingyise;
	}

	public void setQingyise(String qingyise) {
		this.qingyise = qingyise;
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

	public String getDianpao() {
		return dianpao;
	}

	public void setDianpao(String dianpao) {
		this.dianpao = dianpao;
	}

	public String getDapao() {
		return dapao;
	}

	public void setDapao(String dapao) {
		this.dapao = dapao;
	}

	public String getQuzhongfabai() {
		return quzhongfabai;
	}

	public void setQuzhongfabai(String quzhongfabai) {
		this.quzhongfabai = quzhongfabai;
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

}
