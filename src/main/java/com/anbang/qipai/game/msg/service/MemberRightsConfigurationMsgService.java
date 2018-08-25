package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.MemberRightsConfigurationSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.members.MemberRightsConfiguration;

@EnableBinding(MemberRightsConfigurationSource.class)
public class MemberRightsConfigurationMsgService {

	@Autowired
	private MemberRightsConfigurationSource memberRightsConfigurationSource;

	public void createMemberRights(MemberRightsConfiguration memberRightsConfiguration) {
		CommonMO mo = new CommonMO();
		mo.setMsg("qipai_game_conf");
		mo.setData(memberRightsConfiguration);
		memberRightsConfigurationSource.memberrightsconfiguration().send(MessageBuilder.withPayload(mo).build());
	}
}
