package com.anbang.qipai.game.plan.bean.historicalresult;

import java.util.Map;

public class FangpaoMajiangJuPlayerResult implements MajiangJuPlayerResult {

	private String playerId;
	private String nickname;
	private String headimgurl;
	private int huCount;
	private int caishenCount;
	private int gangCount;
	private int paoCount;
	private int niaoCount;
	private int maxHushu;
	private int totalScore;

	public FangpaoMajiangJuPlayerResult(Map juPlayerResult) {
		this.playerId = (String) juPlayerResult.get("playerId");
		this.nickname = (String) juPlayerResult.get("nickname");
		this.headimgurl = (String) juPlayerResult.get("headimgurl");
		this.huCount = ((Double) juPlayerResult.get("huCount")).intValue();
		this.caishenCount = ((Double) juPlayerResult.get("caishenCount")).intValue();
		this.gangCount = ((Double) juPlayerResult.get("gangCount")).intValue();
		this.paoCount = ((Double) juPlayerResult.get("paoCount")).intValue();
		this.niaoCount = ((Double) juPlayerResult.get("niaoCount")).intValue();
		this.maxHushu = ((Double) juPlayerResult.get("maxHushu")).intValue();
		this.totalScore = ((Double) juPlayerResult.get("totalScore")).intValue();
	}

	public FangpaoMajiangJuPlayerResult() {

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

	public int getGangCount() {
		return gangCount;
	}

	public void setGangCount(int gangCount) {
		this.gangCount = gangCount;
	}

	public int getPaoCount() {
		return paoCount;
	}

	public void setPaoCount(int paoCount) {
		this.paoCount = paoCount;
	}

	public int getNiaoCount() {
		return niaoCount;
	}

	public void setNiaoCount(int niaoCount) {
		this.niaoCount = niaoCount;
	}

	public int getMaxHushu() {
		return maxHushu;
	}

	public void setMaxHushu(int maxHushu) {
		this.maxHushu = maxHushu;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
}
