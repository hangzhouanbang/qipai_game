package com.anbang.qipai.game.plan.dao;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;

public interface GameRoomDao {

	void save(GameRoom gameRoom);

	int count(long startTimeForCreate, long endTimeForCreate, String createMemberId, boolean vip);

	GameRoom findRoomOpen(String roomNo);

	GameRoom findRoomByGameAndServerGameGameId(Game game, String serverGameId);

	List<GameRoom> findExpireGameRoom(long deadlineTime, boolean finished);

	void updateGameRoomFinished(List<String> ids, boolean finished);

	void updateFinishGameRoom(Game game, String serverGameId, boolean finished);

	void updateGameRoomCurrentPanNum(Game game, String serverGameId, int no);
}
