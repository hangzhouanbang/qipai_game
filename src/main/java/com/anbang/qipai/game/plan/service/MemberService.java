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

	public void setPlanMembersRights(int memberRoomsCount, int memberRoomsAliveHours) {
		MemberRightsConfiguration mrc = memberRightsConfigurationDao.find();
		if (mrc == null) {
			mrc = new MemberRightsConfiguration();
			mrc.setId("1");
			mrc.setPlanMemberRoomsAliveHours(memberRoomsAliveHours);
			mrc.setPlanMemberRoomsCount(memberRoomsCount);
			memberRightsConfigurationDao.save(mrc);
		} else {
			memberRightsConfigurationDao.setPlanMembersRights(memberRoomsCount, memberRoomsAliveHours);
		}
		memberDao.updatePlanMembersRights(memberRoomsCount, memberRoomsAliveHours);
	}

	public void setVipMembersRights(int memberRoomsCount, int memberRoomsAliveHours) {
		MemberRightsConfiguration mrc = memberRightsConfigurationDao.find();
		if (mrc == null) {
			mrc = new MemberRightsConfiguration();
			mrc.setId("1");
			mrc.setVipMemberRoomsAliveHours(memberRoomsAliveHours);
			mrc.setVipMemberRoomsCount(memberRoomsCount);
			memberRightsConfigurationDao.save(mrc);
		} else {
			memberRightsConfigurationDao.setVipMembersRights(memberRoomsCount, memberRoomsAliveHours);
		}
		memberDao.updateVipMembersRights(memberRoomsCount, memberRoomsAliveHours);
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
			}
			memberDao.updateRights(memberId, rights);
		}
	}

}
