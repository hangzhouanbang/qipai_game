package com.anbang.qipai.game.plan.bean.members;

/**
 * 会员权益配置
 * 
 * @author Neo
 *
 */
public class MemberRightsConfiguration {

	private String id;// 永远为1

	// 房间个数
	private int planMemberRoomsCount;
	private int vipMemberRoomsCount;

	// 房间存活小时数
	private int planMemberRoomsAliveHours;
	private int vipMemberRoomsAliveHours;

	// 普通会员每日开房(vip房)上限
	private int planMemberMaxCreateRoomDaily;

	// 普通会员每日开房(vip房)金币价格
	private int planMemberCreateRoomDailyGoldPrice;

	// 普通会员加入房间(vip房)金币价格
	private int planMemberJoinRoomGoldPrice;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPlanMemberRoomsCount() {
		return planMemberRoomsCount;
	}

	public void setPlanMemberRoomsCount(int planMemberRoomsCount) {
		this.planMemberRoomsCount = planMemberRoomsCount;
	}

	public int getVipMemberRoomsCount() {
		return vipMemberRoomsCount;
	}

	public void setVipMemberRoomsCount(int vipMemberRoomsCount) {
		this.vipMemberRoomsCount = vipMemberRoomsCount;
	}

	public int getPlanMemberRoomsAliveHours() {
		return planMemberRoomsAliveHours;
	}

	public void setPlanMemberRoomsAliveHours(int planMemberRoomsAliveHours) {
		this.planMemberRoomsAliveHours = planMemberRoomsAliveHours;
	}

	public int getVipMemberRoomsAliveHours() {
		return vipMemberRoomsAliveHours;
	}

	public void setVipMemberRoomsAliveHours(int vipMemberRoomsAliveHours) {
		this.vipMemberRoomsAliveHours = vipMemberRoomsAliveHours;
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

	public int getPlanMemberJoinRoomGoldPrice() {
		return planMemberJoinRoomGoldPrice;
	}

	public void setPlanMemberJoinRoomGoldPrice(int planMemberJoinRoomGoldPrice) {
		this.planMemberJoinRoomGoldPrice = planMemberJoinRoomGoldPrice;
	}

}
