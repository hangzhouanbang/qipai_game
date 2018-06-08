package com.anbang.qipai.game.plan.bean.games;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 游戏房间
 * 
 * @author Neo
 *
 */
public class GameRoom {
	private String id;
	private String no;// 房间6位编号,可循环使用
	private Game game;
	private List<GameLaw> laws;
	private boolean vip;
	private int playersCount;
	private int panCountPerJu;
	private GameServerRoom serverRoom;
	private int currentPanNum;
	private long deadlineTime;

	public void calculateVip() {
		if (laws != null) {
			for (GameLaw law : laws) {
				if (law.isVip()) {
					vip = true;
					return;
				}
			}
		}
		vip = false;
	}

	public boolean validateLaws() {
		if (laws != null) {
			Set<String> groupIdSet = new HashSet<>();
			for (GameLaw law : laws) {
				String groupId = law.getMutexGroupId();
				if (groupId != null) {
					if (!groupIdSet.add(groupId)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<GameLaw> getLaws() {
		return laws;
	}

	public void setLaws(List<GameLaw> laws) {
		this.laws = laws;
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

	public GameServerRoom getServerRoom() {
		return serverRoom;
	}

	public void setServerRoom(GameServerRoom serverRoom) {
		this.serverRoom = serverRoom;
	}

	public int getCurrentPanNum() {
		return currentPanNum;
	}

	public void setCurrentPanNum(int currentPanNum) {
		this.currentPanNum = currentPanNum;
	}

	public long getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(long deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

}
