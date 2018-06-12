package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.bean.members.MemberRights;

public interface MemberDao {

	Member findById(String id);

	void save(Member member);

	void updateRights(String memberId, MemberRights rights);

	void updatePlanMembersRights(int memberRoomsCount, int memberRoomsAliveHours, int planMemberMaxCreateRoomDaily,
			int planMemberCreateRoomDailyGoldPrice);

	void updateVipMembersRights(int memberRoomsCount, int memberRoomsAliveHours);

}
