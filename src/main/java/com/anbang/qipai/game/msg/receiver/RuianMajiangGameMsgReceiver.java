package com.anbang.qipai.game.msg.receiver;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.cqrs.c.service.GameRoomCmdService;
import com.anbang.qipai.game.msg.channel.sink.RuianMajiangGameSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.games.PlayersRecord;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalResult;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.plan.service.MajiangHistoricalResultService;
import com.anbang.qipai.game.plan.service.MemberService;
import com.anbang.qipai.game.remote.service.QipaiMembersRemoteService;
import com.google.gson.Gson;
import com.highto.framework.web.page.ListPage;

@EnableBinding(RuianMajiangGameSink.class)
public class RuianMajiangGameMsgReceiver {

	@Autowired
	private GameService gameService;

	@Autowired
	private GameRoomCmdService gameRoomCmdService;

	@Autowired
	private QipaiMembersRemoteService qipaiMembersRomoteService;

	@Autowired
	private MemberService memberService;
	
	private Gson gson = new Gson();

	@StreamListener(RuianMajiangGameSink.RUIANMAJIANGGAME)
	public void receive(CommonMO mo) {
		String msg = mo.getMsg();
		if ("playerQuit".equals(msg)) {// 有人退出游戏
			Map data = (Map) mo.getData();
			String gameId = (String) data.get("gameId");
			String playerId = (String) data.get("playerId");		
			GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.ruianMajiang, gameId);
			List<PlayersRecord> playersRecord = room.getPlayersRecord();	
				if (room.isVip() && !memberService.findMember(playerId).isVip()) {	
					int amount = playersRecord.get(0).getPayGold();	
					qipaiMembersRomoteService.gold_givegoldtomember(playerId, amount , "back gold to leave home");	
				//删除玩家记录
				for (int i = 0; i < playersRecord.size(); i++) {
					if (playersRecord.get(i).getPlayerId().equals(playerId)) {
						playersRecord.remove(i);
					}
				}
				gameService.saveGameRoom(room);					
			}
			gameService.ruianMajiangPlayerQuitQame(gameId, playerId);
		}
		
		if ("ju finished".equals(msg)) {// 一局游戏结束
			Map data = (Map) mo.getData();
			String gameId = (String) data.get("gameId");
			GameRoom gameRoom = gameService.findRoomByGameAndServerGameGameId(Game.ruianMajiang, gameId);
			gameRoomCmdService.removeRoom(gameRoom.getNo());
			gameService.gameRoomFinished(Game.ruianMajiang, gameId);

			List<PlayersRecord> playersRecord = gameRoom.getPlayersRecord();			
			//一盘没有打完，返回玉石
			for (int i = 0; i < playersRecord.size(); i++) {							
				if (gameRoom.getCurrentPanNum() == 0 && gameRoom.isVip() && !playersRecord.get(i).isVip()) {
					int amount = playersRecord.get(i).getPayGold();
					qipaiMembersRomoteService.gold_givegoldtomember(playersRecord.get(i).getPlayerId(), amount, "back gold to leave game");
				}				
				gameService.saveGameRoom(gameRoom);	
			}

		}
		if ("pan finished".equals(msg)) {// 一盘游戏结束
			Map data = (Map) mo.getData();
			String gameId = (String) data.get("gameId");
			int no = (int) data.get("no");
			List playerIds = (List) data.get("playerIds");
			gameService.panFinished(Game.ruianMajiang, gameId, no, playerIds);
		}
	}

}
