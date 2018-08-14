package com.anbang.qipai.game.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.dao.GameServerDao;
import com.anbang.qipai.game.plan.dao.mongodb.repository.GameServerRepository;

@Component
public class MongodbGameServerDao implements GameServerDao {

	@Autowired
	private MongoTemplate mognoTempalte;

	@Autowired
	private GameServerRepository repository;

	@Override
	public void save(GameServer gameServer) {
		mognoTempalte.insert(gameServer);
	}

	@Override
	public void remove(String[] ids) {
		Object[] serverIds = ids;
		Query query = new Query(Criteria.where("id").in(serverIds));
		mognoTempalte.remove(query, GameServer.class);
	}

	@Override
	public List<GameServer> findAll() {
		return repository.findAll();
	}

}
