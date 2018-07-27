package com.anbang.qipai.game.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

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
		return repository.findByMemberIdAndGameRoomId(memberId, gameRoomId);
	}

}
