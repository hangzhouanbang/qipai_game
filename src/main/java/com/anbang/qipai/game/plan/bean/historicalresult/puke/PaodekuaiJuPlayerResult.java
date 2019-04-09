package com.anbang.qipai.game.plan.bean.historicalresult.puke;

import java.util.Map;

import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;

public class PaodekuaiJuPlayerResult implements GameJuPlayerResult {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private int danguanCount;
	private int shuangguanCount;
	private int boomCount;
	private int maxScore;
	private int totalScore;

	public PaodekuaiJuPlayerResult(Map juPlayerResult) {
		this.playerId = (String) juPlayerResult.get("playerId");
		this.nickname = (String) juPlayerResult.get("nickname");
		this.headimgurl = (String) juPlayerResult.get("headimgurl");
		this.danguanCount = ((Double) juPlayerResult.get("danguanCount")).intValue();
		this.shuangguanCount = ((Double) juPlayerResult.get("shuangguanCount")).intValue();
		this.boomCount = ((Double) juPlayerResult.get("boomCount")).intValue();
		this.maxScore = ((Double) juPlayerResult.get("maxScore")).intValue();
		this.totalScore = ((Double) juPlayerResult.get("totalScore")).intValue();
	}

	public PaodekuaiJuPlayerResult() {

	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getDanguanCount() {
		return danguanCount;
	}

	public void setDanguanCount(int danguanCount) {
		this.danguanCount = danguanCount;
	}

	public int getShuangguanCount() {
		return shuangguanCount;
	}

	public void setShuangguanCount(int shuangguanCount) {
		this.shuangguanCount = shuangguanCount;
	}

	public int getBoomCount() {
		return boomCount;
	}

	public void setBoomCount(int boomCount) {
		this.boomCount = boomCount;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}
}
