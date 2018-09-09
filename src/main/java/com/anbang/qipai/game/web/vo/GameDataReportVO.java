package com.anbang.qipai.game.web.vo;

import com.anbang.qipai.game.plan.bean.games.Game;

public class GameDataReportVO {
	private String id;
	private Game game;
	private long date;// 日期
	private int currentMember;// 进入游戏的当日会员人数
	private int gameNum;// 游戏总局数
	private int loginMember;// 独立玩家

	public GameDataReportVO() {
	}

	public GameDataReportVO(Game game, long date, int currentMember, int gameNum, int loginMember) {
		this.game = game;
		this.date = date;
		this.currentMember = currentMember;
		this.gameNum = gameNum;
		this.loginMember = loginMember;
	}

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

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getCurrentMember() {
		return currentMember;
	}

	public void setCurrentMember(int currentMember) {
		this.currentMember = currentMember;
	}

	public int getGameNum() {
		return gameNum;
	}

	public void setGameNum(int gameNum) {
		this.gameNum = gameNum;
	}

	public int getLoginMember() {
		return loginMember;
	}

	public void setLoginMember(int loginMember) {
		this.loginMember = loginMember;
	}

}
