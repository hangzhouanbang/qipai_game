package com.anbang.qipai.game.plan.dao.mongodb;

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
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalPanResult;
import com.anbang.qipai.game.plan.dao.MajiangHistoricalPanResultDao;

@Component
public class MongodbMajiangHistoricalPanResultDao implements MajiangHistoricalPanResultDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addMajiangHistoricalResult(MajiangHistoricalPanResult result) {
		mongoTemplate.insert(result);
	}

	@Override
	public List<MajiangHistoricalPanResult> findMajiangHistoricalResultByGameIdAndGame(int page, int size,
			String gameId, Game game) {
		Query query = new Query();
		query.addCriteria(Criteria.where("gameId").is(gameId));
		query.addCriteria(Criteria.where("game").is(game));
		query.limit(size);
		query.skip((page - 1) * size);
		query.with(new Sort(new Order(Direction.DESC, "no")));
		return mongoTemplate.find(query, MajiangHistoricalPanResult.class);
	}

	@Override
	public long getAmountByGameIdAndGame(String gameId, Game game) {
		Query query = new Query();
		query.addCriteria(Criteria.where("gameId").is(gameId));
		query.addCriteria(Criteria.where("game").is(game));
		return mongoTemplate.count(query, MajiangHistoricalPanResult.class);
	}

}
