package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.games.MemberGameRoom;

public interface MemberGameRoomDao {

	void save(MemberGameRoom memberGameRoom);

	int count(String memberId);

	MemberGameRoom findByMemberIdAndGameRoomId(String memberId, String gameRoomId);

}
