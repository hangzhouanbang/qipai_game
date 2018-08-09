package com.anbang.qipai.game.cqrs.c.service.disruptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.cqrs.c.service.GameRoomCmdService;
import com.anbang.qipai.game.cqrs.c.service.impl.GameRoomCmdServiceImpl;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;

@Component(value = "gameRoomCmdService")
public class DisruptorGameRoomCmdService extends DisruptorCmdServiceBase implements GameRoomCmdService {

	@Autowired
	private GameRoomCmdServiceImpl gameRoomCmdServiceImpl;

	@Override
	public String createRoom(String memberId, Long createTime) {
		CommonCommand cmd = new CommonCommand(GameRoomCmdServiceImpl.class.getName(), "createRoom", memberId,
				createTime);
		DeferredResult<String> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
			String roomNo = gameRoomCmdServiceImpl.createRoom(cmd.getParameter(), cmd.getParameter());
			return roomNo;
		});
		try {
			return result.getResult();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String removeRoom(String no) {
		CommonCommand cmd = new CommonCommand(GameRoomCmdServiceImpl.class.getName(), "removeRoom", no);
		DeferredResult<String> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
			String roomNo = gameRoomCmdServiceImpl.removeRoom(cmd.getParameter());
			return roomNo;
		});
		try {
			return result.getResult();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
