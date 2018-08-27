package com.anbang.qipai.game.plan.dao.mongodb;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.MemberGameRoom;
import com.anbang.qipai.game.plan.dao.MemberGameRoomDao;
import com.anbang.qipai.game.plan.dao.mongodb.repository.MemberGameRoomRepository;

@Component
public class MongodbMemberGameRoomDao implements MemberGameRoomDao {

	@Autowired
	private MemberGameRoomRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(MemberGameRoom memberGameRoom) {
		repository.save(memberGameRoom);
	}

	@Override
	public int count(String memberId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("memberId").is(memberId));
		query.addCriteria(criteria);
		return (int) mongoTemplate.count(query, MemberGameRoom.class);
	}

	@Override
	public MemberGameRoom findByMemberIdAndGameRoomId(String memberId, String gameRoomId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("memberId").is(memberId),
				Criteria.where("gameRoom.id").is(new ObjectId(gameRoomId)));
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, MemberGameRoom.class);
	}

	@Override
	public void remove(Game game, String serverGameId, String memberId) {
		repository.deleteByMemberIdAndGameRoomGameAndGameRoomServerGameGameId(memberId, game, serverGameId);
	}

	@Override
	public List<MemberGameRoom> findMemberGameRoomByMemberId(String memberId) {
		Query query = new Query(Criteria.where("memberId").is(memberId));
		return mongoTemplate.find(query, MemberGameRoom.class);
	}

	@Override
	public void removeExpireRoom(Game game, String serverGameId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("gameRoom.game").is(game));
		query.addCriteria(Criteria.where("gameRoom.serverGame.gameId").is(serverGameId));
		mongoTemplate.remove(query, MemberGameRoom.class);
	}

	@Override
	public void updateMemberGameRoomCurrentPanNum(Game game, String serverGameId, List<String> playerIds, int no) {
		Query query = new Query(Criteria.where("memberId").in(playerIds));
		query.addCriteria(Criteria.where("gameRoom.game").is(game)
				.andOperator(Criteria.where("gameRoom.serverGame.gameId").is(serverGameId)));
		Update update = new Update();
		update.set("gameRoom.currentPanNum", no);
		mongoTemplate.updateMulti(query, update, MemberGameRoom.class);
	}

}
