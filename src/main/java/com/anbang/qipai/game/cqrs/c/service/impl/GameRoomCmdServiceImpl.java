package com.anbang.qipai.game.cqrs.c.service.impl;

import org.springframework.stereotype.Component;

import com.anbang.qipai.game.cqrs.c.domain.games.CanNotJoinMoreRoomsException;
import com.anbang.qipai.game.cqrs.c.domain.games.GameRoomNoManager;
import com.anbang.qipai.game.cqrs.c.domain.games.MemberGameRoomManager;
import com.anbang.qipai.game.cqrs.c.service.GameRoomCmdService;

@Component
public class GameRoomCmdServiceImpl extends CmdServiceBase implements GameRoomCmdService {

	@Override
	public String createRoom(String memberId, Integer maxRooms, Long createTime) throws CanNotJoinMoreRoomsException {
		MemberGameRoomManager memberGameRoomManager = singletonEntityRepository.getEntity(MemberGameRoomManager.class);
		memberGameRoomManager.joinRoom(memberId, maxRooms);
		GameRoomNoManager gameRoomNoManager = singletonEntityRepository.getEntity(GameRoomNoManager.class);
		String roomNo = gameRoomNoManager.newNo(createTime);
		return roomNo;
	}

}
