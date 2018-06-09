package com.anbang.qipai.game.plan.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.game.plan.bean.games.GameServer;

public interface GameServerRepository extends MongoRepository<GameServer, String> {

}
