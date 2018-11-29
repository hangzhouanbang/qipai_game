package com.anbang.qipai.game.cqrs.q.dao;

import com.anbang.qipai.game.cqrs.q.dbo.PlayBackDbo;
import com.anbang.qipai.game.plan.bean.games.Game;

public interface PlayBackDboDao {

	void save(PlayBackDbo dbo);

	PlayBackDbo findById(String id);

	PlayBackDbo findByGameAndGameIdAndPanNo(Game game, String gameId, int panNo);
}
