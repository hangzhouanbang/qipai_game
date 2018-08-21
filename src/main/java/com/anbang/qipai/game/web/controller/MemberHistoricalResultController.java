package com.anbang.qipai.game.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.plan.bean.historicalresult.MajiangHistoricalResult;
import com.anbang.qipai.game.plan.service.MajiangHistoricalResultService;
import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.web.vo.CommonVO;
import com.highto.framework.web.page.ListPage;

@RestController
@RequestMapping("/result")
public class MemberHistoricalResultController {

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private MajiangHistoricalResultService majiangHistoricalResultService;

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

}
