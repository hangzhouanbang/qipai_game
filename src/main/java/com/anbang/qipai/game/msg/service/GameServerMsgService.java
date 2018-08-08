package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.GameServerSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.games.GameLaw;
import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.bean.games.LawsMutexGroup;

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

	public void createGameLaw(GameLaw law) {
		CommonMO mo = new CommonMO();
		mo.setMsg("create gamelaw");
		mo.setData(law);
		gameServerSource.gameServer().send(MessageBuilder.withPayload(mo).build());
	}

	public void removeGameLaw(String lawId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("remove gamelaw");
		mo.setData(lawId);
		gameServerSource.gameServer().send(MessageBuilder.withPayload(mo).build());
	}

	public void addLawsMutexGroup(LawsMutexGroup lawsMutexGroup) {
		CommonMO mo = new CommonMO();
		mo.setMsg("create lawsmutexgroup");
		mo.setData(lawsMutexGroup);
		gameServerSource.gameServer().send(MessageBuilder.withPayload(mo).build());
	}

	public void removeLawsMutexGroup(String groupId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("remove lawsmutexgroup");
		mo.setData(groupId);
		gameServerSource.gameServer().send(MessageBuilder.withPayload(mo).build());
	}
}
