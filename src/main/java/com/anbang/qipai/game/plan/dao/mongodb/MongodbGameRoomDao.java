package com.anbang.qipai.game.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.dao.GameRoomDao;
import com.anbang.qipai.game.plan.dao.mongodb.repository.GameRoomRepository;

@Component
public class MongodbGameRoomDao implements GameRoomDao {

	@Autowired
	private GameRoomRepository repository;

	@Override
	public void save(GameRoom gameRoom) {
		repository.save(gameRoom);
	}

}
