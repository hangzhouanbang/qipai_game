package com.anbang.qipai.game.plan.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameLaw;

public interface GameLawRepository extends MongoRepository<GameLaw, String> {

	GameLaw findOneByGameAndName(Game game, String name);

}
