package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.games.GameServer;

public interface GameServerDao {

	void save(GameServer gameServer);

	void remove(String id);

}
