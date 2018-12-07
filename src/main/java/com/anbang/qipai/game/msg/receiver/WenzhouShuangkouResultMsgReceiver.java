package com.anbang.qipai.game.msg.receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.msg.channel.sink.WenzhouShuangkouResultSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GamePanPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.puke.WenzhouShuangkouJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.puke.WenzhouShuangkouPanPlayerResult;
import com.anbang.qipai.game.plan.service.GameHistoricalJuResultService;
import com.anbang.qipai.game.plan.service.GameHistoricalPanResultService;
import com.anbang.qipai.game.plan.service.GameService;
import com.google.gson.Gson;

@EnableBinding(WenzhouShuangkouResultSink.class)
public class WenzhouShuangkouResultMsgReceiver {
	@Autowired
	private GameHistoricalJuResultService gameHistoricalResultService;

	@Autowired
	private GameHistoricalPanResultService gameHistoricalPanResultService;

	@Autowired
	private GameService gameService;

	private Gson gson = new Gson();

	@StreamListener(WenzhouShuangkouResultSink.WENZHOUSHUANGKOURESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("wenzhoushuangkou ju result".equals(msg)) {
			String gameId = (String) map.get("gameId");
			GameHistoricalJuResult pukeHistoricalResult = new GameHistoricalJuResult();
			pukeHistoricalResult.setGameId(gameId);
			GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.wenzhouShuangkou, gameId);
			pukeHistoricalResult.setRoomNo(room.getNo());
			pukeHistoricalResult.setGame(Game.wenzhouShuangkou);
			pukeHistoricalResult.setDayingjiaId((String) map.get("dayingjiaId"));
			pukeHistoricalResult.setDatuhaoId((String) map.get("datuhaoId"));

			List<GameJuPlayerResult> juPlayerResultList = new ArrayList<>();
			((List) map.get("playerResultList")).forEach((juPlayerResult) -> juPlayerResultList
					.add(new WenzhouShuangkouJuPlayerResult((Map) juPlayerResult)));
			pukeHistoricalResult.setPlayerResultList(juPlayerResultList);

			pukeHistoricalResult.setPanshu(((Double) map.get("panshu")).intValue());
			pukeHistoricalResult.setLastPanNo(((Double) map.get("lastPanNo")).intValue());
			pukeHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

			gameHistoricalResultService.addGameHistoricalResult(pukeHistoricalResult);
		}
		if ("wenzhoushuangkou pan result".equals(msg)) {
			String gameId = (String) map.get("gameId");
			GameHistoricalPanResult pukeHistoricalResult = new GameHistoricalPanResult();
			pukeHistoricalResult.setGameId(gameId);
			pukeHistoricalResult.setGame(Game.wenzhouShuangkou);

			List<GamePanPlayerResult> panPlayerResultList = new ArrayList<>();
			((List) map.get("playerResultList")).forEach((panPlayerResult) -> panPlayerResultList
					.add(new WenzhouShuangkouPanPlayerResult((Map) panPlayerResult)));
			pukeHistoricalResult.setPlayerResultList(panPlayerResultList);

			pukeHistoricalResult.setNo(((Double) map.get("no")).intValue());
			pukeHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

			gameHistoricalPanResultService.addGameHistoricalResult(pukeHistoricalResult);
		}
	}
}
