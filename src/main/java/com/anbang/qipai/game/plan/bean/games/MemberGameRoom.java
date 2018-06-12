package com.anbang.qipai.game.plan.bean.games;

/**
 * 会员加入的游戏房间
 * 
 * @author Neo
 *
 */
public class MemberGameRoom {
	private String id;
	private String memberId;
	private GameRoom gameRoom;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}

}
