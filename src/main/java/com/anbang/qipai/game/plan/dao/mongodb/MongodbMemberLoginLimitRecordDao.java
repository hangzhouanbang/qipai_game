package com.anbang.qipai.game.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.members.MemberLoginLimitRecord;
import com.anbang.qipai.game.plan.dao.MemberLoginLimitRecordDao;

@Component
public class MongodbMemberLoginLimitRecordDao implements MemberLoginLimitRecordDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(MemberLoginLimitRecord record) {
		mongoTemplate.insert(record);
	}

	@Override
	public void updateMemberLoginLimitRecordEfficientById(String[] ids, boolean efficient) {
		Object[] tempIds = ids;
		Query query = new Query(Criteria.where("id").in(tempIds));
		Update update = new Update();
		update.set("efficient", efficient);
		mongoTemplate.updateMulti(query, update, MemberLoginLimitRecord.class);
	}

	@Override
	public MemberLoginLimitRecord findByMemberId(String memberId, boolean efficient) {
		Query query = new Query();
		query.addCriteria(Criteria.where("memberId").is(memberId));
		query.addCriteria(Criteria.where("efficient").is(efficient));
		return mongoTemplate.findOne(query, MemberLoginLimitRecord.class);
	}

}
