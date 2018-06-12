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

	/**
	 * 普通会员每日开房（vip房）上限
	 */
	private int planMemberMaxCreateRoomDaily;

	/**
	 * 普通会员每日开房（vip房）金币价格
	 */
	private int planMemberCreateRoomDailyGoldPrice;

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

	public int getPlanMemberMaxCreateRoomDaily() {
		return planMemberMaxCreateRoomDaily;
	}

	public void setPlanMemberMaxCreateRoomDaily(int planMemberMaxCreateRoomDaily) {
		this.planMemberMaxCreateRoomDaily = planMemberMaxCreateRoomDaily;
	}

	public int getPlanMemberCreateRoomDailyGoldPrice() {
		return planMemberCreateRoomDailyGoldPrice;
	}

	public void setPlanMemberCreateRoomDailyGoldPrice(int planMemberCreateRoomDailyGoldPrice) {
		this.planMemberCreateRoomDailyGoldPrice = planMemberCreateRoomDailyGoldPrice;
	}

}
