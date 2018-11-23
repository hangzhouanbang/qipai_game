package com.anbang.qipai.game.plan.dao;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;

public interface GameHistoricalPanResultDao {

	void addGameHistoricalResult(GameHistoricalPanResult result);

	List<GameHistoricalPanResult> findGameHistoricalResultByGameIdAndGame(int page, int size, String gameId, Game game);

	long getAmountByGameIdAndGame(String gameId, Game game);
}
