package com.anbang.qipai.game.cqrs.c.domain.games;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会员加入房间管理
 * 
 * @author Neo
 *
 */
public class MemberGameRoomManager {

	private Map<String, Integer> memberIdRoomsCountMap = new ConcurrentHashMap<>();

	public void joinRoom(String memberId, int maxRooms) throws CanNotJoinMoreRoomsException {
		Integer roomsCount = memberIdRoomsCountMap.get(memberId);
		if (roomsCount == null) {
			roomsCount = 0;
		}
		if (roomsCount < maxRooms) {
			memberIdRoomsCountMap.put(memberId, roomsCount + 1);
		} else {
			throw new CanNotJoinMoreRoomsException();
		}
	}

}
