package com.anbang.qipai.game.msg.receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.msg.channel.sink.DaboluoResultSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.msg.service.DaboluoResultMsgService;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GamePanPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.majiang.DianpaoMajiangJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.majiang.DianpaoMajiangPanPlayerResult;
import com.anbang.qipai.game.plan.service.GameHistoricalJuResultService;
import com.anbang.qipai.game.plan.service.GameHistoricalPanResultService;
import com.anbang.qipai.game.plan.service.GameService;
import com.google.gson.Gson;

@EnableBinding(DaboluoResultSink.class)
public class DaboluoResultMsgReceiver {
	@Autowired
	private GameHistoricalJuResultService majiangHistoricalResultService;

	@Autowired
	private GameHistoricalPanResultService majiangHistoricalPanResultService;

	@Autowired
	private DaboluoResultMsgService daboluoResultMsgService;

	@Autowired
	private GameService gameService;

	private Gson gson = new Gson();

	@StreamListener(DaboluoResultSink.DABOLUORESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("daboluo ju result".equals(msg)) {
			Object gid = map.get("gameId");
			Object dyjId = map.get("dayingjiaId");
			Object dthId = map.get("datuhaoId");
			if (gid != null && dyjId != null && dthId != null) {
				String gameId = (String) gid;
				GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.daboluo, gameId);
				if (room != null) {
					GameHistoricalJuResult majiangHistoricalResult = new GameHistoricalJuResult();
					majiangHistoricalResult.setGameId(gameId);
					majiangHistoricalResult.setRoomNo(room.getNo());
					majiangHistoricalResult.setGame(Game.daboluo);
					majiangHistoricalResult.setDayingjiaId((String) dyjId);
					majiangHistoricalResult.setDatuhaoId((String) dthId);

					Object playerList = map.get("playerResultList");
					if (playerList != null) {
						List<GameJuPlayerResult> juPlayerResultList = new ArrayList<>();
						((List) playerList).forEach((juPlayerResult) -> juPlayerResultList
								.add(new DianpaoMajiangJuPlayerResult((Map) juPlayerResult)));
						majiangHistoricalResult.setPlayerResultList(juPlayerResultList);

						majiangHistoricalResult.setPanshu(((Double) map.get("panshu")).intValue());
						majiangHistoricalResult.setLastPanNo(((Double) map.get("lastPanNo")).intValue());
						majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

						majiangHistoricalResultService.addGameHistoricalResult(majiangHistoricalResult);
						daboluoResultMsgService.newJuResult(majiangHistoricalResult);
					}
				}
			}
		}
		if ("daboluo pan result".equals(msg)) {
			Object gid = map.get("gameId");
			if (gid != null) {
				String gameId = (String) gid;
				GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.daboluo, gameId);
				if (room != null) {
					GameHistoricalPanResult majiangHistoricalResult = new GameHistoricalPanResult();
					majiangHistoricalResult.setGameId(gameId);
					majiangHistoricalResult.setGame(Game.daboluo);

					Object playerList = map.get("playerResultList");
					if (playerList != null) {
						List<GamePanPlayerResult> panPlayerResultList = new ArrayList<>();
						((List) map.get("playerResultList")).forEach((panPlayerResult) -> panPlayerResultList
								.add(new DianpaoMajiangPanPlayerResult((Map) panPlayerResult)));
						majiangHistoricalResult.setPlayerResultList(panPlayerResultList);

						majiangHistoricalResult.setNo(((Double) map.get("no")).intValue());
						majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

						majiangHistoricalPanResultService.addGameHistoricalResult(majiangHistoricalResult);
						daboluoResultMsgService.newPanResult(majiangHistoricalResult);
					}
				}
			}
		}
	}
}
