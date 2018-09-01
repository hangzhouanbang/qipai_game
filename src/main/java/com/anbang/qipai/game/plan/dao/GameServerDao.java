package com.anbang.qipai.game.plan.dao;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameServer;

public interface GameServerDao {

	void save(GameServer gameServer);

	void remove(String[] ids);

	List<GameServer> findAll();

	List<GameServer> findByGame(Game game);
}
