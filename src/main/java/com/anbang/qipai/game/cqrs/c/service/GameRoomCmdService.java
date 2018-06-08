package com.anbang.qipai.game.cqrs.c.service;

import com.anbang.qipai.game.cqrs.c.domain.games.CanNotJoinMoreRoomsException;

public interface GameRoomCmdService {

	String createRoom(String memberId, Integer maxRooms, Long createTime) throws CanNotJoinMoreRoomsException;

}
