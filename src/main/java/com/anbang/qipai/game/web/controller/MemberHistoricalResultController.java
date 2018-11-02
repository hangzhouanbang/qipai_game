package com.anbang.qipai.game.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.msg.service.GameDataReportMsgService;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.bean.games.NoServerAvailableForGameException;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalJuResult;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.plan.service.MajiangHistoricalJuResultService;
import com.anbang.qipai.game.plan.service.MajiangHistoricalPanResultService;
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
	private MajiangHistoricalJuResultService majiangHistoricalResultService;

	@Autowired
	private MajiangHistoricalPanResultService majiangHistoricalPanResultService;

	@Autowired
	private GameDataReportMsgService gameDataReportMsgService;

	@Autowired
	private GameService gameService;

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
		ListPage listPage = majiangHistoricalResultService.findMajiangHistoricalResultByMemberId(page, size, memberId);
		vo.setSuccess(true);
		vo.setMsg("majiang historical result");
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
		MajiangHistoricalJuResult majiangHistoricalResult = majiangHistoricalResultService
				.findMajiangHistoricalResultById(id);
		vo.setSuccess(true);
		vo.setMsg("majiang historical result detail");
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
		ListPage listPage = majiangHistoricalPanResultService.findMajiangHistoricalResultByMemberId(page, size, gameId,
				game);
		vo.setSuccess(true);
		vo.setMsg("majiang historical result");
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
		Map data = new HashMap();
		data.put("httpUrl", gameServer.getHttpUrl());
		data.put("wsUrl", gameServer.getWsUrl());
		data.put("gameId", gameId);
		data.put("panNo", panNo);
		vo.setSuccess(true);
		vo.setMsg("playback");
		vo.setData(data);
		return vo;
	}

	/**
	 * 每日游戏数据生成
	 */
	@Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点
	// @RequestMapping(value = "/creategamedatareport")
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
