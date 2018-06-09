package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.GameServerSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.games.GameServer;

@EnableBinding(GameServerSource.class)
public class GameServerMsgService {

	@Autowired
	private GameServerSource gameServerSource;

	public void gameServerOnline(GameServer gameServer) {
		CommonMO mo = new CommonMO();
		mo.setMsg("online");
		mo.setData(gameServer);
		gameServerSource.gameServer().send(MessageBuilder.withPayload(mo).build());
	}

	public void gameServerOffline(String gameServerId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("offline");
		mo.setData(gameServerId);
		gameServerSource.gameServer().send(MessageBuilder.withPayload(mo).build());
	}
}
