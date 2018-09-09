package com.anbang.qipai.game.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.msg.service.GameDataReportMsgService;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalResult;
import com.anbang.qipai.game.plan.service.MajiangHistoricalResultService;
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
	private MajiangHistoricalResultService majiangHistoricalResultService;

	@Autowired
	private GameDataReportMsgService gameDataReportMsgService;

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
		MajiangHistoricalResult majiangHistoricalResult = majiangHistoricalResultService
				.findMajiangHistoricalResultById(id);
		vo.setSuccess(true);
		vo.setMsg("majiang historical result detail");
		vo.setData(majiangHistoricalResult);
		return vo;
	}

	/**
	 * 每日游戏数据生成
	 */
	@Scheduled(cron = "0 0 1 * * ?") // 每天凌晨1点
	public void createPlatformReport() {
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
