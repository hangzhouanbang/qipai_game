package com.anbang.qipai.game.msg.receiver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.msg.channel.sink.MemberGoldsSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.members.MemberGoldBalance;
import com.anbang.qipai.game.plan.service.MemberGoldBalanceService;
import com.google.gson.Gson;

@EnableBinding(MemberGoldsSink.class)
public class MemberGoldsMsgReceiver {

	@Autowired
	private MemberGoldBalanceService memberGoldBalanceService;

	private Gson gson = new Gson();

	@StreamListener(MemberGoldsSink.MEMBERGOLDS)
	public void recordMemberGoldRecordDbo(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("accounting".equals(msg)) {
			String accountId = (String) map.get("accountId");
			String memberId = (String) map.get("memberId");
			int balanceAfter = ((Double) map.get("balanceAfter")).intValue();
			MemberGoldBalance memberGoldBalance = new MemberGoldBalance();
			memberGoldBalance.setId(accountId);
			memberGoldBalance.setMemberId(memberId);
			memberGoldBalance.setBalanceAfter(balanceAfter);
			memberGoldBalanceService.save(memberGoldBalance);
		}
	}
}
