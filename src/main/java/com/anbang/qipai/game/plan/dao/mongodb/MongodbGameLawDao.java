package com.anbang.qipai.game.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameLaw;
import com.anbang.qipai.game.plan.dao.GameLawDao;

@Component
public class MongodbGameLawDao implements GameLawDao{
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public GameLaw findByGameAndName(Game game, String name) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("Game").is(game),Criteria.where("name").is(name));
		GameLaw gamelaw = mongoTemplate.findOne(new Query(criteria), GameLaw.class);
		return gamelaw;
	}

}
