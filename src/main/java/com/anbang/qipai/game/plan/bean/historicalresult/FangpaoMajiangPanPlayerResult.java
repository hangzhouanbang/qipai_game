package com.anbang.qipai.game.plan.bean.historicalresult;

import java.util.Map;

public class FangpaoMajiangPanPlayerResult implements MajiangPanPlayerResult {
	private String id;// 玩家id
	private String nickname;// 玩家昵称
	private int score;// 一盘总分

	public FangpaoMajiangPanPlayerResult(Map panPlayerResult) {
		this.id = (String) panPlayerResult.get("id");
		this.nickname = (String) panPlayerResult.get("nickname");
		this.score = (int) panPlayerResult.get("score");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
