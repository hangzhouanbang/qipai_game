package com.anbang.qipai.game.msg.receiver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.msg.channel.MembersSink;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.service.MemberService;
import com.google.gson.Gson;

@EnableBinding(MembersSink.class)
public class MembersMsgReceiver {

	@Autowired
	private MemberService memberService;

	private Gson gson = new Gson();

	@StreamListener(MembersSink.MEMBERS)
	public void addMember(Object payload) {
		Map map = gson.fromJson(payload.toString(), Map.class);
		String msg = (String) map.get("msg");
		if (msg.equals("newMember")) {
			Map memberMap = (Map) map.get("data");
			Member member = gson.fromJson(gson.toJson(memberMap), Member.class);
			memberService.addMember(member);
			// 更新会员权益
			memberService.updateMemberRights(member.getId());
		}
	}

}
