package com.anbang.qipai.game.plan.dao;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;

public interface GameHistoricalJuResultDao {

	void addGameHistoricalResult(GameHistoricalJuResult result);

	List<GameHistoricalJuResult> findGameHistoricalResultByMemberId(int page, int size, String memberId);

	long getAmountByMemberId(String memberId);

	int countGameNumByGameAndTime(Game game, long startTime, long endTime);

	GameHistoricalJuResult findGameHistoricalResultById(String id);
}
