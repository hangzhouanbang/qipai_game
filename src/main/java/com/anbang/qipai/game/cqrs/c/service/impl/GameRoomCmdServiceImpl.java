package com.anbang.qipai.game.cqrs.c.service.impl;

import org.springframework.stereotype.Component;

import com.anbang.qipai.game.cqrs.c.domain.games.GameRoomNoManager;
import com.anbang.qipai.game.cqrs.c.service.GameRoomCmdService;

@Component
public class GameRoomCmdServiceImpl extends CmdServiceBase implements GameRoomCmdService {

	@Override
	public String createRoom(String memberId, Long createTime) {
		GameRoomNoManager gameRoomNoManager = singletonEntityRepository.getEntity(GameRoomNoManager.class);
		String roomNo = gameRoomNoManager.newNo(createTime);
		return roomNo;
	}

	@Override
	public String removeRoom(String no) {
		GameRoomNoManager gameRoomNoManager = singletonEntityRepository.getEntity(GameRoomNoManager.class);
		return gameRoomNoManager.removeNo(no);
	}

}
