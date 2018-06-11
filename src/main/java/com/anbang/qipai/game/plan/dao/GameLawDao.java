package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameLaw;

public interface GameLawDao {

	GameLaw findByGameAndName(Game game, String name);

	void save(GameLaw law);

	void remove(String id);

}
