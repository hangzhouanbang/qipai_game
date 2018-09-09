package com.anbang.qipai.game.plan.dao.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalResult;
import com.anbang.qipai.game.plan.dao.MajiangHistoricalResultDao;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;

@Component
public class MongodbMajiangHistoricalResultDao implements MajiangHistoricalResultDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addMajiangHistoricalResult(MajiangHistoricalResult result) {
		mongoTemplate.insert(result);
	}

	@Override
	public List<MajiangHistoricalResult> findMajiangHistoricalResultByMemberId(int page, int size, String memberId) {
		Query query = new Query(Criteria.where("playerResultList").elemMatch(Criteria.where("playerId").is(memberId)));
		Sort sort = new Sort(new Order(Direction.DESC, "id"));
		query.with(sort);
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, MajiangHistoricalResult.class);
	}

	@Override
	public long getAmountByMemberId(String memberId) {
		Query query = new Query(Criteria.where("playerResultList").elemMatch(Criteria.where("playerId").is(memberId)));
		return mongoTemplate.count(query, MajiangHistoricalResult.class);
	}

	@Override
	public MajiangHistoricalResult findMajiangHistoricalResultById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, MajiangHistoricalResult.class);
	}

	@Override
	public int countGameNumByGameAndTime(Game game, long startTime, long endTime) {
		List<DBObject> pipeline = new ArrayList<>();
		BasicDBObject match = new BasicDBObject();
		match.put("finishTime", new BasicDBObject("$gt", startTime).put("$lt", endTime));
		match.put("game", game);
		DBObject queryMatch = new BasicDBObject("$match", match);
		pipeline.add(queryMatch);

		BasicDBObject group = new BasicDBObject();
		group.put("_id", null);
		group.put("num", new BasicDBObject("$sum", "$lastPanNo"));
		DBObject queryGroup = new BasicDBObject("$group", group);
		pipeline.add(queryGroup);
		Cursor cursor = mongoTemplate.getCollection("memberLoginRecord").aggregate(pipeline,
				AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
		if (cursor == null) {
			return 0;
		} else {
			return (int) cursor.next().get("num");
		}
	}

}
