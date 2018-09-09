package com.anbang.qipai.game.plan.dao;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalResult;

public interface MajiangHistoricalResultDao {

	void addMajiangHistoricalResult(MajiangHistoricalResult result);

	List<MajiangHistoricalResult> findMajiangHistoricalResultByMemberId(int page, int size, String memberId);

	long getAmountByMemberId(String memberId);

	int countGameNumByGameAndTime(Game game, long startTime, long endTime);

	MajiangHistoricalResult findMajiangHistoricalResultById(String id);
}
