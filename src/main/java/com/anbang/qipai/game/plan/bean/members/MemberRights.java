package com.anbang.qipai.game.plan.bean.members;

/**
 * 会员权益
 * 
 * @author Neo
 *
 */
public class MemberRights {

	/**
	 * 房间个数
	 */
	private int roomsCount;

	/**
	 * 房间存活小时数
	 */
	private int roomsAliveHours;

	public int getRoomsCount() {
		return roomsCount;
	}

	public void setRoomsCount(int roomsCount) {
		this.roomsCount = roomsCount;
	}

	public int getRoomsAliveHours() {
		return roomsAliveHours;
	}

	public void setRoomsAliveHours(int roomsAliveHours) {
		this.roomsAliveHours = roomsAliveHours;
	}

}
