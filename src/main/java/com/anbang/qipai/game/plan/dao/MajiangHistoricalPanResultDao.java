package com.anbang.qipai.game.plan.dao;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalPanResult;

public interface MajiangHistoricalPanResultDao {

	void addMajiangHistoricalResult(MajiangHistoricalPanResult result);

	List<MajiangHistoricalPanResult> findMajiangHistoricalResultByGameIdAndGame(int page, int size, String gameId,
			Game game);

	long getAmountByGameIdAndGame(String gameId, Game game);
}
