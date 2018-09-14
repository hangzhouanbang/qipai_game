package com.anbang.qipai.game.web.vo;

import com.anbang.qipai.game.plan.bean.games.Game;

public class MemberGameRoomVO {
	private String no;// 房间6位编号,可循环使用
	private Game game;// 游戏类型
	private int playersCount;// 人数
	private int currentPanNum;// 当前盘数
	private int panCountPerJu;// 总盘数
	private long remainTime;// 剩余时间

	public MemberGameRoomVO() {

	}

	public MemberGameRoomVO(String no, Game game, int playersCount, int currentPanNum, int panCountPerJu,
			long deadlineTime) {
		this.no = no;
		this.game = game;
		this.playersCount = playersCount;
		this.currentPanNum = currentPanNum;
		this.panCountPerJu = panCountPerJu;
		long delta = deadlineTime - System.currentTimeMillis();
		if (delta > 0) {
			this.remainTime = delta / 1000 / 3600;
		} else {
			this.remainTime = 0;
		}
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getPlayersCount() {
		return playersCount;
	}

	public void setPlayersCount(int playersCount) {
		this.playersCount = playersCount;
	}

	public int getCurrentPanNum() {
		return currentPanNum;
	}

	public void setCurrentPanNum(int currentPanNum) {
		this.currentPanNum = currentPanNum;
	}

	public int getPanCountPerJu() {
		return panCountPerJu;
	}

	public void setPanCountPerJu(int panCountPerJu) {
		this.panCountPerJu = panCountPerJu;
	}

	public long getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(long remainTime) {
		this.remainTime = remainTime;
	}

}
