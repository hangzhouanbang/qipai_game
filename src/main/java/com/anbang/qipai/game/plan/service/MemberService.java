package com.anbang.qipai.game.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.dao.MemberDao;

@Component
public class MemberService {

	@Autowired
	private MemberDao memberDao;

	public Member findMember(String memberId) {
		return memberDao.findById(memberId);
	}
}
