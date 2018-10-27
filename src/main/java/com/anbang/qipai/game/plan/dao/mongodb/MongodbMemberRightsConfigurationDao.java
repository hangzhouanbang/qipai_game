package com.anbang.qipai.game.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.members.MemberRightsConfiguration;
import com.anbang.qipai.game.plan.dao.MemberRightsConfigurationDao;

@Component
public class MongodbMemberRightsConfigurationDao implements MemberRightsConfigurationDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public MemberRightsConfiguration find() {
		return mongoTemplate.findById("1", MemberRightsConfiguration.class);
	}

	@Override
	public void save(MemberRightsConfiguration mrc) {
		mongoTemplate.save(mrc);
	}

	@Override
	public void setPlanMembersRights(int memberRoomsCount, int memberRoomsAliveHours, int planMemberMaxCreateRoomDaily,
			int planMemberCreateRoomDailyGoldPrice, int planMemberJoinRoomGoldPrice) {
		mongoTemplate.updateFirst(new Query(Criteria.where("id").is("1")),
				new Update().set("planMemberRoomsCount", memberRoomsCount)
						.set("planMemberRoomsAliveHours", memberRoomsAliveHours)
						.set("planMemberMaxCreateRoomDaily", planMemberMaxCreateRoomDaily)
						.set("planMemberCreateRoomDailyGoldPrice", planMemberCreateRoomDailyGoldPrice)
						.set("planMemberJoinRoomGoldPrice", planMemberJoinRoomGoldPrice),
				MemberRightsConfiguration.class);
	}

	@Override
	public void setVipMembersRights(int memberRoomsCount, int memberRoomsAliveHours) {
		mongoTemplate.updateFirst(new Query(Criteria.where("id").is("1")), new Update()
				.set("vipMemberRoomsCount", memberRoomsCount).set("vipMemberRoomsAliveHours", memberRoomsAliveHours),
				MemberRightsConfiguration.class);
	}

}
