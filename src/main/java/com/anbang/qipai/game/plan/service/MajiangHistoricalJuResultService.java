package com.anbang.qipai.game.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalJuResult;
import com.anbang.qipai.game.plan.dao.MajiangHistoricalJuResultDao;
import com.highto.framework.web.page.ListPage;

@Service
public class MajiangHistoricalJuResultService {

	@Autowired
	private MajiangHistoricalJuResultDao majiangHistoricalResultDao;

	public void addMajiangHistoricalResult(MajiangHistoricalJuResult result) {
		majiangHistoricalResultDao.addMajiangHistoricalResult(result);
	}

	public ListPage findMajiangHistoricalResultByMemberId(int page, int size, String memberId) {
		long amount = majiangHistoricalResultDao.getAmountByMemberId(memberId);
		List<MajiangHistoricalJuResult> list = majiangHistoricalResultDao.findMajiangHistoricalResultByMemberId(page,
				size, memberId);
		ListPage listPage = new ListPage(list, page, size, (int) amount);
		return listPage;
	}

	public MajiangHistoricalJuResult findMajiangHistoricalResultById(String id) {
		return majiangHistoricalResultDao.findMajiangHistoricalResultById(id);
	}

	public int countGameNumByGameAndTime(Game game, long startTime, long endTime) {
		return majiangHistoricalResultDao.countGameNumByGameAndTime(game, startTime, endTime);
	}
}
