package com.anbang.qipai.game.cqrs.c.service.disruptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.cqrs.c.service.PlayBackCodeCmdService;
import com.anbang.qipai.game.cqrs.c.service.impl.PlayBackCodeCmdServiceImpl;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;

@Component(value = "playBackCodeCmdService")
public class DisruptorPlayBackCodeCmdService extends DisruptorCmdServiceBase implements PlayBackCodeCmdService {

	@Autowired
	private PlayBackCodeCmdServiceImpl playBackCmdServiceImpl;

	@Override
	public Integer getPlayBackCode() {
		CommonCommand cmd = new CommonCommand(PlayBackCodeCmdServiceImpl.class.getName(), "getPlayBackCode");
		DeferredResult<Integer> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
			Integer code = playBackCmdServiceImpl.getPlayBackCode();
			return code;
		});
		try {
			return result.getResult();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
