package com.anbang.qipai.game.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameLaw;
import com.anbang.qipai.game.plan.dao.GameLawDao;
import com.anbang.qipai.game.plan.dao.mongodb.repository.GameLawRepository;

@Component
public class MongodbGameLawDao implements GameLawDao {

	@Autowired
	private GameLawRepository repository;

	@Override
	public GameLaw findByGameAndName(Game game, String name) {
		return repository.findOneByGameAndName(game, name);
	}

	@Override
	public void save(GameLaw law) {
		repository.save(law);
	}

	@Override
	public void remove(String id) {
		repository.delete(id);
	}

}
