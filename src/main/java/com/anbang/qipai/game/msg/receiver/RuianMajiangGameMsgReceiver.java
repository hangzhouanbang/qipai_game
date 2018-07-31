package com.anbang.qipai.game.msg.receiver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.msg.channel.RuianMajiangGameSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.service.GameService;
import com.google.gson.Gson;

@EnableBinding(RuianMajiangGameSink.class)
public class RuianMajiangGameMsgReceiver {

	@Autowired
	private GameService gameService;

	private Gson gson = new Gson();

	@StreamListener(RuianMajiangGameSink.RUIANMAJIANGGAME)
	public void receive(CommonMO mo) {

		if ("playerQuit".equals(mo.getMsg())) {// 有人退出游戏
			Map data = (Map) mo.getData();
			String gameId = (String) data.get("gameId");
			String playerId = (String) data.get("playerId");
			gameService.ruianMajiangPlayerQuitQame(gameId, playerId);
		}
	}

}
