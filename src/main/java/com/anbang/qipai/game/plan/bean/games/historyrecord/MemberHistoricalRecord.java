package com.anbang.qipai.game.plan.bean.games.historyrecord;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.Game;

public class MemberHistoricalRecord {

	private String id;
	
	private String memberId;//会员id
	
	private Game game;//游戏名称
	
	private long endTime;//结束时间
	
	private List<RuianHistoricalRecord> ruian;//瑞安麻将多个用户战绩信息
	
	private List<WenZhouHistoricalRecord> wenzhou;//温州麻将多个用户战绩信息
	
	private List<DianPaoHistoricalRecord> dianpao;//点炮麻将多个用户战绩信息
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public List<RuianHistoricalRecord> getRuian() {
		return ruian;
	}

	public void setRuian(List<RuianHistoricalRecord> ruian) {
		this.ruian = ruian;
	}

	public List<WenZhouHistoricalRecord> getWenzhou() {
		return wenzhou;
	}

	public void setWenzhou(List<WenZhouHistoricalRecord> wenzhou) {
		this.wenzhou = wenzhou;
	}

	public List<DianPaoHistoricalRecord> getDianpao() {
		return dianpao;
	}

	public void setDianpao(List<DianPaoHistoricalRecord> dianpao) {
		this.dianpao = dianpao;
	}


	
	
}
