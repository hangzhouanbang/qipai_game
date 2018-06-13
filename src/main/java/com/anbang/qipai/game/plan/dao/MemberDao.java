package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.bean.members.MemberRights;
import com.anbang.qipai.game.plan.bean.members.MemberRightsConfiguration;

public interface MemberDao {

	Member findById(String id);

	void save(Member member);
	
	MemberRightsConfiguration findMemberRightsById();

	void updateRights(String memberId, MemberRights rights);

	void updatePlanMembersRights(int memberRoomsCount, int memberRoomsAliveHours, int planMemberMaxCreateRoomDaily,
			int planMemberCreateRoomDailyGoldPrice,int planMemberaddRoomDailyGoldPrice);

	void updateVipMembersRights(int memberRoomsCount, int memberRoomsAliveHours);

}
