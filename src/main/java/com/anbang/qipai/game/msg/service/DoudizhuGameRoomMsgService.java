package com.anbang.qipai.game.msg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.DoudizhuGameRoomSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;

@EnableBinding(DoudizhuGameRoomSource.class)
public class DoudizhuGameRoomMsgService {
	@Autowired
	private DoudizhuGameRoomSource doudizhuGameRoomSource;

	public void removeGameRoom(List<String> gameIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg("gameIds");
		mo.setData(gameIds);
		doudizhuGameRoomSource.doudizhuGameRoom().send(MessageBuilder.withPayload(mo).build());
	}
}
