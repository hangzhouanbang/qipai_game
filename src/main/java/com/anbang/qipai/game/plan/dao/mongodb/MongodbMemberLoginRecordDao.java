package com.anbang.qipai.game.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.members.MemberLoginRecord;
import com.anbang.qipai.game.plan.dao.MemberLoginRecordDao;

@Component
public class MongodbMemberLoginRecordDao implements MemberLoginRecordDao {

	@Autowired
	private MongoTemplate mongTemplate;

	@Override
	public void save(MemberLoginRecord record) {
		mongTemplate.insert(record);
	}

	@Override
	public void updateOnlineTimeById(String id, long onlineTime) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("onlineTime", onlineTime);
		mongTemplate.updateFirst(query, update, MemberLoginRecord.class);
	}

	@Override
	public MemberLoginRecord findRecentRecordByMemberId(String memberId) {
		Query query = new Query(Criteria.where("memberId").is(memberId));
		// 需要建立索引
		Sort sort = new Sort(new Order(Direction.DESC, "loginTime"));
		query.with(sort);
		return mongTemplate.findOne(query, MemberLoginRecord.class);
	}

}
