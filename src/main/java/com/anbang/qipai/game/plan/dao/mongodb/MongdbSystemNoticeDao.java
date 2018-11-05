package com.anbang.qipai.game.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.conf.SystemNoticeState;
import com.anbang.qipai.game.plan.bean.notice.SystemNotice;
import com.anbang.qipai.game.plan.dao.SystemNoticeDao;

@Component
public class MongdbSystemNoticeDao implements SystemNoticeDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(SystemNotice notice) {
		mongoTemplate.insert(notice);
	}

	@Override
	public void updateSystemNoticeState(String noticeId, String adminName, String state) {
		Query query = new Query(Criteria.where("id").is(noticeId));
		Update update = new Update();
		update.set("adminName", adminName);
		update.set("state", state);
		mongoTemplate.updateFirst(query, update, SystemNotice.class);
	}

	@Override
	public void addSystemNotices(List<SystemNotice> notices) {
		mongoTemplate.insert(notices, SystemNotice.class);
	}

	@Override
	public SystemNotice findById(String noticeId) {
		Query query = new Query(Criteria.where("id").is(noticeId));
		return mongoTemplate.findOne(query, SystemNotice.class);
	}

	@Override
	public void updateSystemNoticeValid(String noticeId, String adminName, boolean valid) {
		Query query = new Query(Criteria.where("id").is(noticeId));
		Update update = new Update();
		update.set("adminName", adminName);
		update.set("valid", valid);
		mongoTemplate.updateFirst(query, update, SystemNotice.class);
	}

	@Override
	public List<SystemNotice> findByValid(boolean valid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("valid").is(valid));
		query.addCriteria(Criteria.where("state").is(SystemNoticeState.START));
		return mongoTemplate.find(query, SystemNotice.class);
	}

}
