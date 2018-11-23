package com.anbang.qipai.game.plan.bean.historicalresult.majiang;

import java.util.Map;

import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;

public class WenzhouMajiangJuPlayerResult implements GameJuPlayerResult {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private int huCount;
	private int caishenCount;
	private int shuangfanCount;
	private int maxScore;
	private int totalScore;

	public WenzhouMajiangJuPlayerResult(Map juPlayerResult) {
		this.playerId = (String) juPlayerResult.get("playerId");
		this.nickname = (String) juPlayerResult.get("nickname");
		this.headimgurl = (String) juPlayerResult.get("headimgurl");
		this.huCount = ((Double) juPlayerResult.get("huCount")).intValue();
		this.caishenCount = ((Double) juPlayerResult.get("caishenCount")).intValue();
		this.shuangfanCount = ((Double) juPlayerResult.get("shuangfanCount")).intValue();
		this.maxScore = ((Double) juPlayerResult.get("maxScore")).intValue();
		this.totalScore = ((Double) juPlayerResult.get("totalScore")).intValue();
	}

	public WenzhouMajiangJuPlayerResult() {

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

	public int getHuCount() {
		return huCount;
	}

	public void setHuCount(int huCount) {
		this.huCount = huCount;
	}

	public int getCaishenCount() {
		return caishenCount;
	}

	public void setCaishenCount(int caishenCount) {
		this.caishenCount = caishenCount;
	}

	public int getShuangfanCount() {
		return shuangfanCount;
	}

	public void setShuangfanCount(int shuangfanCount) {
		this.shuangfanCount = shuangfanCount;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
}
