package com.anbang.qipai.game.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.cqrs.c.service.PlayBackCodeCmdService;
import com.anbang.qipai.game.cqrs.q.dbo.PlayBackDbo;
import com.anbang.qipai.game.cqrs.q.service.PlayBackDboService;
import com.anbang.qipai.game.msg.service.GameDataReportMsgService;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.bean.games.NoServerAvailableForGameException;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.service.GameHistoricalJuResultService;
import com.anbang.qipai.game.plan.service.GameHistoricalPanResultService;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.web.vo.CommonVO;
import com.anbang.qipai.game.web.vo.GameDataReportVO;
import com.highto.framework.web.page.ListPage;

@RestController
@RequestMapping("/result")
public class MemberHistoricalResultController {

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private GameHistoricalJuResultService majiangHistoricalResultService;

	@Autowired
	private GameHistoricalPanResultService majiangHistoricalPanResultService;

	@Autowired
	private GameDataReportMsgService gameDataReportMsgService;

	@Autowired
	private GameService gameService;

	@Autowired
	private PlayBackCodeCmdService playBackCodeCmdService;

	@Autowired
	private PlayBackDboService playBackDboService;

	@RequestMapping(value = "/query_historicalresult")
	public CommonVO queryHistoricalResult(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "20") Integer size, String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		ListPage listPage = majiangHistoricalResultService.findGameHistoricalResultByMemberId(page, size, memberId);
		vo.setSuccess(true);
		vo.setMsg("historical result");
		vo.setData(listPage);
		return vo;
	}

	@RequestMapping(value = "/query_historicalresult_detail")
	public CommonVO queryHistoricalResultDetail(String id, String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		GameHistoricalJuResult majiangHistoricalResult = majiangHistoricalResultService
				.findGameHistoricalResultById(id);
		vo.setSuccess(true);
		vo.setMsg("historical result detail");
		vo.setData(majiangHistoricalResult);
		return vo;
	}

	@RequestMapping(value = "/query_historicalpanresult")
	public CommonVO queryHistoricalPanResult(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "20") Integer size, String token, Game game, String gameId) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		ListPage listPage = majiangHistoricalPanResultService.findGameHistoricalResultByMemberId(page, size, gameId,
				game);
		vo.setSuccess(true);
		vo.setMsg("historical result");
		vo.setData(listPage);
		return vo;
	}

	@RequestMapping(value = "/playback_self")
	public CommonVO playback(String token, Game game, String gameId, int panNo) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		GameServer gameServer;
		try {
			gameServer = gameService.getRandomGameServer(game);
		} catch (NoServerAvailableForGameException e) {
			vo.setSuccess(false);
			vo.setMsg("NoServerAvailableForGameException");
			return vo;
		}
		GameRoom room = gameService.findRoomByGameAndServerGameGameId(game, gameId);
		Map data = new HashMap();
		data.put("httpUrl", gameServer.getHttpUrl());
		data.put("gameId", gameId);
		data.put("panNo", panNo);
		data.put("roomNo", room.getNo());
		data.put("game", room.getGame());
		vo.setSuccess(true);
		vo.setMsg("playback");
		vo.setData(data);
		return vo;
	}

	@RequestMapping(value = "/shareplayback")
	public CommonVO sharePlayback(String token, Game game, String gameId, int panNo) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		PlayBackDbo playBackDbo = playBackDboService.findByGameAndGameIdAndPanNo(game, gameId, panNo);
		if (playBackDbo != null) {
			Map data = new HashMap();
			data.put("code", playBackDbo.getId());
			vo.setSuccess(true);
			vo.setMsg("playbackcode");
			vo.setData(data);
			return vo;
		}
		Integer code = playBackCodeCmdService.getPlayBackCode();
		int size = code.toString().length();
		String newCode = "";
		int i = 6 - size;
		while (i > 0) {
			newCode += "0";
			i--;
		}
		newCode += code.toString();
		PlayBackDbo dbo = new PlayBackDbo();
		dbo.setId(newCode);
		dbo.setGame(game);
		dbo.setGameId(gameId);
		dbo.setPanNo(panNo);
		playBackDboService.save(dbo);
		Map data = new HashMap();
		data.put("code", newCode);
		vo.setSuccess(true);
		vo.setMsg("playbackcode");
		vo.setData(data);
		return vo;
	}

	@RequestMapping(value = "/playback_code")
	public CommonVO playbackCode(String token, String code) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		PlayBackDbo dbo = playBackDboService.findById(code);
		if (dbo == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid code");
			return vo;
		}
		GameServer gameServer;
		try {
			gameServer = gameService.getRandomGameServer(dbo.getGame());
		} catch (NoServerAvailableForGameException e) {
			vo.setSuccess(false);
			vo.setMsg("NoServerAvailableForGameException");
			return vo;
		}
		GameRoom room = gameService.findRoomByGameAndServerGameGameId(dbo.getGame(), dbo.getGameId());
		Map data = new HashMap();
		data.put("httpUrl", gameServer.getHttpUrl());
		data.put("gameId", dbo.getGameId());
		data.put("panNo", dbo.getPanNo());
		data.put("roomNo", room.getNo());
		data.put("game", room.getGame());
		vo.setSuccess(true);
		vo.setMsg("playback");
		vo.setData(data);
		return vo;
	}

	/**
	 * 每日游戏数据生成
	 */
	@Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点
	@RequestMapping(value = "/creategamedatareport")
	private void createGameDataReport() {
		Game[] games = Game.values();
		long oneDay = 3600000 * 24;
		// 当日凌晨2点
		long endTime = System.currentTimeMillis();
		// 昨日凌晨2点
		long startTime = endTime - oneDay;
		for (Game game : games) {
			int currentMember = 0;// 进入游戏的当日会员人数
			int gameNum = majiangHistoricalResultService.countGameNumByGameAndTime(game, startTime, endTime);// 游戏总局数
			int loginMember = 0;// 独立玩家
			GameDataReportVO report = new GameDataReportVO(game, endTime, currentMember, gameNum, loginMember);
			gameDataReportMsgService.recordGameDataReport(report);
		}
	}
}
