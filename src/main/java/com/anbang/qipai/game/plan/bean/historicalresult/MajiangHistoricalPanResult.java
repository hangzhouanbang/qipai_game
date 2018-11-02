package com.anbang.qipai.game.plan.bean.historicalresult;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.Game;

public class MajiangHistoricalPanResult {
	private String id;
	private String gameId;
	private Game game;
	private int no;// 盘数
	private long finishTime;// 完成时间
	private List<MajiangPanPlayerResult> playerResultList;

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

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public List<MajiangPanPlayerResult> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<MajiangPanPlayerResult> playerResultList) {
		this.playerResultList = playerResultList;
	}

}
