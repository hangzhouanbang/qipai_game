package com.anbang.qipai.game.msg.receiver;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import com.anbang.qipai.game.msg.channel.sink.MemberGoldsSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.service.MemberService;
import com.google.gson.Gson;

@EnableBinding(MemberGoldsSink.class)
public class MemberGoldsMsgReceiver {

	@Autowired
	private MemberService memberService;

	private Gson gson = new Gson();

	@StreamListener(MemberGoldsSink.MEMBERGOLDS)
	public void recordMemberGoldRecordDbo(CommonMO mo) {
		Map<String, Object> map = (Map<String, Object>) mo.getData();
		if ("accounting".equals(mo.getMsg())) {
			Member dbo = new Member();
			dbo.setId((String) map.get("memberId"));
			dbo.setBalanceAfter((int) map.get("balanceAfter"));
			memberService.updateMemberGold(dbo.getId(), dbo.getBalanceAfter());
		}
	}
}
