package com.anbang.qipai.game.plan.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.game.plan.bean.games.GameRoom;

public interface GameRoomRepository extends MongoRepository<GameRoom, String> {

	GameRoom findByNoAndFinished(String no, boolean finished);

}
