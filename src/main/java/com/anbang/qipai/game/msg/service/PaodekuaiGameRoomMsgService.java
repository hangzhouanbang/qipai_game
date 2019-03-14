package com.anbang.qipai.game.msg.service;

import java.util.List;

import com.anbang.qipai.game.msg.channel.source.PaodekuaiGameRoomSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.msjobj.CommonMO;

@EnableBinding(PaodekuaiGameRoomSource.class)
public class PaodekuaiGameRoomMsgService {
	@Autowired
	private PaodekuaiGameRoomSource paodekuaiGameRoomSource;

	public void removeGameRoom(List<String> gameIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg("gameIds");
		mo.setData(gameIds);
		paodekuaiGameRoomSource.paodekuaiGameRoom().send(MessageBuilder.withPayload(mo).build());
	}
}
