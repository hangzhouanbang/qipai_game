package com.anbang.qipai.game.msg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.DianpaoGameRoomSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;

@EnableBinding(DianpaoGameRoomSource.class)
public class DianpaoGameRoomMsgService {
	@Autowired
	private DianpaoGameRoomSource dianpaoGameRoomSource;

	public void removeGameRoom(List<String> gameIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg("gameIds");
		mo.setData(gameIds);
		dianpaoGameRoomSource.dianpaoGameRoom().send(MessageBuilder.withPayload(mo).build());
	}
}
