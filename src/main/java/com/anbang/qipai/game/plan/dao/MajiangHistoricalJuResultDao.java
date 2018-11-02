package com.anbang.qipai.game.plan.dao;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalJuResult;

public interface MajiangHistoricalJuResultDao {

	void addMajiangHistoricalResult(MajiangHistoricalJuResult result);

	List<MajiangHistoricalJuResult> findMajiangHistoricalResultByMemberId(int page, int size, String memberId);

	long getAmountByMemberId(String memberId);

	int countGameNumByGameAndTime(Game game, long startTime, long endTime);

	MajiangHistoricalJuResult findMajiangHistoricalResultById(String id);
}
