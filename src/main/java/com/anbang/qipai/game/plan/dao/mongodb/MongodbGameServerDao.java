package com.anbang.qipai.game.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.dao.GameServerDao;
import com.anbang.qipai.game.plan.dao.mongodb.repository.GameServerRepository;

@Component
public class MongodbGameServerDao implements GameServerDao {

	@Autowired
	private GameServerRepository repository;

	@Override
	public void save(GameServer gameServer) {
		repository.save(gameServer);
	}

	@Override
	public void remove(String id) {
		repository.delete(id);
	}

	@Override
	public List<GameServer> findAll() {
		return repository.findAll();
	}

}
