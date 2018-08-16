package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.bean.members.MemberRights;
import com.anbang.qipai.game.plan.bean.members.MemberRightsConfiguration;

public interface MemberDao {

	Member findById(String id);

	void save(Member member);
	
	MemberRightsConfiguration findMemberRightsById();

	void updateRights(String memberId, MemberRights rights);

	void updatePlanMembersRights(int planMemberRoomsCount, int planMemberRoomsAliveHours, int planMemberMaxCreateRoomDaily,
			int planMemberCreateRoomDailyGoldPrice,int planMemberJoinRoomGoldPrice);

	void updateVipMembersRights(int vipMemberRoomsCount, int vipMemberRoomsAliveHours);

}
