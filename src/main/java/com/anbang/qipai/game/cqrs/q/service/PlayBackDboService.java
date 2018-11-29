package com.anbang.qipai.game.cqrs.q.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.game.cqrs.q.dao.PlayBackDboDao;
import com.anbang.qipai.game.cqrs.q.dbo.PlayBackDbo;
import com.anbang.qipai.game.plan.bean.games.Game;

@Service
public class PlayBackDboService {

	@Autowired
	private PlayBackDboDao playBackDboDao;

	public void save(PlayBackDbo dbo) {
		playBackDboDao.save(dbo);
	}

	public PlayBackDbo findById(String id) {
		return playBackDboDao.findById(id);
	}

	public PlayBackDbo findByGameAndGameIdAndPanNo(Game game, String gameId, int panNo) {
		return playBackDboDao.findByGameAndGameIdAndPanNo(game, gameId, panNo);
	}
}
