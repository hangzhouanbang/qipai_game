package com.anbang.qipai.game.plan.dao;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.GameRoom;

public interface GameRoomDao {

	void save(GameRoom gameRoom);

	int count(long startTimeForCreate, long endTimeForCreate, String createMemberId, boolean vip);

	GameRoom findRoomOpen(String roomNo);

	List<GameRoom> findExpireGameRoom(long deadlineTime, boolean finished);

	void updateGameRoomFinished(List<String> ids,boolean finished);
}
