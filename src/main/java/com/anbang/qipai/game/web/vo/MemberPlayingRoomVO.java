package com.anbang.qipai.game.web.vo;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;

public class MemberPlayingRoomVO {
	private String no;// 房间6位编号,可循环使用
	private Game game;
	private boolean vip;
	private int playersCount;
	private int panCountPerJu;
	private int currentPanNum;

	public MemberPlayingRoomVO(GameRoom gameRoom) {
		this.no = gameRoom.getNo();
		this.game = gameRoom.getGame();
		this.vip = gameRoom.isVip();
		this.playersCount = gameRoom.getPlayersCount();
		this.panCountPerJu = gameRoom.getPanCountPerJu();
		this.currentPanNum = gameRoom.getCurrentPanNum();
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

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public int getPlayersCount() {
		return playersCount;
	}

	public void setPlayersCount(int playersCount) {
		this.playersCount = playersCount;
	}

	public int getPanCountPerJu() {
		return panCountPerJu;
	}

	public void setPanCountPerJu(int panCountPerJu) {
		this.panCountPerJu = panCountPerJu;
	}

	public int getCurrentPanNum() {
		return currentPanNum;
	}

	public void setCurrentPanNum(int currentPanNum) {
		this.currentPanNum = currentPanNum;
	}

}
