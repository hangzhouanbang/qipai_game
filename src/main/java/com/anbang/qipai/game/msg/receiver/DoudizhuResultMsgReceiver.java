package com.anbang.qipai.game.msg.receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.msg.channel.sink.DoudizhuResultSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.msg.service.DoudizhuResultMsgService;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GamePanPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.puke.DoudizhuJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.puke.DoudizhuPanPlayerResult;
import com.anbang.qipai.game.plan.service.GameHistoricalJuResultService;
import com.anbang.qipai.game.plan.service.GameHistoricalPanResultService;
import com.anbang.qipai.game.plan.service.GameService;
import com.google.gson.Gson;

@EnableBinding(DoudizhuResultSink.class)
public class DoudizhuResultMsgReceiver {
	@Autowired
	private GameHistoricalJuResultService majiangHistoricalResultService;

	@Autowired
	private GameHistoricalPanResultService majiangHistoricalPanResultService;

	@Autowired
	private DoudizhuResultMsgService doudizhuResultMsgService;

	@Autowired
	private GameService gameService;

	private Gson gson = new Gson();

	@StreamListener(DoudizhuResultSink.DOUDIZHURESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("doudizhu ju result".equals(msg)) {
			Object gid = map.get("gameId");
			Object dyjId = map.get("dayingjiaId");
			Object dthId = map.get("datuhaoId");
			if (gid != null && dyjId != null && dthId != null) {
				String gameId = (String) gid;
				GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.doudizhu, gameId);
				if (room != null) {
					GameHistoricalJuResult majiangHistoricalResult = new GameHistoricalJuResult();
					majiangHistoricalResult.setGameId(gameId);
					majiangHistoricalResult.setRoomNo(room.getNo());
					majiangHistoricalResult.setGame(Game.doudizhu);
					majiangHistoricalResult.setDayingjiaId((String) dyjId);
					majiangHistoricalResult.setDatuhaoId((String) dthId);

					Object playerList = map.get("playerResultList");
					if (playerList != null) {
						List<GameJuPlayerResult> juPlayerResultList = new ArrayList<>();
						((List) playerList).forEach((juPlayerResult) -> juPlayerResultList
								.add(new DoudizhuJuPlayerResult((Map) juPlayerResult)));
						majiangHistoricalResult.setPlayerResultList(juPlayerResultList);

						majiangHistoricalResult.setPanshu(((Double) map.get("panshu")).intValue());
						majiangHistoricalResult.setLastPanNo(((Double) map.get("lastPanNo")).intValue());
						majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

						majiangHistoricalResultService.addGameHistoricalResult(majiangHistoricalResult);
						doudizhuResultMsgService.newJuResult(majiangHistoricalResult);
					}
				}
			}
		}
		if ("doudizhu pan result".equals(msg)) {
			Object gid = map.get("gameId");
			if (gid != null) {
				String gameId = (String) gid;
				GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.doudizhu, gameId);
				if (room != null) {
					GameHistoricalPanResult majiangHistoricalResult = new GameHistoricalPanResult();
					majiangHistoricalResult.setGameId(gameId);
					majiangHistoricalResult.setGame(Game.doudizhu);

					Object playerList = map.get("playerResultList");
					if (playerList != null) {
						List<GamePanPlayerResult> panPlayerResultList = new ArrayList<>();
						((List) map.get("playerResultList")).forEach((panPlayerResult) -> panPlayerResultList
								.add(new DoudizhuPanPlayerResult((Map) panPlayerResult)));
						majiangHistoricalResult.setPlayerResultList(panPlayerResultList);

						majiangHistoricalResult.setNo(((Double) map.get("no")).intValue());
						majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

						majiangHistoricalPanResultService.addGameHistoricalResult(majiangHistoricalResult);
						doudizhuResultMsgService.newPanResult(majiangHistoricalResult);
					}
				}
			}
		}
	}
}
