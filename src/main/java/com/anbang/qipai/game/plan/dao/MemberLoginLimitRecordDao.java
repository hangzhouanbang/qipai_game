package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.members.MemberLoginLimitRecord;

public interface MemberLoginLimitRecordDao {

	void save(MemberLoginLimitRecord record);

	MemberLoginLimitRecord findByMemberId(String memberId, boolean efficient);

	void updateMemberLoginLimitRecordEfficientById(String[] ids, boolean efficient);

}
