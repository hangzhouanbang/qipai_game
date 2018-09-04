package com.anbang.qipai.game.msg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.FangpaoGameRoomSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;

@EnableBinding(FangpaoGameRoomSource.class)
public class FangpaoGameRoomMsgService {
	@Autowired
	private FangpaoGameRoomSource fangpaoGameRoomSource;

	public void removeGameRoom(List<String> gameIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg("gameIds");
		mo.setData(gameIds);
		fangpaoGameRoomSource.fangpaoGameRoom().send(MessageBuilder.withPayload(mo).build());
	}
}
