package com.anbang.qipai.game.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.bean.members.MemberRights;
import com.anbang.qipai.game.plan.bean.members.MemberRightsConfiguration;
import com.anbang.qipai.game.plan.dao.MemberDao;
import com.anbang.qipai.game.plan.dao.MemberRightsConfigurationDao;

@Component
public class MemberService {

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MemberRightsConfigurationDao memberRightsConfigurationDao;

	public Member findMember(String memberId) {
		return memberDao.findById(memberId);
	}

	public void addMember(Member member) {
		memberDao.save(member);
	}
	
	public MemberRightsConfiguration findMemberRightsById() {
		return memberDao.findMemberRightsById();
	}

	public void setPlanMembersRights(int planmemberRoomsCount, int planMemberRoomsAliveHours, int planMemberMaxCreateRoomDaily,
			int planMemberCreateRoomDailyGoldPrice,int planMemberaddRoomDailyGoldPrice) {
		MemberRightsConfiguration mrc = memberRightsConfigurationDao.find();
		if (mrc == null) {
			mrc = new MemberRightsConfiguration();
			mrc.setId("1");
			mrc.setPlanMemberRoomsAliveHours(planMemberRoomsAliveHours);
			mrc.setPlanMemberRoomsCount(planmemberRoomsCount);
			mrc.setPlanMemberMaxCreateRoomDaily(planMemberMaxCreateRoomDaily);
			mrc.setPlanMemberCreateRoomDailyGoldPrice(planMemberCreateRoomDailyGoldPrice);
			mrc.setPlanMemberaddRoomDailyGoldPrice(planMemberaddRoomDailyGoldPrice);
			memberRightsConfigurationDao.save(mrc);
		} else {
			memberRightsConfigurationDao.setPlanMembersRights(planmemberRoomsCount, planMemberRoomsAliveHours);
		}
		memberDao.updatePlanMembersRights(planmemberRoomsCount, planMemberRoomsAliveHours, planMemberMaxCreateRoomDaily,
				planMemberCreateRoomDailyGoldPrice,planMemberaddRoomDailyGoldPrice);
	}

	public void setVipMembersRights(int vipMemberRoomsCount, int vipMemberRoomsAliveHours) {
		MemberRightsConfiguration mrc = memberRightsConfigurationDao.find();
		if (mrc == null) {
			mrc = new MemberRightsConfiguration();
			mrc.setId("1");
			mrc.setVipMemberRoomsAliveHours(vipMemberRoomsAliveHours);
			mrc.setVipMemberRoomsCount(vipMemberRoomsCount);
			memberRightsConfigurationDao.save(mrc);
		} else {
			memberRightsConfigurationDao.setVipMembersRights(vipMemberRoomsCount, vipMemberRoomsAliveHours);
		}
		memberDao.updateVipMembersRights(vipMemberRoomsCount, vipMemberRoomsAliveHours);
	}

	public void updateMemberRights(String memberId) {
		MemberRightsConfiguration mrc = memberRightsConfigurationDao.find();
		if (mrc != null) {
			MemberRights rights = new MemberRights();
			Member member = memberDao.findById(memberId);
			if (member.isVip()) {
				rights.setRoomsCount(mrc.getVipMemberRoomsCount());
				rights.setRoomsAliveHours(mrc.getVipMemberRoomsAliveHours());
			} else {
				rights.setRoomsCount(mrc.getPlanMemberRoomsCount());
				rights.setRoomsAliveHours(mrc.getPlanMemberRoomsAliveHours());
				rights.setPlanMemberCreateRoomDailyGoldPrice(mrc.getPlanMemberCreateRoomDailyGoldPrice());
				rights.setPlanMemberMaxCreateRoomDaily(mrc.getPlanMemberMaxCreateRoomDaily());
				rights.setPlanMemberCreateRoomDailyGoldPrice(mrc.getPlanMemberCreateRoomDailyGoldPrice());
				rights.setPlanMemberaddRoomDailyGoldPrice(mrc.getPlanMemberaddRoomDailyGoldPrice());
			}
			memberDao.updateRights(memberId, rights);
		}
	}

}
