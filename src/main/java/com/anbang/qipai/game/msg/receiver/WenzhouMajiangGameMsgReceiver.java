package com.anbang.qipai.game.msg.receiver;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.cqrs.c.service.GameRoomCmdService;
import com.anbang.qipai.game.msg.channel.sink.WenzhouMajiangGameSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.service.GameService;
import com.google.gson.Gson;

@EnableBinding(WenzhouMajiangGameSink.class)
public class WenzhouMajiangGameMsgReceiver {
	@Autowired
	private GameService gameService;

	@Autowired
	private GameRoomCmdService gameRoomCmdService;

	private Gson gson = new Gson();

	@StreamListener(WenzhouMajiangGameSink.WENZHOUMAJIANGGAME)
	public void receive(CommonMO mo) {
		String msg = mo.getMsg();
		if ("playerQuit".equals(msg)) {// 有人退出游戏
			Map data = (Map) mo.getData();
			String gameId = (String) data.get("gameId");
			String playerId = (String) data.get("playerId");
			gameService.wenzhouMajiangPlayerQuitQame(gameId, playerId);
		}
		if ("ju finished".equals(msg)) {// 一局游戏结束
			Map data = (Map) mo.getData();
			String gameId = (String) data.get("gameId");
			GameRoom gameRoom = gameService.findRoomByGameAndServerGameGameId(Game.wenzhouMajiang, gameId);
			gameRoomCmdService.removeRoom(gameRoom.getNo());
			gameService.gameRoomFinished(Game.wenzhouMajiang, gameId);
		}
		if ("pan finished".equals(msg)) {// 一盘游戏结束
			Map data = (Map) mo.getData();
			String gameId = (String) data.get("gameId");
			int no = (int) data.get("no");
			List playerIds = (List) data.get("playerIds");
			gameService.panFinished(Game.wenzhouMajiang, gameId, no, playerIds);
		}
	}
}
