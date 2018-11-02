package com.anbang.qipai.game.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalPanResult;
import com.anbang.qipai.game.plan.dao.MajiangHistoricalPanResultDao;
import com.highto.framework.web.page.ListPage;

@Service
public class MajiangHistoricalPanResultService {

	@Autowired
	private MajiangHistoricalPanResultDao majiangHistoricalPanResultDao;

	public void addMajiangHistoricalResult(MajiangHistoricalPanResult result) {
		majiangHistoricalPanResultDao.addMajiangHistoricalResult(result);
	}

	public ListPage findMajiangHistoricalResultByMemberId(int page, int size, String gameId, Game game) {
		long amount = majiangHistoricalPanResultDao.getAmountByGameIdAndGame(gameId, game);
		List<MajiangHistoricalPanResult> list = majiangHistoricalPanResultDao
				.findMajiangHistoricalResultByGameIdAndGame(page, size, gameId, game);
		ListPage listPage = new ListPage(list, page, size, (int) amount);
		return listPage;
	}
}
