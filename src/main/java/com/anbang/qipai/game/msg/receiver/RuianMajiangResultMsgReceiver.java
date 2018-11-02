package com.anbang.qipai.game.msg.receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.msg.channel.sink.RuianMajiangResultSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalPanResult;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangPanPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.RuianMajiangJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.RuianMajiangPanPlayerResult;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.plan.service.MajiangHistoricalJuResultService;
import com.anbang.qipai.game.plan.service.MajiangHistoricalPanResultService;
import com.google.gson.Gson;

@EnableBinding(RuianMajiangResultSink.class)
public class RuianMajiangResultMsgReceiver {
	@Autowired
	private MajiangHistoricalJuResultService majiangHistoricalResultService;

	@Autowired
	private MajiangHistoricalPanResultService majiangHistoricalPanResultService;

	@Autowired
	private GameService gameService;

	private Gson gson = new Gson();

	@StreamListener(RuianMajiangResultSink.RUIANMAJIANGRESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("ruianmajiang ju result".equals(msg)) {
			String gameId = (String) map.get("gameId");
			MajiangHistoricalJuResult majiangHistoricalResult = new MajiangHistoricalJuResult();
			majiangHistoricalResult.setGameId(gameId);
			GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.ruianMajiang, gameId);
			majiangHistoricalResult.setRoomNo(room.getNo());
			majiangHistoricalResult.setGame(Game.ruianMajiang);
			majiangHistoricalResult.setDayingjiaId((String) map.get("dayingjiaId"));
			majiangHistoricalResult.setDatuhaoId((String) map.get("datuhaoId"));

			List<MajiangJuPlayerResult> juPlayerResultList = new ArrayList<>();
			((List) map.get("playerResultList")).forEach(
					(juPlayerResult) -> juPlayerResultList.add(new RuianMajiangJuPlayerResult((Map) juPlayerResult)));
			majiangHistoricalResult.setPlayerResultList(juPlayerResultList);

			majiangHistoricalResult.setPanshu(((Double) map.get("panshu")).intValue());
			majiangHistoricalResult.setLastPanNo(((Double) map.get("lastPanNo")).intValue());
			majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

			majiangHistoricalResultService.addMajiangHistoricalResult(majiangHistoricalResult);
		}
		if ("ruianmajiang pan result".equals(msg)) {
			String gameId = (String) map.get("gameId");
			MajiangHistoricalPanResult majiangHistoricalResult = new MajiangHistoricalPanResult();
			majiangHistoricalResult.setGameId(gameId);
			majiangHistoricalResult.setGame(Game.ruianMajiang);

			List<MajiangPanPlayerResult> panPlayerResultList = new ArrayList<>();
			((List) map.get("playerResultList")).forEach((panPlayerResult) -> panPlayerResultList
					.add(new RuianMajiangPanPlayerResult((Map) panPlayerResult)));
			majiangHistoricalResult.setPlayerResultList(panPlayerResultList);

			majiangHistoricalResult.setNo(((Double) map.get("no")).intValue());
			majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

			majiangHistoricalPanResultService.addMajiangHistoricalResult(majiangHistoricalResult);
		}
	}
}
