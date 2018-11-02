package com.anbang.qipai.game.cqrs.q.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.cqrs.q.dao.PlayBackDboDao;
import com.anbang.qipai.game.cqrs.q.dbo.PlayBackDbo;

@Component
public class MongodbPlayBackDboDao implements PlayBackDboDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(PlayBackDbo dbo) {
		mongoTemplate.insert(dbo);
	}

	@Override
	public PlayBackDbo findById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, PlayBackDbo.class);
	}

}
