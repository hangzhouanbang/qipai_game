package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.games.GameRoom;

public interface GameRoomDao {

	void save(GameRoom gameRoom);

	int count(long startTimeForCreate, long endTimeForCreate, String createMemberId, boolean vip);

}
