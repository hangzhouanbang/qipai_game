package com.anbang.qipai.game.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.dao.GameRoomDao;
import com.anbang.qipai.game.plan.dao.mongodb.repository.GameRoomRepository;

@Component
public class MongodbGameRoomDao implements GameRoomDao {

	@Autowired
	private GameRoomRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(GameRoom gameRoom) {
		repository.save(gameRoom);
	}

	@Override
	public int count(long startTimeForCreate, long endTimeForCreate, String createMemberId, boolean vip) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("createTime").gt(startTimeForCreate),
				Criteria.where("createTime").lt(endTimeForCreate), Criteria.where("createMemberId").is(createMemberId),
				Criteria.where("vip").is(vip));
		query.addCriteria(criteria);
		return (int) mongoTemplate.count(query, GameRoom.class);
	}

}
