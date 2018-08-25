package com.anbang.qipai.game.plan.dao;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.MemberGameRoom;

public interface MemberGameRoomDao {

	void save(MemberGameRoom memberGameRoom);

	int count(String memberId);

	MemberGameRoom findByMemberIdAndGameRoomId(String memberId, String gameRoomId);

	void remove(Game game, String serverGameId, String memberId);

	void removeExpireRoom(Game game, String serverGameId);

	List<MemberGameRoom> findMemberGameRoomByMemberId(String memberId);

	void updateMemberGameRoomCurrentPanNum(Game game, String serverGameId,List<String> playerIds, int no);
}
