package com.anbang.qipai.game.cqrs.q.dbo;

import com.anbang.qipai.game.plan.bean.games.Game;

public class PlayBackDbo {
	private String id;// 回放码
	private String gameId;
	private Game game;
	private int panNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getPanNo() {
		return panNo;
	}

	public void setPanNo(int panNo) {
		this.panNo = panNo;
	}

}
