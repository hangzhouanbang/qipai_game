package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.members.MemberRightsConfiguration;

public interface MemberRightsConfigurationDao {

	MemberRightsConfiguration find();

	void save(MemberRightsConfiguration mrc);

	void setPlanMembersRights(int memberRoomsCount, int memberRoomsAliveHours, int planMemberMaxCreateRoomDaily,
			int planMemberCreateRoomDailyGoldPrice, int planMemberJoinRoomGoldPrice);

	void setVipMembersRights(int memberRoomsCount, int memberRoomsAliveHours);

}
