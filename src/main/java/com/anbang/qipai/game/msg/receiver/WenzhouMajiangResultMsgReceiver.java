package com.anbang.qipai.game.msg.receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.msg.channel.sink.WenzhouMajiangResultSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GamePanPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.majiang.WenzhouMajiangJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.majiang.WenzhouMajiangPanPlayerResult;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.plan.service.GameHistoricalJuResultService;
import com.anbang.qipai.game.plan.service.GameHistoricalPanResultService;
import com.google.gson.Gson;

@EnableBinding(WenzhouMajiangResultSink.class)
public class WenzhouMajiangResultMsgReceiver {
	@Autowired
	private GameHistoricalJuResultService majiangHistoricalResultService;

	@Autowired
	private GameHistoricalPanResultService majiangHistoricalPanResultService;

	@Autowired
	private GameService gameService;

	private Gson gson = new Gson();

	@StreamListener(WenzhouMajiangResultSink.WENZHOUMAJIANGRESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("wenzhoumajiang ju result".equals(msg)) {
			String gameId = (String) map.get("gameId");
			GameHistoricalJuResult majiangHistoricalResult = new GameHistoricalJuResult();
			majiangHistoricalResult.setGameId(gameId);
			GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.wenzhouMajiang, gameId);
			majiangHistoricalResult.setRoomNo(room.getNo());
			majiangHistoricalResult.setGame(Game.wenzhouMajiang);
			majiangHistoricalResult.setDayingjiaId((String) map.get("dayingjiaId"));
			majiangHistoricalResult.setDatuhaoId((String) map.get("datuhaoId"));

			List<GameJuPlayerResult> juPlayerResultList = new ArrayList<>();
			((List) map.get("playerResultList")).forEach(
					(juPlayerResult) -> juPlayerResultList.add(new WenzhouMajiangJuPlayerResult((Map) juPlayerResult)));
			majiangHistoricalResult.setPlayerResultList(juPlayerResultList);

			majiangHistoricalResult.setPanshu(((Double) map.get("panshu")).intValue());
			majiangHistoricalResult.setLastPanNo(((Double) map.get("lastPanNo")).intValue());
			majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

			majiangHistoricalResultService.addGameHistoricalResult(majiangHistoricalResult);
		}
		if ("wenzhoumajiang pan result".equals(msg)) {
			String gameId = (String) map.get("gameId");
			GameHistoricalPanResult majiangHistoricalResult = new GameHistoricalPanResult();
			majiangHistoricalResult.setGameId(gameId);
			majiangHistoricalResult.setGame(Game.wenzhouMajiang);

			List<GamePanPlayerResult> panPlayerResultList = new ArrayList<>();
			((List) map.get("playerResultList")).forEach((panPlayerResult) -> panPlayerResultList
					.add(new WenzhouMajiangPanPlayerResult((Map) panPlayerResult)));
			majiangHistoricalResult.setPlayerResultList(panPlayerResultList);

			majiangHistoricalResult.setNo(((Double) map.get("no")).intValue());
			majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

			majiangHistoricalPanResultService.addGameHistoricalResult(majiangHistoricalResult);
		}
	}
}
