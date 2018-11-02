package com.anbang.qipai.game.cqrs.q.dao;

import com.anbang.qipai.game.cqrs.q.dbo.PlayBackDbo;

public interface PlayBackDboDao {

	void save(PlayBackDbo dbo);

	PlayBackDbo findById(String id);
}
