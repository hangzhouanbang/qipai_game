package com.anbang.qipai.game.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalResult;
import com.anbang.qipai.game.plan.dao.MajiangHistoricalResultDao;
import com.highto.framework.web.page.ListPage;

@Service
public class MajiangHistoricalResultService {

	@Autowired
	private MajiangHistoricalResultDao majiangHistoricalResultDao;

	public void addMajiangHistoricalResult(MajiangHistoricalResult result) {
		majiangHistoricalResultDao.addMajiangHistoricalResult(result);
	}

	public ListPage findMajiangHistoricalResultByMemberId(int page, int size, String memberId) {
		long amount = majiangHistoricalResultDao.getAmountByMemberId(memberId);
		List<MajiangHistoricalResult> list = majiangHistoricalResultDao.findMajiangHistoricalResultByMemberId(page,
				size, memberId);
		ListPage listPage = new ListPage(list, page, size, (int) amount);
		return listPage;
	}

	public MajiangHistoricalResult findMajiangHistoricalResultById(String id) {
		return majiangHistoricalResultDao.findMajiangHistoricalResultById(id);
	}

	public int countGameNumByGameAndTime(Game game, long startTime, long endTime) {
		return majiangHistoricalResultDao.countGameNumByGameAndTime(game, startTime, endTime);
	}
}
