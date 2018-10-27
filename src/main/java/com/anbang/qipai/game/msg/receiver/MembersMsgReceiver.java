package com.anbang.qipai.game.msg.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.msg.channel.sink.MembersSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.service.MemberService;
import com.google.gson.Gson;

@EnableBinding(MembersSink.class)
public class MembersMsgReceiver {

	@Autowired
	private MemberService memberService;

	private Gson gson = new Gson();

	@StreamListener(MembersSink.MEMBERS)
	public void addMember(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Member member = gson.fromJson(json, Member.class);
		if ("newMember".equals(msg)) {
			memberService.addMember(member);
			// 更新会员权益
			memberService.updateMemberRights(member.getId());
		}
		if ("memberOrder delive".equals(msg) || "recharge vip".equals(msg) || "update member vip".equals(msg)) {
			memberService.updateMemberVip(member.getId(), member.isVip());
			// 更新会员权益
			memberService.updateMemberRights(member.getId());
		}
	}

}
