package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.members.MemberLoginRecord;

public interface MemberLoginRecordDao {

	void save(MemberLoginRecord record);

	void updateOnlineTimeById(String id, long onlineTime);

	MemberLoginRecord findRecentRecordByMemberId(String memberId);
}
