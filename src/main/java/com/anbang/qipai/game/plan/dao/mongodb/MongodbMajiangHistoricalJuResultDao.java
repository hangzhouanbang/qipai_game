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
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalJuResult;
import com.anbang.qipai.game.plan.dao.MajiangHistoricalJuResultDao;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;

@Component
public class MongodbMajiangHistoricalJuResultDao implements MajiangHistoricalJuResultDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addMajiangHistoricalResult(MajiangHistoricalJuResult result) {
		mongoTemplate.insert(result);
	}

	@Override
	public List<MajiangHistoricalJuResult> findMajiangHistoricalResultByMemberId(int page, int size, String memberId) {
		Query query = new Query(Criteria.where("playerResultList").elemMatch(Criteria.where("playerId").is(memberId)));
		Sort sort = new Sort(new Order(Direction.DESC, "id"));
		query.with(sort);
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, MajiangHistoricalJuResult.class);
	}

	@Override
	public long getAmountByMemberId(String memberId) {
		Query query = new Query(Criteria.where("playerResultList").elemMatch(Criteria.where("playerId").is(memberId)));
		return mongoTemplate.count(query, MajiangHistoricalJuResult.class);
	}

	@Override
	public MajiangHistoricalJuResult findMajiangHistoricalResultById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, MajiangHistoricalJuResult.class);
	}

	@Override
	public int countGameNumByGameAndTime(Game game, long startTime, long endTime) {
		List<DBObject> pipeline = new ArrayList<>();
		BasicDBObject match = new BasicDBObject();
		BasicDBObject criteria = new BasicDBObject("$gt", startTime);
		criteria.put("$lt", endTime);
		match.put("finishTime", criteria);
		match.put("game", game.name());
		DBObject queryMatch = new BasicDBObject("$match", match);
		pipeline.add(queryMatch);

		BasicDBObject group = new BasicDBObject();
		group.put("_id", null);
		group.put("num", new BasicDBObject("$sum", "$lastPanNo"));
		DBObject queryGroup = new BasicDBObject("$group", group);
		pipeline.add(queryGroup);
		Cursor cursor = mongoTemplate.getCollection("majiangHistoricalResult").aggregate(pipeline,
				AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
		try {
			return (int) cursor.next().get("num");
		} catch (Exception e) {
			return 0;
		}
	}

}
