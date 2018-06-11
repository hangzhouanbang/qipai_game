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

}
