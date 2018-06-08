package com.anbang.qipai.game.plan.bean.games;

/**
 * 游戏服务器房间相关信息
 * 
 * @author Neo
 *
 */
public class GameServerRoom {
	private String wsUrl;// websocket
	private String roomId;// 游戏服务器那边的一个房间id,可能那边叫局id,因为那边可能没有房间概念

	public String getWsUrl() {
		return wsUrl;
	}

	public void setWsUrl(String wsUrl) {
		this.wsUrl = wsUrl;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
