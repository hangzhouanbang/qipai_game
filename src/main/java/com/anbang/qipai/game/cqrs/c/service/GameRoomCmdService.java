package com.anbang.qipai.game.cqrs.c.service;

public interface GameRoomCmdService {

	String createRoom(String memberId, Long createTime);

	String removeRoom(String no);
}
