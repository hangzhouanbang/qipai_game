package com.anbang.qipai.game.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.bean.members.MemberRights;
import com.anbang.qipai.game.plan.bean.members.MemberRightsConfiguration;
import com.anbang.qipai.game.plan.dao.MemberDao;
import com.anbang.qipai.game.plan.dao.mongodb.repository.MemberRepository;

@Component
public class MongodbMemberDao implements MemberDao {

	@Autowired
	private MemberRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Member findById(String id) {
		return repository.findOne(id);
	}

	@Override
	public void save(Member member) {
		repository.save(member);
	}

	@Override
	public void updateMemberGold(String memberId, int balanceAfter) {
		mongoTemplate.updateFirst(new Query(Criteria.where("id").is(memberId)), new Update().set("balanceAfter", balanceAfter),
				Member.class);	
	}
	
	@Override
	public void updateRights(String memberId, MemberRights rights) {
		mongoTemplate.updateFirst(new Query(Criteria.where("id").is(memberId)), new Update().set("rights", rights),
				Member.class);
	}

	@Override
	public void updatePlanMembersRights(int planMemberRoomsCount, int planMemberRoomsAliveHours,
			int planMemberMaxCreateRoomDaily, int planMemberCreateRoomDailyGoldPrice, int planMemberJoinRoomGoldPrice) {
		mongoTemplate.updateMulti(new Query(Criteria.where("vip").is(false)),
				new Update().set("rights.roomsCount", planMemberRoomsCount)
						.set("rights.roomsAliveHours", planMemberRoomsAliveHours)
						.set("rights.planMemberMaxCreateRoomDaily", planMemberMaxCreateRoomDaily)
						.set("rights.planMemberCreateRoomDailyGoldPrice", planMemberCreateRoomDailyGoldPrice)
						.set("rights.planMemberJoinRoomGoldPrice", planMemberJoinRoomGoldPrice),
				Member.class);
	}

	@Override
	public void updateVipMembersRights(int vipMemberRoomsCount, int vipMemberRoomsAliveHours) {
		mongoTemplate.updateMulti(new Query(Criteria.where("vip").is(true)), new Update()
				.set("rights.roomsCount", vipMemberRoomsCount).set("rights.roomsAliveHours", vipMemberRoomsAliveHours),
				Member.class);
	}

	@Override
	public MemberRightsConfiguration findMemberRightsById() {
		return mongoTemplate.findById("1", MemberRightsConfiguration.class);
	}

	@Override
	public void updateVIP(String memberId, boolean vip) {
		mongoTemplate.updateFirst(new Query(Criteria.where("id").is(memberId)), new Update().set("vip", vip),
				Member.class);
	}

}
