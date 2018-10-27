package com.anbang.qipai.game.msg.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.conf.MemberOnlineState;
import com.anbang.qipai.game.msg.channel.source.MemberLoginRecordSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.members.MemberLoginRecord;

@EnableBinding(MemberLoginRecordSource.class)
public class MemberLoginRecordMsgService {

	@Autowired
	private MemberLoginRecordSource memberLoginRecordSource;

	public void memberLoginRecord(MemberLoginRecord record) {
		CommonMO mo = new CommonMO();
		mo.setMsg("member login");
		Map data = new HashMap<>();
		data.put("record", record);
		data.put("onlineState", MemberOnlineState.ONLINE);
		mo.setData(data);
		memberLoginRecordSource.memberLoginRecord().send(MessageBuilder.withPayload(mo).build());
	}

	public void updateMemberOnlineRecord(MemberLoginRecord record) {
		CommonMO mo = new CommonMO();
		mo.setMsg("update member onlineTime");
		mo.setData(record);
		memberLoginRecordSource.memberLoginRecord().send(MessageBuilder.withPayload(mo).build());
	}

	public void memberLogout(String memberId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("member logout");
		Map data = new HashMap<>();
		data.put("memberId", memberId);
		data.put("onlineState", MemberOnlineState.OFFLINE);
		mo.setData(data);
		memberLoginRecordSource.memberLoginRecord().send(MessageBuilder.withPayload(mo).build());
	}
}
