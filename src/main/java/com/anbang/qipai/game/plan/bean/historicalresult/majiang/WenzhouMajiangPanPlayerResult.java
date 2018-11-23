package com.anbang.qipai.game.plan.bean.historicalresult.majiang;

import java.util.Map;

import com.anbang.qipai.game.plan.bean.historicalresult.GamePanPlayerResult;

public class WenzhouMajiangPanPlayerResult implements GamePanPlayerResult {
	private String playerId;// 玩家id
	private String nickname;// 玩家昵称
	private int score;// 一盘总分

	public WenzhouMajiangPanPlayerResult() {

	}

	public WenzhouMajiangPanPlayerResult(Map panPlayerResult) {
		this.playerId = (String) panPlayerResult.get("playerId");
		this.nickname = (String) panPlayerResult.get("nickname");
		this.score = ((Double) panPlayerResult.get("score")).intValue();
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
