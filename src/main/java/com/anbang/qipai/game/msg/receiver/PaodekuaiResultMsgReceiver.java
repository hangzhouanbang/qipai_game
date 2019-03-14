package com.anbang.qipai.game.msg.receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.anbang.qipai.game.msg.channel.sink.PaodekuaiResultSink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.msg.channel.sink.PaodekuaiResultSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.msg.service.PaodekuaiResultMsgService;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GamePanPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.puke.PaodekuaiJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.puke.PaodekuaiPanPlayerResult;
import com.anbang.qipai.game.plan.service.GameHistoricalJuResultService;
import com.anbang.qipai.game.plan.service.GameHistoricalPanResultService;
import com.anbang.qipai.game.plan.service.GameService;
import com.google.gson.Gson;

@EnableBinding(PaodekuaiResultSink.class)
public class PaodekuaiResultMsgReceiver {
	@Autowired
	private GameHistoricalJuResultService majiangHistoricalResultService;

	@Autowired
	private GameHistoricalPanResultService majiangHistoricalPanResultService;

	@Autowired
	private PaodekuaiResultMsgService paodekuaiResultMsgService;

	@Autowired
	private GameService gameService;

	private Gson gson = new Gson();

	@StreamListener(PaodekuaiResultSink.PAODEKUAIRESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("paodekuai ju result".equals(msg)) {
			Object gid = map.get("gameId");
			Object dyjId = map.get("dayingjiaId");
			Object dthId = map.get("datuhaoId");
			if (gid != null && dyjId != null && dthId != null) {
				String gameId = (String) gid;
				GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.paodekuai, gameId);
				if (room != null) {
					GameHistoricalJuResult majiangHistoricalResult = new GameHistoricalJuResult();
					majiangHistoricalResult.setGameId(gameId);
					majiangHistoricalResult.setRoomNo(room.getNo());
					majiangHistoricalResult.setGame(Game.paodekuai);
					majiangHistoricalResult.setDayingjiaId((String) dyjId);
					majiangHistoricalResult.setDatuhaoId((String) dthId);

					Object playerList = map.get("playerResultList");
					if (playerList != null) {
						List<GameJuPlayerResult> juPlayerResultList = new ArrayList<>();
						((List) playerList).forEach((juPlayerResult) -> juPlayerResultList
								.add(new PaodekuaiJuPlayerResult((Map) juPlayerResult)));
						majiangHistoricalResult.setPlayerResultList(juPlayerResultList);

						majiangHistoricalResult.setPanshu(((Double) map.get("panshu")).intValue());
						majiangHistoricalResult.setLastPanNo(((Double) map.get("lastPanNo")).intValue());
						majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

						majiangHistoricalResultService.addGameHistoricalResult(majiangHistoricalResult);
						paodekuaiResultMsgService.newJuResult(majiangHistoricalResult);
					}
				}
			}
		}
		if ("paodekuai pan result".equals(msg)) {
			Object gid = map.get("gameId");
			if (gid != null) {
				String gameId = (String) gid;
				GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.paodekuai, gameId);
				if (room != null) {
					GameHistoricalPanResult majiangHistoricalResult = new GameHistoricalPanResult();
					majiangHistoricalResult.setGameId(gameId);
					majiangHistoricalResult.setGame(Game.paodekuai);

					Object playerList = map.get("playerResultList");
					if (playerList != null) {
						List<GamePanPlayerResult> panPlayerResultList = new ArrayList<>();
						((List) map.get("playerResultList")).forEach((panPlayerResult) -> panPlayerResultList
								.add(new PaodekuaiPanPlayerResult((Map) panPlayerResult)));
						majiangHistoricalResult.setPlayerResultList(panPlayerResultList);

						majiangHistoricalResult.setNo(((Double) map.get("no")).intValue());
						majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

						majiangHistoricalPanResultService.addGameHistoricalResult(majiangHistoricalResult);
						paodekuaiResultMsgService.newPanResult(majiangHistoricalResult);
					}
				}
			}
		}
	}
}
