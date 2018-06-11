package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.games.LawsMutexGroup;

public interface LawsMutexGroupDao {

	void save(LawsMutexGroup lawsMutexGroup);

	void remove(String id);

}
