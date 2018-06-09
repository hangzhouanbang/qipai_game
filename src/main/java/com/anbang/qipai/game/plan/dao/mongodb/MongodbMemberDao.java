package com.anbang.qipai.game.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.dao.MemberDao;

@Component
public class MongodbMemberDao implements MemberDao{
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Member findById(String id) {
		return mongoTemplate.findById(id, Member.class);
	}
}
