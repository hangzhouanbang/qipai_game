package com.anbang.qipai.game.msg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.WenzhouShuangkouGameRoomSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;

@EnableBinding(WenzhouShuangkouGameRoomSource.class)
public class WenzhouShuangkouGameRoomMsgService {
	@Autowired
	private WenzhouShuangkouGameRoomSource wenzhouShuangkouGameRoomSource;

	public void removeGameRoom(List<String> gameIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg("gameIds");
		mo.setData(gameIds);
		wenzhouShuangkouGameRoomSource.wenzhouShuangkouGameRoom().send(MessageBuilder.withPayload(mo).build());
	}
}
