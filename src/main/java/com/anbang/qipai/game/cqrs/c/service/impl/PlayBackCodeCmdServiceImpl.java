package com.anbang.qipai.game.cqrs.c.service.impl;

import org.springframework.stereotype.Component;

import com.anbang.qipai.game.cqrs.c.domain.playback.PlayBackCodeManager;
import com.anbang.qipai.game.cqrs.c.service.PlayBackCodeCmdService;

@Component
public class PlayBackCodeCmdServiceImpl extends CmdServiceBase implements PlayBackCodeCmdService {

	@Override
	public Integer getPlayBackCode() {
		PlayBackCodeManager playBackCodeManager = singletonEntityRepository.getEntity(PlayBackCodeManager.class);
		int code = playBackCodeManager.getPlayBackCode();
		return code;
	}

}
